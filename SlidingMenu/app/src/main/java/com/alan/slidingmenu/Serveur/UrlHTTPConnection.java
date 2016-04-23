package com.alan.slidingmenu.Serveur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.alan.slidingmenu.Popup.PopupAbonnement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Alan on 22/10/15.
 */
public class UrlHTTPConnection extends AsyncTask<String, Void, String> {

    private Context context;
    public static final String CONNEXION = "connexion";
    public static final String INSCRIPTION = "inscription";
    public static final String NOUVELLE_SEANCE = "newSeance";
    public static final String PROCHAINE_SEANCE = "nextSeance";
    public static final String HISTORIQUE = "historique";
    public static final String SEANCE = "seance";
    public static final String NOUVELLE_SERIE = "newSerie";
    public static final String HISTORIQUE_SCEANCE = "historiqueSeance";
    public static final String ABONNEMENT = "abonnement";
    public static final String NOUVELLE_COMPETITION = "newCompet";
    public static final String COMPETITION = "competition";
    public static final String DELETE_COMPETITION = "deleteCompet";
    public static final String MODIF_EMAIL = "modifEmail";
    public static final String MODIF_PASSWORD = "modifPass";
    public static final String DELETE_COMPTE = "deleteCompte";
    public static final String MES_ABONNEMENTS = "mesAbonnements";
    public static final String DELETE_ABONNEMENT = "deleteAbonnement";
    public static final String DELETE_SEANCE = "deleteSeance";
    private String script;

    public UrlHTTPConnection(Context pcontext, String string) {
        context = pcontext;
        script = string;
    }

    @Override
    protected String doInBackground(String... params) {

        // creation de la connection HTTP
        URL url = null;
        try {

            url = new URL("http://wltraining.fr/" + script + ".php");
            //url = new URL("http://192.168.43.8:8888/ProjetTutS4/" + script + ".php");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(conn);

        // création des données POST qui doivent être passé en paramètre
        String message = (String) params[0];

        // creation de data sous la forme nom=iza&message=coucou
        String data = null;
        try {
            data = URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // envoyer la requete HTTP par le bon stream et fermer la connection
        OutputStream os = null;
        try {
            os = conn.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
        BufferedWriter bufferWriter = null;
        try {
            bufferWriter = new BufferedWriter((new OutputStreamWriter(os, "UTF-8")));
            bufferWriter.write(data);
            bufferWriter.flush();
            bufferWriter.close();
            os.close();
            conn.connect();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // attraper et concatener la réponse du serveur en un block
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader((new InputStreamReader(conn.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // affiche la réponse du serveur dans le LogCat
        Log.i("retour serveur", "Reponse = " + s);
    }
}
