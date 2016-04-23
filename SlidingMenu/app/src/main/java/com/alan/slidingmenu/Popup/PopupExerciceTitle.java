package com.alan.slidingmenu.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.alan.slidingmenu.Activity.NouvelleSeanceActivity;
import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.R;

/**
 * Created by alanmocaer on 05/02/16.
 */

public class PopupExerciceTitle extends Dialog {

    private Dialog pop;
    private Exercice exo;
    private Context context;

    public PopupExerciceTitle(Context pContext, Exercice pExo) {

        super(pContext);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = pContext;
        pop = this;
        exo = pExo;

        this.setContentView(R.layout.popup_exercice_title);
        //this.setCancelable(false);
        //this.show();
        Button ok = (Button) findViewById(R.id.popupOKTitle);
        ok.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      EditText title = (EditText) findViewById(R.id.titleExercice);
                                      EditText rep = (EditText) findViewById(R.id.popupRepTitle);
                                      EditText serie = (EditText) findViewById(R.id.popupSerieTitle);
                                      EditText pourc = (EditText) findViewById(R.id.popupPourcTitle);

                                      if (title.getText().length() == 0) {
                                          title.setError("Quel exercice ?");
                                      }
                                      if (serie.getText().length() == 0) {
                                          serie.setError("Combien de séries ?");
                                      } else if (rep.getText().length() == 0) {
                                          rep.setError("Combien de répétitions ?");
                                      } else if (pourc.getText().length() == 0) {
                                          pourc.setError("Quel pourcentage ?");
                                      } else {
                                          exo.setNom(title.getText().toString());
                                          exo.setSerie(Integer.parseInt(serie.getText().toString()));
                                          exo.setRep(Integer.parseInt(rep.getText().toString()));
                                          exo.setPourc(Integer.parseInt(pourc.getText().toString()));

                                          ((NouvelleSeanceActivity) context).addExo();
                                          pop.cancel();
                                      }

                                  }

                              }

        );


    }
}