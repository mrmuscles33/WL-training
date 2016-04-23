package com.alan.slidingmenu.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupConnexion;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alanmocaer on 09/03/16.
 */
public class PopupDeleteCompte  extends Dialog {

    private Dialog pop;
    private Context context;
    private EditText pseudo;
    private EditText password;

    public PopupDeleteCompte(Context pContext) {

        super(pContext);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_delete_compte);

        this.setTitle("Supprimer mon compte");

        context = pContext;
        pop = this;

        pseudo = (EditText) findViewById(R.id.popupDltCptPseudo);
        password = (EditText) findViewById(R.id.popupDltCptPassword);

        Button supprimer = (Button) findViewById(R.id.popupDltCptButton);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreferences pref = new UserPreferences(context);
                User user = pref.readUser();

                if (user == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour supprimer votre compte.")
                            .setPositiveButton("Se connecter", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else if (pseudo.getText().toString().length() == 0)
                    pseudo.setError("Renseignez ce champ");
                else if (password.getText().toString().length() == 0)
                    password.setError("Renseignez ce champ");
                else if (!(pseudo.getText().toString().equals(user.getPseudo()) || pseudo.getText().toString().equals(user.getEmail()))) {
                    pseudo.setText("");
                    pseudo.setError("Pseudo ou email incorrect");
                } else if (!password.getText().toString().equals(user.getPassword())) {
                    password.setText("");
                    password.setError("Mot de passe incorrect");
                } else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("idUser", user.getIdUser());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.DELETE_COMPTE);
                    url.execute(json.toString());

                    pref.writeUser(User.USER_VIDE);

                    pop.cancel();
                    Toast.makeText(context, "Compte supprimé", Toast.LENGTH_SHORT);

                    ((CompteActivity) context).finish();
                    context.startActivity(((CompteActivity) context).getIntent());

                }
            }
        });
    }

}
