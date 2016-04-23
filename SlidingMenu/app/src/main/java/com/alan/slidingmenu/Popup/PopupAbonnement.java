package com.alan.slidingmenu.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class PopupAbonnement extends Dialog {

    private Dialog pop;
    private Context context;
    private EditText entraineur;

    public PopupAbonnement(Context pContext) {

        super(pContext);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_abonnement);

        context = pContext;
        pop = this;

        entraineur = (EditText) findViewById(R.id.abonnerEntraineur);

        Button abonner = (Button) findViewById(R.id.abonnerButton);
        abonner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPreferences pref = new UserPreferences(context);
                User user = pref.readUser();

                if (user == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour vous abonner.")
                            .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else if (entraineur.getText().toString().length() == 0)
                    entraineur.setError("Renseignez ce champ");
                else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("pseudo", entraineur.getText().toString());
                        json.put("idUser", user.getIdUser());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.ABONNEMENT);
                    url.execute(json.toString());


                    try {
                        String reponse = url.get();
                        JSONObject repJson = new JSONObject(reponse);

                        if ((boolean) repJson.get("reponse")) {
                            entraineur.setError("Entraineur inconnu");
                        } else {
                            pop.cancel();
                            Toast.makeText(context,"Abonnement réussi", Toast.LENGTH_SHORT);
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
