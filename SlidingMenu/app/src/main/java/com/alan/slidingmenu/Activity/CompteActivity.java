package com.alan.slidingmenu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.MyListAdapter;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupEmail;
import com.alan.slidingmenu.Popup.PopupPassword;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by alanmocaer on 08/03/16.
 */
public class CompteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private NavigationView navigationView;
    private CompteActivity context;

    private MyListAdapter mSchedule;
    private ListView list;
    private int ids[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

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

        Button modifEmail = (Button) findViewById(R.id.button_change_mail);
        modifEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupEmail pop = new PopupEmail(context);
                pop.show();
            }
        });

        Button modifPass = (Button) findViewById(R.id.button_change_pass);
        modifPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupPassword pop = new PopupPassword(context);
                pop.show();
            }
        });

        Button deleteCompte = (Button) findViewById(R.id.button_supp_compte);
        deleteCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupDeleteCompte pop = new PopupDeleteCompte(context);
                pop.show();
            }
        });

        // A FAIRE
        list = (ListView) findViewById(R.id.list_abo);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;

        JSONObject json = new JSONObject();
        try {
            json.put("idUser", user.getIdUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.MES_ABONNEMENTS);
        url.execute(json.toString());

        JSONArray lesAbo = new JSONArray();

        try {
            lesAbo = new JSONArray(url.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int nbAbo = lesAbo.length();

        ids = new int[nbAbo];
        String lesPseudo[] = new String[nbAbo];
        String lesEmail[] = new String[nbAbo];

        for (int i = 0; i < nbAbo; i++) {
            try {
                lesPseudo[i] = lesAbo.getJSONObject(i).getString("pseudo");
                lesEmail[i] = lesAbo.getJSONObject(i).getString("email");
                ids[i] = lesAbo.getJSONObject(i).getInt("idUser");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < nbAbo; i++) {
            map = new HashMap<String, String>();
            map.put("pseudo", lesPseudo[i]);
            map.put("email", lesEmail[i]);
            listItem.add(map);
        }

        mSchedule = new MyListAdapter(context, listItem,
                R.layout.liste_abonnement, new String[]{"pseudo", "email"}, new int[]{
                R.id.listeViewAboPseudo, R.id.listeViewAboEmail});

        list.setAdapter(mSchedule);
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

    public void deleteAbo(View v) {
        final int id = ids[list.getPositionForView(v)];

        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage("Voulez vous supprimer cet abonnement.")
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        UserPreferences pref = new UserPreferences(context);

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("idEntraineur", id);
                            obj.put("idAthlete", pref.readUser().getIdUser());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.DELETE_ABONNEMENT);
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
