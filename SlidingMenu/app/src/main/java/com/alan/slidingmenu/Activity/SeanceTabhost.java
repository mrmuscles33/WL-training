package com.alan.slidingmenu.Activity;

import android.content.Intent;
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

import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.Classe.MyListAdapter;
import com.alan.slidingmenu.Classe.Seance;
import com.alan.slidingmenu.Classe.SeancePreferences;
import com.alan.slidingmenu.Fragment.SectionPagerAdapter;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Fragment.FragmentHistorique;
import com.alan.slidingmenu.Fragment.FragmentProchaineSeance;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class SeanceTabhost extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,NomHeader {

    private ViewPager viewPager;
    private MyListAdapter mSchedule;
    private ListView list;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_tabhost);

        //Sliding menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Gestion des fragment
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        UserPreferences pref = new UserPreferences(this);
        User user = pref.readUser();
        setNomHeader(user);

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
        //getMenuInflater().inflate(R.menu.seance_tabhost, menu);
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

    public void goToSeance(View v) {

        if (viewPager.getCurrentItem() == 0) {
            int numLine = ((FragmentProchaineSeance) ((SectionPagerAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem())).getList().getPositionForView(v);

            int[] ids = ((FragmentProchaineSeance) ((SectionPagerAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem())).getIds();

            SeancePreferences prefSeance = new SeancePreferences(this);

            Seance seance = new Seance();

            if (prefSeance.readSeance().getId()==ids[numLine]){
                System.out.println("Reprise de la seance");
                seance = prefSeance.readSeance();
            } else {

                seance.setId(ids[numLine]);

                JSONObject obj = new JSONObject();
                try {
                    obj.put("idSeance", ids[numLine]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UrlHTTPConnection url = new UrlHTTPConnection(this, UrlHTTPConnection.SEANCE);
                url.execute(obj.toString());

                String rep = "";

                try {
                    rep = url.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                try {

                    JSONArray array = new JSONArray(rep);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);

                        Exercice e = new Exercice();

                        e.setId(o.getInt("idExercice"));
                        e.setNom(o.getString("NomExercice"));
                        e.setSerie(o.getInt("NombreSerie"));
                        e.setRep(o.getInt("NombreRep"));
                        e.setPourc(o.getInt("Charge"));

                        seance.getLesExos().add(e);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Intent i = new Intent(SeanceTabhost.this, SeanceActivity.class);
            i.putExtra("Seance", seance);
            startActivity(i);
            finish();
        } else {
            int numLine = ((FragmentHistorique) ((SectionPagerAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem())).getList().getPositionForView(v);

            String[] ids = ((FragmentHistorique) ((SectionPagerAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem())).getIds();
            String[] dates = ((FragmentHistorique) ((SectionPagerAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem())).getDates();

            UserPreferences pref = new UserPreferences(this);
            User u = pref.readUser();

            JSONObject obj = new JSONObject();
            try {
                obj.put("idSeance", ids[numLine]);
                obj.put("idUser", u.getIdUser());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            UrlHTTPConnection url = new UrlHTTPConnection(this, UrlHTTPConnection.HISTORIQUE_SCEANCE);
            url.execute(obj.toString());

            String rep = "";

            try {
                rep = url.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Seance seance = new Seance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date convertedCurrentDate = sdf.parse(dates[numLine]);
                seance.setDate(convertedCurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            try {

                JSONArray array = new JSONArray(rep);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);

                    Exercice e = new Exercice();

                    e.setNom(o.getString("NomExercice"));
                    e.setSerie(o.getInt("NombreSerie"));
                    e.setRep(o.getInt("NombreRep"));
                    e.setPourc(o.getInt("Charge"));

                    int[] lesSeries = new int[e.getSerie()];

                    JSONArray arraySeries = o.getJSONArray("lesSeries");

                    for (int j = 0; j < arraySeries.length(); j++) {
                        lesSeries[j] = arraySeries.getInt(j);
                    }

                    e.setLesSeries(lesSeries);

                    seance.getLesExos().add(e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(SeanceTabhost.this, HistoriqueActivity.class);
            i.putExtra("Seance", seance);
            startActivity(i);
        }
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
