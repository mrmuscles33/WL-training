package com.alan.slidingmenu.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alan.slidingmenu.Activity.NouvelleSeanceActivity;
import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.R;

/**
 * Created by Alan on 14/10/15.
 */

public class PopupExercice extends Dialog {

    private Dialog pop;
    private Exercice exo;
    private Context context;

    public PopupExercice(Context pContext, Exercice pExo) {

        super(pContext);

        context = pContext;
        pop = this;
        exo = pExo;

        this.setContentView(R.layout.popup_exercice);
        this.setTitle(exo.getNom());
        //this.setCancelable(false);
        //this.show();
        Button ok = (Button) findViewById(R.id.popupOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText rep = (EditText) findViewById(R.id.popupRep);
                EditText serie = (EditText) findViewById(R.id.popupSerie);
                EditText pourc = (EditText) findViewById(R.id.popupPourc);

                if (serie.getText().length() == 0) {
                    serie.setError("Combien de séries ?");
                } else if (rep.getText().length() == 0) {
                    rep.setError("Combien de répétitions ?");
                } else if (pourc.getText().length() == 0) {
                    pourc.setError("Quel pourcentage ?");
                } else {
                    exo.setSerie(Integer.parseInt(serie.getText().toString()));
                    exo.setRep(Integer.parseInt(rep.getText().toString()));
                    exo.setPourc(Integer.parseInt(pourc.getText().toString()));

                    ((NouvelleSeanceActivity) context).addExo();
                    pop.cancel();
                }

            }
        });


    }


}
