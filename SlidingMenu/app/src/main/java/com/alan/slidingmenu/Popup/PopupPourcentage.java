package com.alan.slidingmenu.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alan.slidingmenu.R;

/**
 * Created by Alan on 14/10/15.
 */

public class PopupPourcentage extends Dialog {

    private Dialog pop;
    private Context context;

    public PopupPourcentage(Context pContext) {

        super(pContext);

        context = pContext;
        pop = this;


        this.setContentView(R.layout.popup_pourcentage);
        this.setTitle("Pourcentage");
        this.setCancelable(true);
        //this.show();
        Button calc = (Button) findViewById(R.id.popupPourcentageCalc);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText max = (EditText) findViewById(R.id.popupPourcentageMax);
                EditText pourc = (EditText) findViewById(R.id.popupPourcentagePourc);

                int maximum = Integer.parseInt(max.getText().toString());
                int pourcentage = Integer.parseInt(pourc.getText().toString());

                TextView res = (TextView) findViewById(R.id.popupPourcentageRes);
                res.setText(Integer.toString(maximum * pourcentage / 100));

            }
        });


    }


}
