package com.alan.slidingmenu.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.alan.slidingmenu.Activity.CompetitionActivity;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ifig on 26/01/2016.
 */

public class PopupCompetition extends Dialog {

    private Dialog pop;
    private CompetitionActivity context;
    private EditText libelle;
    private EditText lieu;
    private EditText poids;
    private EditText arr1;
    private EditText arr2;
    private EditText arr3;
    private EditText epj1;
    private EditText epj2;
    private EditText epj3;
    private DatePicker datePicker;

    public PopupCompetition(CompetitionActivity pContext) {

        super(pContext);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.popup_competition);

        context = pContext;
        pop = this;

        libelle = (EditText) findViewById(R.id.competLibelle);
        lieu = (EditText) findViewById(R.id.competLieu);
        poids = (EditText) findViewById(R.id.competPoids);
        arr1 = (EditText) findViewById(R.id.competA1);
        arr2 = (EditText) findViewById(R.id.competA2);
        arr3 = (EditText) findViewById(R.id.competA3);
        epj1 = (EditText) findViewById(R.id.competEpj1);
        epj2 = (EditText) findViewById(R.id.competEpj2);
        epj3 = (EditText) findViewById(R.id.competEpj3);
        datePicker = (DatePicker) findViewById(R.id.competDate);

        Button save = (Button) findViewById(R.id.competButtonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (libelle.getText().toString().length()==0)
                    libelle.setError("Renseignez ce champ");
                else if (lieu.getText().toString().length()==0)
                    lieu.setError("Renseignez ce champ");
                else if (poids.getText().toString().length()==0)
                poids.setError("Renseignez ce champ");
                else if (arr1.getText().toString().length()==0)
                    arr1.setError("Renseignez ce champ");
                else if (arr2.getText().toString().length()==0)
                    arr2.setError("Renseignez ce champ");
                else if (epj1.getText().toString().length()==0)
                    epj1.setError("Renseignez ce champ");
                else if (epj2.getText().toString().length()==0)
                    epj2.setError("Renseignez ce champ");
                else if (epj3.getText().toString().length()==0)
                    epj3.setError("Renseignez ce champ");
                else {
                    JSONObject json = new JSONObject();
                    try {

                        Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateSQL =  format.format(date);

                        UserPreferences pref = new UserPreferences(context);
                        User user = pref.readUser();

                        json.put("Libelle",libelle.getText().toString());
                        json.put("Lieu",lieu.getText().toString());
                        json.put("DateCompet",dateSQL);
                        json.put("PoidsDeCorps",poids.getText().toString());
                        json.put("Arr1",arr1.getText().toString());
                        json.put("Arr2",arr2.getText().toString());
                        json.put("Arr3",arr3.getText().toString());
                        json.put("Epj1",epj1.getText().toString());
                        json.put("Epj2",epj2.getText().toString());
                        json.put("Epj3", epj3.getText().toString());
                        json.put("idUser",user.getIdUser());

                        UrlHTTPConnection url = new UrlHTTPConnection(context,UrlHTTPConnection.NOUVELLE_COMPETITION);
                        url.execute(json.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }  catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    pop.cancel();

                    context.finish();
                    context.startActivity(context.getIntent());
                }
            }
        });

    }

}
