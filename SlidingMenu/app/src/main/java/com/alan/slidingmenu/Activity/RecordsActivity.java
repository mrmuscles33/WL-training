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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alan.slidingmenu.Classe.Records;
import com.alan.slidingmenu.Classe.RecordsPreferences;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;

public class RecordsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private NavigationView navigationView;

    private AppCompatActivity context;

    private EditText arr1;
    private EditText arr2;
    private EditText arrD;
    private EditText arrT;
    private EditText epj1;
    private EditText epj2;
    private EditText epjD;
    private EditText epjT;
    private EditText SD1;
    private EditText SD2;
    private EditText SD3;
    private EditText SN1;
    private EditText SN2;
    private EditText SN3;
    private EditText DC1;
    private EditText DI1;
    private EditText DD1;
    private EditText DN1;
    private EditText bic1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

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

        arr1 = (EditText) findViewById(R.id.recordArr1);
        arr2 = (EditText) findViewById(R.id.recordArr2);
        arrD = (EditText) findViewById(R.id.recordArrD);
        arrT = (EditText) findViewById(R.id.recordArrT);
        epj1 = (EditText) findViewById(R.id.recordEpj1);
        epj2 = (EditText) findViewById(R.id.recordEpj2);
        epjD = (EditText) findViewById(R.id.recordEpjD);
        epjT = (EditText) findViewById(R.id.recordEpjT);
        SD1 = (EditText) findViewById(R.id.recordSD1);
        SD2 = (EditText) findViewById(R.id.recordSD2);
        SD3 = (EditText) findViewById(R.id.recordSD3);
        SN1 = (EditText) findViewById(R.id.recordSN1);
        SN2 = (EditText) findViewById(R.id.recordSN2);
        SN3 = (EditText) findViewById(R.id.recordSN3);
        DC1 = (EditText) findViewById(R.id.recordDC1);
        DI1 = (EditText) findViewById(R.id.recordDI1);
        DD1 = (EditText) findViewById(R.id.recordDD1);
        DN1 = (EditText) findViewById(R.id.recordDN1);
        bic1 = (EditText) findViewById(R.id.recordBiceps1);

        RecordsPreferences prefRecords = new RecordsPreferences(this);
        Records r = prefRecords.readRecords();

        arr1.setText(Integer.toString(r.getArr1()));
        arr2.setText(Integer.toString(r.getArr2()));
        arrD.setText(Integer.toString(r.getArrD()));
        arrT.setText(Integer.toString(r.getArrT()));

        epj1.setText(Integer.toString(r.getEpj1()));
        epj2.setText(Integer.toString(r.getEpj2()));
        epjD.setText(Integer.toString(r.getEpjD()));
        epjT.setText(Integer.toString(r.getEpjT()));

        SD1.setText(Integer.toString(r.getSD1()));
        SD2.setText(Integer.toString(r.getSD2()));
        SD3.setText(Integer.toString(r.getSD3()));

        SN1.setText(Integer.toString(r.getSN1()));
        SN2.setText(Integer.toString(r.getSN2()));
        SN3.setText(Integer.toString(r.getSN3()));

        DC1.setText(Integer.toString(r.getDC1()));
        DI1.setText(Integer.toString(r.getDI1()));
        DD1.setText(Integer.toString(r.getDD1()));
        DN1.setText(Integer.toString(r.getDN1()));

        bic1.setText(Integer.toString(r.getBic1()));
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
        getMenuInflater().inflate(R.menu.records, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saveRecords) {
            Records r = new Records();

            r.setArr1(Integer.parseInt(arr1.getText().toString()));
            r.setArr2(Integer.parseInt(arr2.getText().toString()));
            r.setArrD(Integer.parseInt(arrD.getText().toString()));
            r.setArrT(Integer.parseInt(arrT.getText().toString()));

            r.setEpj1(Integer.parseInt(epj1.getText().toString()));
            r.setEpj2(Integer.parseInt(epj2.getText().toString()));
            r.setEpjD(Integer.parseInt(epjD.getText().toString()));
            r.setEpjT(Integer.parseInt(epjT.getText().toString()));

            r.setSD1(Integer.parseInt(SD1.getText().toString()));
            r.setSD2(Integer.parseInt(SD2.getText().toString()));
            r.setSD3(Integer.parseInt(SD3.getText().toString()));

            r.setSN1(Integer.parseInt(SN1.getText().toString()));
            r.setSN2(Integer.parseInt(SN2.getText().toString()));
            r.setSN3(Integer.parseInt(SN3.getText().toString()));

            r.setDC1(Integer.parseInt(DC1.getText().toString()));
            r.setDI1(Integer.parseInt(DI1.getText().toString()));
            r.setDD1(Integer.parseInt(DD1.getText().toString()));
            r.setDN1(Integer.parseInt(DN1.getText().toString()));

            r.setBic1(Integer.parseInt(bic1.getText().toString()));

            RecordsPreferences prefRecords = new RecordsPreferences(this);
            prefRecords.writeRecords(r);

            context.finish();
            context.startActivity(context.getIntent());

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
