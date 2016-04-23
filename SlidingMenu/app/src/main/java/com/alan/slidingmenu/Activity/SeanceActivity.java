package com.alan.slidingmenu.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.Bouton;
import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.Classe.Seance;
import com.alan.slidingmenu.Classe.SeancePreferences;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupPourcentage;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SeanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,NomHeader {

    private Chronometer chronometer;
    private static final int ACTION = 0;
    private static final int PAUSE = 1;
    private static final int INITIAL = 2;
    private int etat;
    private Seance seance;
    private Context context;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sliding menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Nom de l'utilisateur courant
        UserPreferences pref = new UserPreferences(this);
        User user = pref.readUser();

        setNomHeader(user);

        context = this;

        // Recup de la seance
        seance = new Seance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            seance = (Seance) b.get("Seance");
        }

        SeancePreferences prefSeance = new SeancePreferences(this);
        prefSeance.writeSeance(seance);

        for (Exercice exo : seance.getLesExos()) {

            LinearLayout conteneurSeance = (LinearLayout) findViewById(R.id.conteneurSeance);

            TextView nom = new TextView(this);
            nom.setText(exo.getNom());
            nom.setTextColor(Color.rgb(40, 40, 40));
            nom.setTextSize(20);
            nom.setTypeface(null, Typeface.BOLD);
            nom.setPadding(0, 10, 0, 10);
            conteneurSeance.addView(nom);

            TextView serie = new TextView(this);
            serie.setText("\t" + exo.getSerie() + " séries de " + exo.getRep() + " rép. à " + exo.getPourc() + " %");
            serie.setTextColor(Color.rgb(40, 40, 40));
            serie.setTextSize(18);
            serie.setTypeface(null, Typeface.ITALIC);
            serie.setPadding(0, 10, 0, 10);
            conteneurSeance.addView(serie);

            LinearLayout lesBoutons = new LinearLayout(this);
            lesBoutons.setOrientation(LinearLayout.HORIZONTAL);
            lesBoutons.setMinimumWidth(conteneurSeance.getWidth());
            lesBoutons.setPadding(0, 0, 0, 20);

            for (int i = 0; i < exo.getSerie(); i++) {

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                int X = (int) (metrics.widthPixels * 0.9) / exo.getSerie();

                if (X > 200)
                    X = (int) (metrics.widthPixels * 0.9) / 5;

                Bouton tb = new Bouton(this, X, X, seance.getLesExos().indexOf(exo), i, seance);

                lesBoutons.addView(tb);
            }

            conteneurSeance.addView(lesBoutons);

            View v = new View(this);
            v.setMinimumHeight(4);
            v.setBackgroundColor(Color.rgb(40, 40, 40));

            conteneurSeance.addView(v);


            System.out.println("Exercice : "+exo.getId()+"-"+exo.getNom()+"-"+exo.getSerie()+"-"+exo.getRep()+"-"+exo.getPourc());
        }

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        etat = INITIAL;

        chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (etat) {
                    case INITIAL:
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.setBackgroundColor(Color.GREEN);
                        chronometer.start();
                        etat = ACTION;
                        break;
                    case PAUSE:
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.setBackgroundColor(Color.WHITE);
                        etat = INITIAL;
                        break;
                    case ACTION:
                        chronometer.stop();
                        chronometer.setBackgroundColor(Color.RED);
                        etat = PAUSE;
                        break;

                }
            }
        });

        ImageView calcul = (ImageView) findViewById(R.id.ic_calc);
        calcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupPourcentage pop = new PopupPourcentage(context);
                pop.show();
            }
        });

        Button fini = (Button) findViewById(R.id.seanceFinie);
        fini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONArray seanceJson = new JSONArray();
                UserPreferences pref = new UserPreferences(context);
                User u = pref.readUser();

                try {
                    for (Exercice e : seance.getLesExos()) {
                        JSONArray exoJson = new JSONArray();

                        for (int i : e.getLesSeries()) {
                            JSONObject serie = new JSONObject();

                            serie.put("charge", i);
                            serie.put("idExercice", e.getId());
                            serie.put("idUser", u.getIdUser());

                            exoJson.put(serie);
                        }

                        seanceJson.put(exoJson);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.NOUVELLE_SERIE);
                url.execute(seanceJson.toString());

                Intent i = new Intent(SeanceActivity.this, SeanceTabhost.class);
                i.putExtra("Seance", seance);
                startActivity(i);
                finish();
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
        //getMenuInflater().inflate(R.menu.seance, menu);
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
    public void onPause(){
        super.onPause();
        SeancePreferences prefSeance = new SeancePreferences(this);
        prefSeance.writeSeance(seance);
    }

    @Override
    public void onStop(){
        super.onStop();
        SeancePreferences prefSeance = new SeancePreferences(this);
        prefSeance.writeSeance(seance);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        SeancePreferences prefSeance = new SeancePreferences(this);
        prefSeance.writeSeance(seance);
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
