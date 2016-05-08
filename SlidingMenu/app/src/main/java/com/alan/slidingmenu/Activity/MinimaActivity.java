package com.alan.slidingmenu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alan.slidingmenu.BDD.Minima;
import com.alan.slidingmenu.BDD.MinimaBDD;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ifigm on 23/03/2016.
 */
public class MinimaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private LinearLayout parent;
    private RadioGroup groupe;
    private NavigationView navigationView;
    private List<String> categories;
    private List<Integer> categoriesPoids;
    private MinimaBDD bd;
    private Context pContext;
    private List<Minima> minimas;
    private Spinner spinnerCategorie;
    private Spinner spinnerCategoriePoids;
    private CheckBox national;
    private CheckBox europe;
    private CheckBox monde;
    private int sexe;
    private List<Integer> poids;
    private List<TextView> textViewListPoids;
    private String categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minima);
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

        textViewListPoids = new ArrayList<TextView>();
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids2));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids3));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids4));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids5));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids6));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids7));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids8));
        textViewListPoids.add((TextView) findViewById(R.id.textViewPoids9));

        parent = (LinearLayout) findViewById(R.id.linearMinima);
        spinnerCategorie = (Spinner) findViewById(R.id.spinner);
        spinnerCategoriePoids = (Spinner) findViewById(R.id.spinner2);
        groupe = (RadioGroup) findViewById(R.id.radioGroupeCheck);
        minimas = new ArrayList<Minima>();
        pContext = this;
        bd = new MinimaBDD(getApplicationContext());
        bd.open();
        minimas = bd.getMinima();

        bd.close();

        groupe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                bd.open();
                if (checkedId == R.id.radioSexeMinima) {
                    sexe = 0;
                } else {
                    sexe = 1;
                }

                    categories = bd.getCategorie(sexe);


                bd.close();

                ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(pContext, android.R.layout.simple_spinner_item, categories);
                dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategorie.setAdapter(dataAdapterR);
                spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        categorie = String.valueOf(spinnerCategorie.getSelectedItem());
                        bd.open();
                        categoriesPoids = bd.getCategoriePoids(sexe, categorie);
                        bd.close();

                        ArrayAdapter<Integer> dataAdapterR = new ArrayAdapter<>(pContext, android.R.layout.simple_spinner_item, categoriesPoids);
                        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoriePoids.setAdapter(dataAdapterR);
                        spinnerCategoriePoids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                String categoriePoids = String.valueOf(spinnerCategoriePoids.getSelectedItem());
                                bd.open();
                                int i = 0;
                                poids = bd.getPoids(sexe, categorie, categoriePoids);
                                for (TextView t : textViewListPoids) t.setText("");

                                minimas = bd.getMinima(sexe,categorie,categoriePoids);

                                for(Minima m : minimas){
                                    textViewListPoids.get(minimas.indexOf(m)).setText(m.getNiveaux() + " : " + m.getPoids() + " Kg");
                                }

                                bd.close();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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