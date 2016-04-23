package com.alan.slidingmenu.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.Classe.Seance;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;

public class HistoriqueActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private Seance seance;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
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

        seance = new Seance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            seance = (Seance) b.get("Seance");
        }

        for (Exercice exo : seance.getLesExos()) {

            LinearLayout conteneurSeance = (LinearLayout) findViewById(R.id.conteneurSeanceHistorique);

            TextView date = (TextView) findViewById(R.id.historiqueDate);
            date.setText(seance.getDateFormatee().toString());

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

            TextView lesCharges = new TextView(this);
            lesCharges.setTextColor(Color.rgb(40, 40, 40));
            lesCharges.setTextSize(18);
            lesCharges.setPadding(0, 10, 0, 10);
            lesCharges.setText(Integer.toString(exo.getLesSeries()[0]));

            for (int i = 1; i < exo.getSerie(); i++) {
                lesCharges.setText(lesCharges.getText() + " - " + exo.getLesSeries()[i]);
            }

            conteneurSeance.addView(lesCharges);

            View v = new View(this);
            v.setMinimumHeight(4);
            v.setBackgroundColor(Color.rgb(40, 40, 40));

            conteneurSeance.addView(v);
        }
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
        //getMenuInflater().inflate(R.menu.historique, menu);
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

        com.alan.slidingmenu.Activity.Menu.goTo(this, id);
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
