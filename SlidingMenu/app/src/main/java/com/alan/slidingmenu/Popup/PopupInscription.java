package com.alan.slidingmenu.Popup;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
 * Created by Alan on 29/01/2016.
 */
public class PopupInscription extends Dialog {
    private Dialog pop;
    private Context context;
    private EditText pseudo;
    private EditText email;
    private EditText motDePasse;
    private EditText confirmation;
    private Button inscription;

    public PopupInscription(Context pContext) {

        super(pContext);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_inscription);

        context = pContext;
        pop = this;

        this.setTitle("Inscription");

        pseudo = (EditText) findViewById(R.id.pseudoInscription);
        email = (EditText) findViewById(R.id.emailInscription);
        motDePasse = (EditText) findViewById(R.id.mdpInscription);
        confirmation = (EditText) findViewById(R.id.confirmationInscription);
        inscription = (Button) findViewById(R.id.ButtonInscription);

        pseudo.setText(pseudo.getText().toString().replaceAll("\\s", ""));
        email.setText(email.getText().toString().replaceAll("\\s", ""));
        motDePasse.setText(motDePasse.getText().toString().replaceAll("\\s", ""));
        confirmation.setText(confirmation.getText().toString().replaceAll("\\s", ""));

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strPseudo = pseudo.getText().toString().toLowerCase();

                String strEmail = email.getText().toString().toLowerCase();

                String strMdp = motDePasse.getText().toString().toLowerCase();

                String strConf = confirmation.getText().toString().toLowerCase();

                if (strPseudo.length() == 0)
                    pseudo.setError("Renseignez ce champ");
                else if (strEmail.length() == 0)
                    email.setError("Renseignez ce champ");
                else if (strMdp.length() == 0)
                    motDePasse.setError("Renseignez ce champ");
                else if (strConf.length() == 0)
                    confirmation.setError("Renseignez ce champ");
                else if (!strMdp.equals(strConf)) {
                    confirmation.setText("");
                    motDePasse.setText("");
                    motDePasse.setError("Mots de passe différents");
                } else {

                    //String strEmail = email.getText().toString();
                    //String strPseudo = pseudo.getText().toString();
                    //String strMdp = motDePasse.getText().toString();

                    JSONObject json = new JSONObject();
                    try {
                        json.put("email", strEmail);
                        json.put("pseudo", strPseudo);
                        json.put("password", strMdp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.INSCRIPTION);
                    url.execute(json.toString());


                    JSONObject rep = null;
                    try {
                        System.out.println(url.get());
                        rep = new JSONObject(url.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (!rep.getBoolean("reponse")) {
                            pseudo.setText("");
                            email.setText("");
                            motDePasse.setText("");
                            confirmation.setText("");

                            pseudo.setError("Pseudo ou email déjà réservé");
                        } else {

                            JSONObject obj = new JSONObject();

                            try {

                                obj.put("pseudo", pseudo.getText().toString());
                                obj.put("password", motDePasse.getText().toString());
                                UrlHTTPConnection conn = new UrlHTTPConnection(context, UrlHTTPConnection.CONNEXION);

                                conn.execute(obj.toString());

                                String reponse = conn.get();

                                JSONObject repJson = new JSONObject(reponse);

                                User u = User.USER_VIDE;

                                if ((boolean) repJson.get("reponse")) { // Si connexion reussie
                                    int id = repJson.getInt("idUser");
                                    String email = (String) repJson.get("email");
                                    String pseudo = (String) repJson.get("pseudo");
                                    String password = (String) repJson.get("password");

                                    u = new User(id, email, pseudo, password);

                                    ((AppCompatActivity) context).finish();
                                    context.startActivity(((AppCompatActivity) context).getIntent());

                                    pop.cancel();

                                    Toast.makeText(context, "Connexion reussie", Toast.LENGTH_SHORT).show();
                                }

                                UserPreferences pref = new UserPreferences(context);
                                pref.writeUser(u);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
