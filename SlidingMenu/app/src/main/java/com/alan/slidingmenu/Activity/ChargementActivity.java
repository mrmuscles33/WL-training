package com.alan.slidingmenu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;

public class ChargementActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private NavigationView navigationView;
    private EditText poids;
    private RadioGroup groupe;
    private LinearLayout image;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        UserPreferences pref = new UserPreferences(this);
        User user = pref.readUser();

        setNomHeader(user);

        image = (LinearLayout) findViewById(R.id.imageChargement);
        poids = (EditText) findViewById(R.id.poidsChargement);
        groupe = (RadioGroup) findViewById(R.id.radioGroupChargement);
        //check = (RadioButton) findViewById(groupe.getCheckedRadioButtonId());

        Button calcul = (Button) findViewById(R.id.chargementButton);
        calcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int barre = 0;
                if (R.id.chargementHomme == groupe.getCheckedRadioButtonId())
                    barre = 20;
                else
                    barre = 15;

                int poidsBarre = Integer.parseInt(poids.getText().toString());

                if (poidsBarre >= barre + 5) {

                    double[] lesPoids = {25, 20, 15, 10, 5, 2.5, 2, 1.5, 1, 0.5};
                    int[] qte = new int[10];

                    for (int i = 0; i < 10; i++)
                        qte[i] = 0;

                    //Poids - barre - bagues
                    poidsBarre -= (barre + 5);

                    for (int i = 0; i < 10; i++) {
                        qte[i] = (int) (poidsBarre / (2 * lesPoids[i]));
                        poidsBarre -= 2 * qte[i] * lesPoids[i];
                    }

                    //AFFICHAGE
                    int[] lesImg = {R.drawable.poids25, R.drawable.poids20, R.drawable.poids15, R.drawable.poids10, R.drawable.poids5, R.drawable.poids2v5, R.drawable.poids2, R.drawable.poids1v5, R.drawable.poids1, R.drawable.poids0v5};

                    image.removeAllViews();

                    int nbPoids = 1;
                    for (int i : qte)
                        nbPoids += i;

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < qte[i]; j++) {
                            ImageView img = new ImageView(context);
                            img.setImageResource(lesImg[i]);

                            DisplayMetrics metrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(metrics);

                            int height = (int) (metrics.heightPixels * 1.5);

                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(image.getWidth() / nbPoids, height);
                            img.setLayoutParams(params);

                            image.addView(img);
                        }
                    }

                    ImageView img = new ImageView(context);
                    img.setImageResource(R.drawable.bague);

                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(image.getWidth() / nbPoids, image.getHeight());
                    img.setLayoutParams(params);

                    image.addView(img);

                } else {
                    image.removeAllViews();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chargement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        com.alan.slidingmenu.Activity.Menu.goTo(this,id);
        finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setNomHeader(User user) {
        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navHeaderUserName);
        if (user != User.USER_VIDE)
            name.setText(user.getPseudo() + " (" + user.getEmail() + ")");
        else {
            name.setText("Non connecté");
        }
    }
}
