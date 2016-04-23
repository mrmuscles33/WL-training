package com.alan.slidingmenu.Popup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alan.slidingmenu.BDD.ExoDico;
import com.alan.slidingmenu.Classe.CustomPagerAdapter;
import com.alan.slidingmenu.R;

public class PopupDescExercice extends AppCompatActivity {
    private ViewPager mViewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_desc_exercice);

        Bundle b = getIntent().getExtras();
        ExoDico exo = b.getParcelable("exo");
        String name = exo.getStringExo();
        String categorie = exo.getCategorie();
        String description = exo.getDescription();
        String objectif = exo.getObjectif();
        String variante = exo.getVariante();
        String[] image = new String[5];

        image[0] = exo.getImage1();
        image[1] = exo.getImage2();
        image[2] = exo.getImage3();
        image[3] = exo.getImage4();
        image[4] = exo.getImage5();

        TextView categorieView = (TextView) findViewById(R.id.TextViewCategorie);
        TextView descriptionView = (TextView) findViewById(R.id.TextViewDescription);
        TextView objectifView = (TextView) findViewById(R.id.TextViewObjectifs);
        TextView varianteView = (TextView) findViewById(R.id.TextViewVariante);

        categorieView.setText(categorie + " " + name);
        categorieView.setTypeface(null, Typeface.BOLD);
        descriptionView.setText("Description : " + description + "");
        objectifView.setText("Objectifs : " + objectif + "");
        varianteView.setText("Variantes : " + variante + "");

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this);
        mCustomPagerAdapter.setmResources(image);
        mViewPager = (ViewPager) findViewById(R.id.view_custom);
        mViewPager.setAdapter(mCustomPagerAdapter);
    }

}
