package com.alan.slidingmenu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;

import java.text.DecimalFormat;

public class IWFActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private EditText poidsDeCorps;
    private EditText total;
    private Button calcul;
    private RadioButton check;
    private RadioGroup groupe;
    private CharSequence sexe;
    private TextView textViewafficheIWF;
    private double result;
    private double x;
    private double sinclairTotal;
    public final static double A_MEN = 0.794358141;
    public final static double A_WOMEN = 0.897260740;
    public final static double B_MEN = 174.393;
    public final static double B_WOMEN = 148.026;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iwf);
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


        calcul = (Button) findViewById(R.id.ButtonCalcul);
        groupe = (RadioGroup) findViewById(R.id.RadioGroupCheck);
        poidsDeCorps = (EditText) findViewById(R.id.EditextPoids);
        total = (EditText) findViewById(R.id.EditextTotal);

        textViewafficheIWF = (TextView) findViewById(R.id.textViewAfficheIWF);

        calcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!poidsDeCorps.getText().toString().isEmpty() && !total.getText().toString().isEmpty()) {
                    check = (RadioButton) findViewById(groupe.getCheckedRadioButtonId());
                    sexe = check.getText();
                    calculIWF(sexe, Double.parseDouble(total.getText().toString()), Double.parseDouble(poidsDeCorps.getText().toString()));
                }
            }
        });
    }

    public void calculIWF(CharSequence sexe, double total, double poids) {

        if (poids <= B_MEN && sexe.equals("Homme")) {
            x = Math.log10(poids / B_MEN);
            result = Math.pow(10, A_MEN * x * x);
        } else if (poids <= B_WOMEN && sexe.equals("Femme")) {
            x = Math.log10(poids / B_WOMEN);
            result = Math.pow(10, A_WOMEN * x * x);
        } else result = 1;
        sinclairTotal = total * result;
        afficheIWF(sinclairTotal);
    }

    private void afficheIWF(double result) {
        DecimalFormat df = new DecimalFormat("###.###");
        textViewafficheIWF.setText(df.format(result));
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
        //getMenuInflater().inflate(R.menu.iw, menu);
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
