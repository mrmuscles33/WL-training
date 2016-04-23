package com.alan.slidingmenu.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alan.slidingmenu.Activity.NomHeader;
import com.alan.slidingmenu.Classe.Exercice;
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

public class PopupConnexion extends Dialog {
    private Dialog pop;
    private Exercice exo;
    private Context context;
    private EditText pseudo;
    private EditText motDePasse;
    private Button connexion;
    private Button inscrire;

    public PopupConnexion(Context pContext) {

        super(pContext);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_connexion);

        context = pContext;
        pop = this;

        this.setTitle("Connexion");

        connexion = (Button) findViewById(R.id.ButtonConnexion);
        pseudo = (EditText) findViewById(R.id.EditextPseudo);
        motDePasse = (EditText) findViewById(R.id.EditTextMDP);
        inscrire = (Button) findViewById(R.id.sinscrire);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pseudo.setText(pseudo.getText().toString().replaceAll("\\s", ""));
                pseudo.setText(pseudo.getText().toString().toLowerCase());

                motDePasse.setText(motDePasse.getText().toString().replaceAll("\\s", ""));
                motDePasse.setText(motDePasse.getText().toString().toLowerCase());

                if (pseudo.getText().toString().equals("")) {
                    pseudo.setError("Champ vide");
                } else if (motDePasse.getText().toString().equals("")) {
                    motDePasse.setError("Champ vide");
                } else {

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("pseudo", pseudo.getText().toString());
                        obj.put("password", motDePasse.getText().toString());
                        UrlHTTPConnection conn = new UrlHTTPConnection(context, UrlHTTPConnection.CONNEXION);

                        conn.execute(obj.toString());

                        String reponse = conn.get();

                        JSONObject repJson = new JSONObject(reponse);

                        User u;

                        if ((boolean) repJson.get("reponse")) { // Si connexion reussie
                            int id = repJson.getInt("idUser");
                            String email = (String) repJson.get("email");
                            String pseudo = (String) repJson.get("pseudo");
                            String password = (String) repJson.get("password");

                            u = new User(id, email, pseudo, password);

                            pop.cancel();

                            Toast.makeText(context, "Connexion reussie", Toast.LENGTH_SHORT).show();
                        } else {
                            pseudo.setText("");
                            motDePasse.setText("");
                            pseudo.setError("Identifiants incorrects");

                            u = User.USER_VIDE;
                        }

                        UserPreferences pref = new UserPreferences(context);
                        pref.writeUser(u);

                        ((NomHeader) context).setNomHeader(u);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.cancel();
                PopupInscription popup = new PopupInscription(context);
                popup.show();
            }
        });
    }

}
