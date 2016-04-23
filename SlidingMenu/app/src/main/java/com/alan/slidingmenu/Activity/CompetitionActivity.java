package com.alan.slidingmenu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.Competition;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Fragment.FragmentCompetitionListe;
import com.alan.slidingmenu.Fragment.SectionPagerAdapterCompetition;
import com.alan.slidingmenu.Popup.PopupCompetition;
import com.alan.slidingmenu.Popup.PopupConnexion;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CompetitionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private ViewPager viewPager;
    private NavigationView navigationView;
    private CompetitionActivity context;
    private ArrayList<Competition> lesCompet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new SectionPagerAdapterCompetition(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        UserPreferences pref = new UserPreferences(this);
        User user = pref.readUser();

        setNomHeader(user);

        //RECUP DES DONNEES
        JSONObject json = new JSONObject();
        try {
            json.put("idUser", user.getIdUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UrlHTTPConnection url = new UrlHTTPConnection(this, UrlHTTPConnection.COMPETITION);
        url.execute(json.toString());


        try {
            String rep = url.get();

            lesCompet = new ArrayList<>();

            JSONArray array = new JSONArray(rep);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                int idCompet = obj.getInt("idCompet");
                String libelle = obj.getString("Libelle");
                String lieu = obj.getString("Lieu");
                String dateStr = obj.getString("DateCompet");
                double poids = obj.getDouble("PoidsDeCorps");
                int arr1 = obj.getInt("Arr1");
                int arr2 = obj.getInt("Arr2");
                int arr3 = obj.getInt("Arr3");
                int epj1 = obj.getInt("Epj1");
                int epj2 = obj.getInt("Epj2");
                int epj3 = obj.getInt("Epj3");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dateStr);

                Competition compet = new Competition(idCompet, libelle, lieu, date, poids, arr1, arr2, arr3, epj1, epj2, epj3);

                lesCompet.add(compet);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
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
        getMenuInflater().inflate(R.menu.competition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addCompet) {
            UserPreferences pref = new UserPreferences(this);
            User user = pref.readUser();

            if (user != User.USER_VIDE) {
                PopupCompetition pop = new PopupCompetition(this);
                pop.show();
            } else {

                new AlertDialog.Builder(this)
                        .setTitle("Attention")
                        .setMessage("Vous devez vous connectez pour ajouter une compétition.")
                        .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PopupConnexion pop = new PopupConnexion(context);
                                pop.show();
                            }
                        })
                        .show();
            }
            return true;
        } else if (id == R.id.resetCompet) {

            new AlertDialog.Builder(this)
                    .setTitle("Attention")
                    .setMessage("Voulez vous supprimer toutes vos compétitions.")
                    .setPositiveButton("Supprimier", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            for (Competition compet : lesCompet) {

                                int idCompet = compet.getId();

                                JSONObject obj = new JSONObject();
                                try {
                                    obj.put("idCompet", idCompet);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.DELETE_COMPETITION);
                                url.execute(obj.toString());
                            }

                            context.finish();
                            context.startActivity(context.getIntent());
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();


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

    public void deleteCompet(View v) {

        ListView list = ((FragmentCompetitionListe) ((SectionPagerAdapterCompetition) viewPager.getAdapter()).getItem(0)).getList();

        int position = list.getPositionForView(v);

        final int idCompet = lesCompet.get(position).getId();

        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Voulez vous supprimer cette compétition.")
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("idCompet", idCompet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.DELETE_COMPETITION);
                        url.execute(obj.toString());

                        context.finish();
                        context.startActivity(context.getIntent());
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    public ArrayList<Competition> getLesCompet() {
        return this.lesCompet;
    }
}


