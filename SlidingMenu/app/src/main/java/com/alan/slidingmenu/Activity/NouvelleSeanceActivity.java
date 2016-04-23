package com.alan.slidingmenu.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.Exercice;
import com.alan.slidingmenu.Classe.MyListAdapter;
import com.alan.slidingmenu.Classe.Seance;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupConnexion;
import com.alan.slidingmenu.Popup.PopupExercice;
import com.alan.slidingmenu.Popup.PopupExerciceTitle;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NouvelleSeanceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private Dialog pop;
    private Exercice exo;
    private Seance seance;
    private MyListAdapter mSchedule;
    private ListView list;
    private ArrayList<Exercice> exoDelete;
    private Context context;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvelle_seance);
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

        context = this;

        seance = new Seance();

        exoDelete = new ArrayList();

        Button plus = (Button) findViewById(R.id.deleteExo);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteExo();
            }
        });

        Button moins = (Button) findViewById(R.id.addExo);
        moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(findViewById(R.id.addExo));
                openContextMenu(findViewById(R.id.addExo));
            }
        });

        Button ok = (Button) findViewById(R.id.nouvelleSeanceOk);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UserPreferences pref = new UserPreferences(context);
                User user = pref.readUser();

                if (user == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour rédiger une séance.")
                            .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else {

                    DatePicker dpk = (DatePicker) findViewById(R.id.newSeanceDatePicker);

                    seance.setDate(new Date(dpk.getYear() - 1900, dpk.getMonth(), dpk.getDayOfMonth()));

                    JSONObject json = new JSONObject();

                    try {
                        json.put("dateSeance", seance.getDateSQL());
                        json.put("idUser", user.getIdUser());

                        JSONArray lesExercices = new JSONArray();

                        for (Exercice e : seance.getLesExos()) {
                            JSONObject exo = new JSONObject();

                            exo.put("nomExercice", e.getNom());
                            exo.put("nombreSerie", e.getSerie());
                            exo.put("nombreRep", e.getRep());
                            exo.put("charge", e.getPourc());

                            lesExercices.put(exo);
                        }

                        json.put("lesExercices", lesExercices);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.NOUVELLE_SEANCE);
                    url.execute(json.toString());

                    Intent intent = new Intent(NouvelleSeanceActivity.this, MainActivity.class);
                    intent.putExtra("Seance", seance);
                    startActivity(intent);
                    finish();
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
        //getMenuInflater().inflate(R.menu.nouvelle_seance, menu);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nouvelle_seance, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.autreExo) {
            exo = new Exercice();
            pop = new PopupExerciceTitle(this, exo);
            pop.show();
        } else if (!categorieExercice(item.getItemId())) {

            exo = new Exercice();
            exo.setNom(item.getTitle().toString());

            DatePicker dpk = (DatePicker) findViewById(R.id.newSeanceDatePicker);

            //PAS POUR EXO MAIS SEANCE
            //exo.setDate(new Date(dpk.getYear(),dpk.getMonth(),dpk.getDayOfMonth()));

            pop = new PopupExercice(this, exo);
            pop.show();

        }

        return super.onContextItemSelected(item);
    }

    public boolean categorieExercice(int id) {
        int[] lesCategories = {R.id.arrache,
                R.id.epj,
                R.id.epauleJete,
                R.id.epaule,
                R.id.jete,
                R.id.muscul,
                R.id.bras,
                R.id.cuisses,
                R.id.dorsaux,
                R.id.epaules,
                R.id.lombaires,
                R.id.mollet,
                R.id.pectoraux};

        for (int i : lesCategories) {
            if (id == i)
                return true;
        }

        return false;
    }

    public void addExo() {

        seance.getLesExos().add(exo);

        display();

    }

    public void deleteExo() {
        for (Exercice e : exoDelete) {
            seance.getLesExos().remove(e);
        }

        exoDelete.clear();

        display();
    }

    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        //on récupère la position à l'aide du tag défini dans la classe MyListAdapter
        int position = Integer.parseInt(cb.getTag().toString());

        //System.out.println(position + " / " + list.getCount());

        //position -= list.getFirstVisiblePosition();

        // On récupère l'élément sur lequel on va changer la couleur
        /*View o = list.getChildAt(position).findViewById(
                R.id.blocCheck);*/

        //On change la couleur
        if (cb.isChecked()) {
            //o.setBackgroundColor(Color.RED);
            exoDelete.add(seance.getLesExos().get(position));
        } else {
            //o.setBackgroundColor(0xFF949393);
            exoDelete.remove(seance.getLesExos().get(position));
        }

    }

    private void display() {
        list = (ListView) findViewById(R.id.listView);
        list.setBackgroundColor(0xFF949393);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;

        for (Exercice e : seance.getLesExos()) {
            map = new HashMap<String, String>();
            map.put("Exercice", e.getNom());
            map.put("Serie", Integer.toString(e.getSerie()));
            map.put("Rep", Integer.toString(e.getRep()));
            map.put("Pourc", Integer.toString(e.getPourc()));
            listItem.add(map);
        }

        mSchedule = new MyListAdapter(this.getBaseContext(), listItem,
                R.layout.list_nouvelle_seance, new String[]{"Exercice", "Serie", "Rep", "Pourc"}, new int[]{
                R.id.listeViewExercice, R.id.listeViewSerie, R.id.listeViewRep, R.id.listeViewCharge});

        list.setAdapter(mSchedule);
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
