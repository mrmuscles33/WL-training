package com.alan.slidingmenu.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.R;

/**
 * Created by Alan on 14/10/15.
 */

public class PopupSerie extends Dialog {

    private Dialog pop;
    private Exercice exo;
    private int serie;
    private Context context;

    public PopupSerie(Context pContext, Exercice pExo, int s) {

        super(pContext);

        context = pContext;
        pop = this;
        exo = pExo;
        this.serie = s;

        this.setContentView(R.layout.popup_serie);
        this.setTitle("Série " + (serie + 1));
        this.setCancelable(true);
        //this.show();
        Button ok = (Button) findViewById(R.id.popupSerieOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText charge = (EditText) findViewById(R.id.popupSerieCharge);
                EditText commentaire = (EditText) findViewById(R.id.popupSerieComm);

                if (charge.getText().length() == 0) {
                    charge.setError("Quelle charge ?");
                } else {
                    exo.getLesSeries()[serie] = Integer.parseInt(charge.getText().toString());
                    exo.getLesCommentaires()[serie] = commentaire.getText().toString();

                    System.out.println("commentaire : "+exo.getLesCommentaires()[serie]);
                    pop.cancel();

                }

            }
        });


    }


}
