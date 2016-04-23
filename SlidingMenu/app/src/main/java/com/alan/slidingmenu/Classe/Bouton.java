package com.alan.slidingmenu.Classe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.alan.slidingmenu.Popup.PopupSerie;
import com.alan.slidingmenu.R;

/**
 * Created by Alan on 16/10/15.
 */
public class Bouton extends ImageButton {


    private Bouton c;
    private int X;
    private int Y;
    private boolean check;
    private int exo;
    private int numSerie;
    private Seance seance;
    private Context context;

    public Bouton(Context pContext) {
        super(pContext);
    }

    public Bouton(Context pContext, int pX, int pY, int e, int serie, Seance s) {
        super(pContext);

        c = this;

        X = pX;
        Y = pY;
        check = false;
        this.exo = e;
        numSerie = serie;
        seance = s;
        context = pContext;

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(X, Y);
        this.setLayoutParams(params);

        if (s.getLesExos().get(e).getLesSeries()[serie] == 0)
            this.setBackgroundResource(R.drawable.no_check);
        else
            this.setBackgroundResource(R.drawable.check);

        this.setPadding(2, 2, 2, 2);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    check = false;
                    c.setBackgroundResource(R.drawable.no_check);
                    seance.getLesExos().get(exo).getLesSeries()[numSerie] = 0;
                } else {
                    check = true;
                    c.setBackgroundResource(R.drawable.check);

                    PopupSerie pop = new PopupSerie(context, seance.getLesExos().get(exo), numSerie);
                    pop.show();
                }
            }
        });
    }
}

