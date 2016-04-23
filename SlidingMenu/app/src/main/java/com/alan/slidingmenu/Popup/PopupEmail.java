package com.alan.slidingmenu.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alan.slidingmenu.Activity.CompteActivity;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Ifig on 26/01/2016.
 */

public class PopupEmail extends Dialog {

    private Dialog pop;
    private Context context;
    private EditText email;

    public PopupEmail(Context pContext) {

        super(pContext);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_email);

        this.setTitle("Modifier mon email");

        context = pContext;
        pop = this;
        UserPreferences pref = new UserPreferences(context);

        email = (EditText) findViewById(R.id.newEmail);

        TextView monEmail = (TextView) findViewById(R.id.monEmail);
        monEmail.setText(pref.readUser().getEmail());

        Button modifier = (Button) findViewById(R.id.popupEmailButton);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreferences pref = new UserPreferences(context);
                User user = pref.readUser();

                if (user == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour modifier votre adresse mail.")
                            .setPositiveButton("Se connecter", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else if (email.getText().toString().length() == 0)
                    email.setError("Renseignez ce champ");
                else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("email", email.getText().toString());
                        json.put("idUser", user.getIdUser());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.MODIF_EMAIL);
                    url.execute(json.toString());

                    try {
                        String reponse = url.get();
                        JSONObject repJson = new JSONObject(reponse);

                        if (!(boolean) repJson.get("reponse")) {
                            email.setError("Adresse invalide ou déjà occupée");
                        } else {
                            user.setEmail(email.getText().toString());
                            pref.writeUser(user);

                            pop.cancel();
                            Toast.makeText(context, "Adresse mail modifiée", Toast.LENGTH_SHORT);

                            ((CompteActivity) context).finish();
                            context.startActivity(((CompteActivity) context).getIntent());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
