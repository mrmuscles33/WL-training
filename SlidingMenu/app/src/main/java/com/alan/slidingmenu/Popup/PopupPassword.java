package com.alan.slidingmenu.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alan.slidingmenu.Activity.CompteActivity;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alan on 09/03/2016.
 */

public class PopupPassword extends Dialog {

    private Dialog pop;
    private Context context;
    private EditText newPassword;
    private EditText confNewPassword;
    private EditText oldPassword;

    public PopupPassword(Context pContext) {

        super(pContext);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_password);

        this.setTitle("Modifier mot de passe");

        context = pContext;
        pop = this;

        newPassword = (EditText) findViewById(R.id.popupPassNew);
        confNewPassword = (EditText) findViewById(R.id.popupPassConf);
        oldPassword = (EditText) findViewById(R.id.popupPassOld);

        Button modifier = (Button) findViewById(R.id.popupPassButton);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreferences pref = new UserPreferences(context);
                User user = pref.readUser();

                if (user == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour modifier votre mot de passe.")
                            .setPositiveButton("Se connecter", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else if (newPassword.getText().toString().length() == 0)
                    newPassword.setError("Renseignez ce champ");
                else if (confNewPassword.getText().toString().length() == 0)
                    confNewPassword.setError("Renseignez ce champ");
                else if (oldPassword.getText().toString().length() == 0)
                    oldPassword.setError("Renseignez ce champ");
                else if (!oldPassword.getText().toString().equals(user.getPassword())) {
                    oldPassword.setText("");
                    oldPassword.setError("Mot de passe incorrect");
                } else if (!confNewPassword.getText().toString().equals(newPassword.getText().toString())) {
                    newPassword.setText("");
                    confNewPassword.setText("");
                    newPassword.setError("Mots de passe différents");
                } else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("password", newPassword.getText().toString());
                        json.put("idUser", user.getIdUser());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.MODIF_PASSWORD);
                    url.execute(json.toString());

                    user.setPassword(newPassword.getText().toString());
                    pref.writeUser(user);

                    pop.cancel();
                    Toast.makeText(context, "Adresse mail modifiée", Toast.LENGTH_SHORT);

                    ((CompteActivity) context).finish();
                    context.startActivity(((CompteActivity) context).getIntent());

                }
            }
        });
    }

}
