package com.alan.slidingmenu.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alan.slidingmenu.BDD.ExoBDD;
import com.alan.slidingmenu.BDD.ExoDico;
import com.alan.slidingmenu.BDD.Minima;
import com.alan.slidingmenu.BDD.MinimaBDD;
import com.alan.slidingmenu.Classe.Seance;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupConnexion;
import com.alan.slidingmenu.Popup.PopupInscription;
import com.alan.slidingmenu.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NomHeader {

    private Seance seance;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        if (user == User.USER_VIDE) {
            new AlertDialog.Builder(this)
                    .setTitle("Inscription")
                    .setMessage("Avantages :\n\t- Rédiger et réaliser des séances" +
                            "\n\t- S'abonner à des entraineurs pour suivre leurs séances" +
                            "\n\t- Et bien d'autres choses ...")
                    .setPositiveButton("Inscription", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            PopupInscription pop = new PopupInscription(MainActivity.this);
                            pop.show();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .show();
        }

        setNomHeader(user);
        seance = new Seance();

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {
            seance = (Seance) b.get("Seance");
        }

        Button newSeance = (Button) findViewById(R.id.buttonRediger);
        newSeance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NouvelleSeanceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button seanceBtn = (Button) findViewById(R.id.buttonSeance);
        seanceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeanceTabhost.class);
                intent.putExtra("Seance", seance);
                startActivity(intent);
            }
        });

        Button reocrds = (Button) findViewById(R.id.buttonRecord);
        reocrds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
                startActivity(intent);
            }
        });

        Button exercice = (Button) findViewById(R.id.buttonExercice);
        exercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DicoExerciceActivity.class);
                startActivity(intent);
            }
        });

        List<ExoDico> exos = new ArrayList<>();

        exos.add(new ExoDico("Flexion",
                "Arraché",
                "Le départ se fait du sol en position d'arrache. L'athlète amène la barre au dessus de la tête après une impulsion à mi-cuisse" +
                        "à la suite du tirage pour effectuer un saut accompagné d'un deplacement lateral des pieds." +
                        "La position de reception se fait en flexion. C'est le mouvement technique effectué en competition.",
                "Acquerir et développer le mouvement complet",
                "",
                "img356",
                "img357",
                "img358",
                "img359",
                null));
        exos.add(new ExoDico("Debout",
                "Arraché",
                "Le mouvement est identique à l'arrache flexion, cependant la position" +
                        " de reception se fait très haute", "Chercher de la hauteur dans l'extension et de la vitesse dans le mouvement.",
                "Arraché debout suspension, Arrache debout puissance",
                "img356",
                "img357",
                "img358",
                "img361",
                null));
        exos.add(new ExoDico("Suspension",
                "Arraché", "Le mouvement est identique à l'arrache flexion, cependant le depart se fait a mi-cuisse, ou dessous le genoux.",
                "Ameliorer la trajectoire du mouvement, controler le tirage et etre sous tension au depart",
                "Arrache suspension basse (ou haute), Arrache debout suspension, Arrache suspension puissance, Arrache suspension force",
                "img362",
                "img357",
                "img358",
                "img359",
                null));
        exos.add(new ExoDico("Plot",
                "Arraché",
                "Le mouvement est identique à l'arraché flexion, cependant le départ se fait a mi-cuisse, la barre posée sur les plots",
                "Améliorer la trajectoire du mouvement, ne pas être sous tension au départ et favoriser le placement",
                "Arraché debout plots, Arraché force plots, Arraché puissance plots",
                "img363",
                "img364",
                "img365",
                "img366",
                null));
        exos.add(new ExoDico("Force",
                "Arraché",
                "Le départ se fait du sol en position d'arrache. L'athlète amene la barre au-dessus de la tête apres une impulsion à mi-cuisse à la suite du tirage et on termine le mouvement jambe tendu sur la pointe des pieds. Après l'extension aucune flexion de jambe ne doit être effectuée",
                "Ameliorer la trajectoire du mouvement et obliger de la hauteur dans le mouvement.",
                "Arraché force suspension, Arraché force plots",
                "img356",
                "img357",
                "img358",
                "img367",
                null));
        exos.add(new ExoDico("Puissance",
                "Arraché",
                "Le mouvement est identique à l'arraché flexion, cependant il n'y a pas de déplacement latéral des pieds. Attention à partir avec les pieds en position de réception.",
                "Améliorer la trajectoire du mouvement et la réception (éviter une barre devant ou derrière)",
                "Arraché debout puissance, Arraché puissance suspension",
                "img356",
                "img357",
                "img358",
                "img359",
                null));
        exos.add(new ExoDico("Chute",
                "Arraché",
                "L'athlète est debout avec la barre sur la nuque. Il doit passer en position de réception après une faible impulsion ou simplement en montant sur la pointe des pieds",
                "Améliorer le passage sous la barre, la position de réception et l'action des bras dans le mouvement",
                "",
                "img368",
                "img369",
                "img370",
                null,
                null));
        exos.add(new ExoDico("Passage",
                "Arraché",
                "L'athlète est debout avec la barre au bassin, bras tendu. Il doit passer en flexion sous la barre après avoir monter les trapèzes et tirer sur la barre.",
                "Améliorer le passage sous la barre , la position de réception et l'action des bras dans le mouvement",
                "",
                "img371",
                "img358",
                "img359",
                null,
                null));

        //EPAULE
        exos.add(new ExoDico("Flexion",
                "Epaulé"
                , "Le départ se fait du sol en position d'épaulé. L'athlète amène la barre sur les clavicules après une implulsion à mi-cuisse à la suite du tirage our effectuer un saut accompagné d'un déplacement latéral des pieds. La position de réception se fait en flexion. C'est le mouvement ''technique'' effectué en compétition"
                , "Acquérir et développer le mouvement complet"
                , ""
                , "img373"
                , "img374"
                , "img375"
                , "img376"
                , null));
        exos.add(new ExoDico("Debout"
                , "Epaulé"
                , "Le départ se fait du sol en position d'épaulé. L'hatlète amène la barre sur les clavicules après une impulsion à mi-cuisse à la suite du tirage pour effectuer un saut accompagné d'un déplacement latéral des pieds. La position de réception se fait en flexion. C'est le mouvement '' technique ''  effectué en compétition"
                , "Chercher de la hauteur et de l'explosivité dans l'extension et de la vitesse dans le mouvement"
                , "Epaulé debout suspension, Epaulé debout puissance, Epaulé debout plot"
                , "img373"
                , "img374"
                , "img375"
                , "img378"
                , null));
        exos.add(new ExoDico("Suspension"
                , "Epaulé"
                , "Le mouvement est identique à l'épaulé flexion cependant le départ se fait à mi-cuisse"
                , "Améliorer la trajectoire du mouvement, côntroler le tirage et être sous tension au départ"
                , "épaulé suspension basse (ou haute), épaulé debout suspension, épaulé suspension puissance"
                , "img379"
                , "img375"
                , "img376"
                , null
                , null));
        exos.add(new ExoDico("Plots"
                , "Epaulé"
                , "Le mouvement est identique à l'épaulé suspension, cependant la barre est posée sur des plots."
                , "Améliorer la tajectoire du mouvement, ne pas être sous tension au départ et favoriser le placement."
                , "Epaulé debout plots, Epaulé force plots, Epaulé puissance plots"
                , "img380"
                , "img381"
                , "img382"
                , "img383"
                , null));
        exos.add(new ExoDico("Force"
                , "Epaulé"
                , "Le départ se fait du sol en position d'épaulé. L'athlète amène la barre sur les clavicules après une impulsion à mi-cuisse à la suite du tirage, et termine le mouvement jambe sur la pointe des pieds. Après l'extension aucune flexion de jambe ne doit être effectuée."
                , "Améliorer la trajectoire du mouvement et obliger de la hauteur dans le mouvement"
                , "Epaulé force suspension, Epaulé force plots"
                , "img373"
                , "img374"
                , "img375"
                , "img384"
                , null));
        exos.add(new ExoDico("Puissance"
                , "Epaulé"
                , "Le mouvement est identique à l'épaulé flexion, cependant il n'y a pas de déplacement latéral des pieds. Attention à partir avec les pieds en position de réception. "
                , "Améliorer la trajectoire du mouvement"
                , "Epaulé debout puissance, Epaulé puissance suspension"
                , "img373"
                , "img374"
                , "img375"
                , "img376"
                , null));
        exos.add(new ExoDico("Passage"
                , "Epaulé"
                , "L'athlète est debout avec la barre au bassin, tendu. Il doit passer en flexion sous la barre, en position de réception d'épaulé après avoir monter les trapèzes et les coudes et tirer sur la barre."
                , "Eméliorer le passage sous la barre, la position de réception et l'action des bras dans le mouvement."
                , ""
                , "img385",
                "img375",
                "img376",
                null
                , null));

        //JETE
        exos.add(new ExoDico("Fente",
                "Jeté",
                "Une fois la barre épaulé, l'athlète effectue une légère flexion (appel) permettant de donner de la puissance dans les jambes. Après un saut vertical il doit déplacer le jambes, une vers l'avant qui doit être à l’équerre et l'autre vers l'arrière légèrement fléchie (ou cassée)",
                "Acquérir et développer le mouvement complet",
                "Jeté fente avec temps d'arrêt",
                "img386",
                "img387",
                "img388",
                "img389",
                null));

        exos.add(new ExoDico("Debout",
                "Jeté",
                "Le mouvement est identique au jeté fente, cependant les déplacement des jambes ne se fait par vers avant/arrière mais de façon latérale",
                "Chercher de la hauteur et de l'explosivité dans l'extension, de la vitesse dans le mouvement et améliorer la trajectoire",
                "Jeté debout avec temps d'arrêt",
                "img386",
                "img387",
                "img388",
                "img391",
                null));

        exos.add(new ExoDico("Force",
                "Jeté",
                "La barre est posée sur les clavicules, l'athlète amène la barre au dessus de la tête sans déplacement de pieds, et termine le geste jambe tendu, sur la pointe des pieds. Après l'impulsion aucune flexion de jambe ne doit être effectuée",
                "Améliorer la trajectoire du mouvement",
                "",
                "img386",
                "img387",
                "img388",
                "img392",
                null));

        exos.add(new ExoDico("Puissance",
                "Jeté",
                "Le mouvement est identique au jeté debout cependant il n'y a pas de déplacement latéral des pieds",
                "Favoriser la poussée, la trajectoire du mouvement et la vitesse dans le passage sous la barre",
                "",
                "img386",
                "img387",
                "img388",
                "img391",
                null));

        exos.add(new ExoDico("Chute",
                "Jeté",
                "La barre est posée sur la nuque de l'athlète. Il doit monter sur la pointe des pieds puis chuter sous la barre pour passer en position de réception de jeté fente",
                "Améliorer la position de réception et la stabilité",
                "",
                "img393",
                "img394",
                "img395",
                null,
                null));

        ExoBDD bdd = new ExoBDD(getApplicationContext());


        bdd.open();
        bdd.getMaBaseSQLite().reinitializeTable(bdd.getBDD());

        for (ExoDico e : exos) {
            bdd.insertWord(e);
        }
        bdd.close();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                // MINIMAS
                List<Minima> lesMinimas = new ArrayList<>();

                // FEMME
                // CADETTE 1
                lesMinimas.add(new Minima("Cadette 1", "Débutante", 20, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 25, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 35, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 45, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 50, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 60, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 70, 0, "-40 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 80, 0, "-40 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 25, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 30, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 40, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 50, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 60, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 70, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 80, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 90, 0, "-44 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 30, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 35, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 45, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 55, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 65, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 75, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 85, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 95, 0, "-48 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 35, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 45, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 55, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 65, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 75, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 85, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 95, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 105, 0, "-53 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 40, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 50, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 60, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 70, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 80, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 90, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 100, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 115, 0, "-58 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 45, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 55, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 65, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 75, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 85, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 95, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 110, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 125, 0, "-63 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 50, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 60, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 70, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 80, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 90, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 105, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 120, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 135, 0, "-69 kg"));

                lesMinimas.add(new Minima("Cadette 1", "Débutante", 60, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Départementale", 70, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Régionale", 80, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Interrégionale", 95, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Fédérale", 105, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Nationale", 115, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale B", 130, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 1", "Internationale A", 145, 0, "+69 kg"));

                // CADETTE 2
                lesMinimas.add(new Minima("Cadette 2", "Débutante", 30, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 40, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 50, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 60, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 70, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 80, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 90, 0, "-44 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 100, 0, "-44 kg"));

                lesMinimas.add(new Minima("Cadette 2", "Débutante", 35, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 45, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 55, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 65, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 75, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 85, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 95, 0, "-48 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 110, 0, "-48 kg"));

                lesMinimas.add(new Minima("Cadette 2", "Débutante", 45, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 55, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 65, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 75, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 85, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 95, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 105, 0, "-53 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 120, 0, "-53 kg"));

                lesMinimas.add(new Minima("Cadette 2", "Débutante", 50, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 60, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 70, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 80, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 90, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 100, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 115, 0, "-58 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 130, 0, "-58 kg"));

                lesMinimas.add(new Minima("Cadette 2", "Débutante", 55, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 65, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 75, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 85, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 95, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 110, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 125, 0, "-63 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 140, 0, "-63 kg"));

                lesMinimas.add(new Minima("Cadette 2", "Débutante", 60, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 70, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 80, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 90, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 105, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 120, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 135, 0, "-69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 150, 0, "-69 kg"));

                lesMinimas.add(new Minima("Cadette 2", "Débutante", 70, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Départementale", 80, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Régionale", 95, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Interrégionale", 105, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Fédérale", 115, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Nationale", 130, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale B", 145, 0, "+69 kg"));
                lesMinimas.add(new Minima("Cadette 2", "Internationale A", 160, 0, "+69 kg"));

                // JUNIOR
                lesMinimas.add(new Minima("Junior", "Débutante", 45, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 55, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 65, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 75, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 85, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 95, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 110, 0, "-48 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 125, 0, "-48 kg"));

                lesMinimas.add(new Minima("Junior", "Débutante", 55, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 65, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 75, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 85, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 95, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 105, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 120, 0, "-53 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 135, 0, "-53 kg"));

                lesMinimas.add(new Minima("Junior", "Débutante", 60, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 70, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 80, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 90, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 100, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 115, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 130, 0, "-58 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 150, 0, "-58 kg"));

                lesMinimas.add(new Minima("Junior", "Débutante", 65, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 75, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 85, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 95, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 110, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 125, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 140, 0, "-63 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 160, 0, "-63 kg"));

                lesMinimas.add(new Minima("Junior", "Débutante", 70, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 80, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 90, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 105, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 120, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 135, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 150, 0, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 170, 0, "-69 kg"));

                lesMinimas.add(new Minima("Junior", "Débutante", 80, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 95, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 105, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 115, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 130, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 145, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 160, 0, "-75 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 180, 0, "-75 kg"));

                lesMinimas.add(new Minima("Junior", "Débutante", 90, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Départementale", 100, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Régionale", 110, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégionale", 125, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Fédérale", 140, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Nationale", 150, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale B", 170, 0, "+75 kg"));
                lesMinimas.add(new Minima("Junior", "Internationale A", 190, 0, "+75 kg"));

                // SENIOR
                lesMinimas.add(new Minima("Sénior", "Débutante", 55, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 65, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 75, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 85, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 95, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 110, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 125, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 140, 0, "-48 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 155, 0, "-48 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutante", 65, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 75, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 85, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 95, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 105, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 120, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 140, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 160, 0, "-53 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 175, 0, "-53 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutante", 70, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 80, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 90, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 100, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 115, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 130, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 150, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 170, 0, "-58 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 190, 0, "-58 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutante", 75, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 85, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 95, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 110, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 125, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 140, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 160, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 180, 0, "-63 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 200, 0, "-63 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutante", 80, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 90, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 105, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 120, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 135, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 150, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 170, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 190, 0, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 205, 0, "-69 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutante", 95, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 105, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 115, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 130, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 145, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 160, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 180, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 195, 0, "-75 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 210, 0, "-75 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutante", 100, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Départementale", 110, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Régionale", 125, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégionale", 140, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédérale", 150, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Nationale", 170, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale B", 190, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Internationale A", 210, 0, "+75 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 225, 0, "+75 kg"));


                // HOMME
                // CADET 1
                lesMinimas.add(new Minima("Cadet 1", "Débutant", 35, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 50, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 60, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 75, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 90, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 105, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 115, 1, "-45 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 130, 1, "-45 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 40, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 55, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 65, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 80, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 95, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 110, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 120, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 135, 1, "-50 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 50, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 70, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 85, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 100, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 115, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 130, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 145, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 170, 1, "-56 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 75, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 95, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 105, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 120, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 135, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 150, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 170, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 190, 1, "-62 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 85, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 105, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 115, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 130, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 150, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 170, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 190, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 210, 1, "-69 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 90, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 110, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 130, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 150, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 170, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 185, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 200, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 220, 1, "-77 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 100, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 120, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 140, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 160, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 180, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 200, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 220, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 240, 1, "-85 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 110, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 130, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 145, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 165, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 185, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 210, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 225, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 250, 1, "-94 kg"));

                lesMinimas.add(new Minima("Cadet 1", "Débutant", 120, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Départemental", 140, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Régional", 155, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Interrégional", 175, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "Fédéral", 195, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "National", 220, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International B", 235, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 1", "International A", 260, 1, "+94 kg"));

                // CADET 2
                lesMinimas.add(new Minima("Cadet 2", "Débutant", 45, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 65, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 80, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 95, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 110, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 120, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 135, 1, "-50 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 150, 1, "-50 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 65, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 85, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 100, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 115, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 130, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 145, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 170, 1, "-56 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 190, 1, "-56 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 85, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 105, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 120, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 135, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 150, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 170, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 190, 1, "-62 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 210, 1, "-62 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 95, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 115, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 130, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 150, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 170, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 190, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 210, 1, "-69 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 230, 1, "-69 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 110, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 130, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 150, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 170, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 185, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 200, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 220, 1, "-77 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 250, 1, "-77 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 120, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 140, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 160, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 180, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 200, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 220, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 240, 1, "-85 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 260, 1, "-85 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 125, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 145, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 165, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 185, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 210, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 225, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 250, 1, "-94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 280, 1, "-94 kg"));

                lesMinimas.add(new Minima("Cadet 2", "Débutant", 135, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Départemental", 155, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Régional", 175, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Interrégional", 195, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "Fédéral", 220, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "National", 235, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International B", 260, 1, "+94 kg"));
                lesMinimas.add(new Minima("Cadet 2", "International A", 290, 1, "+94 kg"));

                // JUNIOR
                lesMinimas.add(new Minima("Junior", "Débutant", 80, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 100, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 115, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 130, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 145, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "National", 170, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 190, 1, "-56 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 210, 1, "-56 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 90, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 120, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 135, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 150, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 170, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "National", 190, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 210, 1, "-62 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 230, 1, "-62 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 110, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 130, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 150, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 170, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 190, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "National", 220, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 240, 1, "-69 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 260, 1, "-69 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 130, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 150, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 170, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 190, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 210, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "National", 240, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 260, 1, "-77 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 285, 1, "-77 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 145, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 165, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 185, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 205, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 225, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "National", 250, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 280, 1, "-85 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 300, 1, "-85 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 150, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 170, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 190, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 215, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 235, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "National", 260, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 290, 1, "-94 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 310, 1, "-94 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 155, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 175, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 195, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 220, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 245, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "National", 270, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 300, 1, "-105 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 325, 1, "-105 kg"));

                lesMinimas.add(new Minima("Junior", "Débutant", 165, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "Départemental", 185, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "Régional", 205, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "Interrégional", 225, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "Fédéral", 250, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "National", 280, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "International B", 310, 1, "+105 kg"));
                lesMinimas.add(new Minima("Junior", "International A", 330, 1, "+105 kg"));

                // SENIOR
                lesMinimas.add(new Minima("Sénior", "Débutant", 95, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 115, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 130, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 145, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 170, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 190, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 210, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 225, 1, "-56 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 240, 1, "-56 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 120, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 135, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 150, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 170, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 190, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 210, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 230, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 255, 1, "-62 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 270, 1, "-62 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 130, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 150, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 170, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 190, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 220, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 240, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 260, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 275, 1, "-69 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 290, 1, "-69 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 150, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 170, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 190, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 210, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 240, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 265, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 285, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 305, 1, "-77 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 320, 1, "-77 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 165, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 185, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 205, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 225, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 250, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 280, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 300, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 325, 1, "-85 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 245, 1, "-85 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 170, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 190, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 215, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 235, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 260, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 290, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 310, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 330, 1, "-94 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 355, 1, "-94 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 175, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 195, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 220, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 245, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 270, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 300, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 325, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 345, 1, "-105 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 365, 1, "-105 kg"));

                lesMinimas.add(new Minima("Sénior", "Débutant", 185, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "Départemental", 205, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "Régional", 225, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "Interrégional", 250, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "Fédéral", 280, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "National", 310, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "International B", 330, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "International A", 355, 1, "+105 kg"));
                lesMinimas.add(new Minima("Sénior", "Olympique", 375, 1, "+105 kg"));

                //Minima master homme
                lesMinimas.add(new Minima("M35", 155, 1, "-56 kg", "Monde"));
                lesMinimas.add(new Minima("M35",137,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M35", 127, 1, "-56 kg", "France"));

                lesMinimas.add(new Minima("M35",172,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M35",152,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M35",142,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M35",187,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M35",167,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M35",157,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M35",202,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M35",182,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M35",172,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M35",215,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M35",192,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M35",182,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M35",227,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M35",202,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M35",192,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M35",237,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M35",210,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M35",200,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M35",245,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M35",217,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M35",207,1,"+105 kg","France"));

                lesMinimas.add(new Minima("M40",147,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M40",130,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M40",120,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M40",162,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M40",145,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M40",135,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M40",177,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M40",160,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M40",150,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M40",192,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M40",172,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M40",162,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M40",205,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M40",182,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M40",172,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M40",215,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M40",192,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M40",182,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M40",225,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M40",200,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M40",190,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M40",232,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M40",207,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M40",197,1,"+105 kg","France"));



                lesMinimas.add(new Minima("M45",140,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M45",125,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M45",115,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M45",155,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M45",137,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M45",127,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M45",170,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M45",150,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M45",140,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M45",185,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M45",165,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M45",155,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M45",195,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M45",175,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M45",165,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M45",205,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M45",182,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M45",172,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M45",212,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M45",190,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M45",180,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M45",222,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M45",197,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M45",187,1,"+105 kg","France"));


                lesMinimas.add(new Minima("M50",130,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M50",115,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M50",105,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M50",142,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M50",127,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M50",117,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M50",157,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M50",140,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M50",130,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M50",170,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M50",150,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M50",140,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M50",180,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M50",160,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M50",150,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M50",190,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M50",167,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M50",157,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M50",197,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M50",175,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M50",165,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M50",205,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M50",182,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M50",172,1,"+105 kg","France"));


                lesMinimas.add(new Minima("M55",115,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M55",102,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M55",92,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M55",127,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M55",112,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M55",102,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M55",140,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M55",125,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M55",115,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M55",152,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M55",135,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M55",125,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M55",162,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M55",142,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M55",132,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M55",170,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M55",150,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M55",140,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M55",177,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M55",157,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M55",147,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M55",182,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M55",165,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M55",155,1,"+105 kg","France"));



                lesMinimas.add(new Minima("M60",105,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M60",92,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M60",82,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M60",117,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M60",102,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M60",92,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M60",127,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M60",112,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M60",102,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M60",137,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M60",122,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M60",112,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M60",147,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M60",130,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M60",120,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M60",155,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M60",137,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M60",127,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M60",160,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M60",142,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M60",132,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M60",167,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M60",150,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M60",140,1,"+105 kg","France"));



                lesMinimas.add(new Minima("M65",92,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M65",80,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M65",70,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M65",102,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M65",90,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M65",80,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M65",112,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M65",97,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M65",87,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M65",120,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M65",107,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M65",97,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M65",127,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M65",112,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M65",102,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M65",135,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M65",120,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M65",110,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M65",140,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M65",122,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M65",112,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M65",145,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M65",127,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M65",117,1,"+105 kg","France"));




                lesMinimas.add(new Minima("M70",75,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M70",67,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M70",57,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M70",82,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M70",75,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M70",65,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M70",90,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M70",82,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M70",72,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M70",97,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M70",90,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M70",80,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M70",102,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M70",95,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M70",85,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M70",107,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M70",100,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M70",90,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M70",112,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M70",102,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M70",92,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M70",117,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M70",107,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M70",97,1,"+105 kg","France"));



                lesMinimas.add(new Minima("M75",67,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M75",62,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M75",52,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M75",75,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M75",67,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M75",57,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M75",82,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M75",75,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M75",65,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M75",87,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M75",82,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M75",72,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M75",95,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M75",87,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M75",77,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M75",97,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M75",90,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M75",80,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M75",102,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M75",95,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M75",85,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M75",107,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M75",100,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M75",90,1,"+105 kg","France"));




                lesMinimas.add(new Minima("M80",55,1,"-56 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-56 kg","Europe"));
                lesMinimas.add(new Minima("M80",50,1,"-56 kg","France"));

                lesMinimas.add(new Minima("M80",55,1,"-62 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-62 kg","Europe"));
                lesMinimas.add(new Minima("M80",55,1,"-62 kg","France"));

                lesMinimas.add(new Minima("M80",60,1,"-69 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-69 kg","Europe"));
                lesMinimas.add(new Minima("M80",60,1,"-69 kg","France"));

                lesMinimas.add(new Minima("M80",65,1,"-77 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-77 kg","Europe"));
                lesMinimas.add(new Minima("M80",70,1,"-77 kg","France"));

                lesMinimas.add(new Minima("M80",70,1,"-85 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-85 kg","Europe"));
                lesMinimas.add(new Minima("M80",75,1,"-85 kg","France"));

                lesMinimas.add(new Minima("M80",72,1,"-94 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-94 kg","Europe"));
                lesMinimas.add(new Minima("M80",75,1,"-94 kg","France"));

                lesMinimas.add(new Minima("M80",77,1,"-105 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"-105 kg","Europe"));
                lesMinimas.add(new Minima("M80",80,1,"-105 kg","France"));

                lesMinimas.add(new Minima("M80",80,1,"+105 kg","Monde"));
                lesMinimas.add(new Minima("M80",-1,1,"+105 kg","Europe"));
                lesMinimas.add(new Minima("M80",85,1,"+105 kg","France"));


                //Master femme
                lesMinimas.add(new Minima("F35",82,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F35",70,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F35",60,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F35",90,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F35",72,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F35",60,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F35",90,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F35",72,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F35",62,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F35",95,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F35",77,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F35",67,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F35",102,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F35",85,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F35",75,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F35",107,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F35",87,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F35",77,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F35",112,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F35",95,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F35",85,0,"+75 kg","France"));





                lesMinimas.add(new Minima("F40",80,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F40",65,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F40",55,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F40",85,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F40",70,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F40",60,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F40",90,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F40",72,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F40",62,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F40",95,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F40",75,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F40",65,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F40",97,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F40",80,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F40",80,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F40",100,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F40",82,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F40",72,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F40",105,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F40",90,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F40",80,0,"+75 kg","France"));






                lesMinimas.add(new Minima("F45",82,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F45",70,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F45",60,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F45",90,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F45",72,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F45",60,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F45",90,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F45",72,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F45",62,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F45",95,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F45",77,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F45",67,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F45",90,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F45",75,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F45",65,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F45",92,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F45",77,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F45",67,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F45",97,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F45",85,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F45",75,0,"+75 kg","France"));




                lesMinimas.add(new Minima("F50",67,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F50",60,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F50",50,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F50",72,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F50",62,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F50",52,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F50",75,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F50",65,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F50",55,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F50",77,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F50",70,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F50",60,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F50",82,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F50",72,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F50",62,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F50",85,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F50",75,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F50",65,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F50",90,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F50",82,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F50",72,0,"+75 kg","France"));






                lesMinimas.add(new Minima("F55",60,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F55",55,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F55",45,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F55",65,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F55",57,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F55",47,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F55",67,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F55",62,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F55",52,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F55",70,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F55",65,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F55",55,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F55",75,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F55",67,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F55",57,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F55",77,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F55",70,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F55",60,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F55",82,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F55",77,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F55",67,0,"+75 kg","France"));



                lesMinimas.add(new Minima("F60",55,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F60",52,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F60",42,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F60",57,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F60",55,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F60",45,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F60",62,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F60",57,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F60",47,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F60",65,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F60",60,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F60",50,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F60",67,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F60",62,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F60",52,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F60",70,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F60",65,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F60",55,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F60",72,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F60",67,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F60",57,0,"+75 kg","France"));





                lesMinimas.add(new Minima("F65",50,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F65",50,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F65",40,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F65",52,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F65",42,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F65",42,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F65",55,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F65",55,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F65",45,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F65",57,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F65",57,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F65",47,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F65",60,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F65",60,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F65",50,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F65",62,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F65",62,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F65",52,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F65",65,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F65",65,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F65",55,0,"+75 kg","France"));


                lesMinimas.add(new Minima("F70",46,0,"-48 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"-48 kg","Europe"));
                lesMinimas.add(new Minima("F70",38,0,"-48 kg","France"));

                lesMinimas.add(new Minima("F70",48,0,"-53 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"-53 kg","Europe"));
                lesMinimas.add(new Minima("F70",40,0,"-53 kg","France"));

                lesMinimas.add(new Minima("F70",50,0,"-58 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"-58 kg","Europe"));
                lesMinimas.add(new Minima("F70",42,0,"-58 kg","France"));

                lesMinimas.add(new Minima("F70",53,0,"-63 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"-63 kg","Europe"));
                lesMinimas.add(new Minima("F70",45,0,"-63 kg","France"));

                lesMinimas.add(new Minima("F70",56,0,"-69 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"-69 kg","Europe"));
                lesMinimas.add(new Minima("F70",48,0,"-69 kg","France"));

                lesMinimas.add(new Minima("F70",58,0,"-75 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"-75 kg","Europe"));
                lesMinimas.add(new Minima("F70",50,0,"-75 kg","France"));

                lesMinimas.add(new Minima("F70",61,0,"+75 kg","Monde"));
                lesMinimas.add(new Minima("F70",-1,0,"+75 kg","Europe"));
                lesMinimas.add(new Minima("F70",52,0,"+75 kg","France"));


                MinimaBDD bddMinima = new MinimaBDD(getApplicationContext());


                bddMinima.open();
                if((bddMinima.getMinima())==null) {
                    bddMinima.getMaBaseSQLite().reinitializeTable(bddMinima.getBDD());

                    for (Minima m : lesMinimas) {
                        bddMinima.insertMinima(m);
                    }
                }
                bddMinima.close();
            }
        });
        t.start();
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
        //getMenuInflater().inflate(R.menu.main, menu);
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

        /*if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        /*Intent intent = null;

        switch (id){
            case R.id.nav_rediger_seance :
                intent = new Intent(getApplicationContext(),NouvelleSeanceActivity.class);
                break;
            case R.id.nav_seance :
                intent = new Intent(getApplicationContext(),SeanceTabhost.class);
                break;
            case R.id.nav_iwf:
                intent = new Intent(getApplicationContext(), IWFActivity.class);
                break;
        }

        startActivity(intent);*/

        com.alan.slidingmenu.Activity.Menu.goTo(this, id);

        //finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        UserPreferences pref = new UserPreferences(this);
        User user = pref.readUser();

        setNomHeader(user);
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
