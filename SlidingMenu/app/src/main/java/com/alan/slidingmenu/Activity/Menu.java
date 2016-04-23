package com.alan.slidingmenu.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupAbonnement;
import com.alan.slidingmenu.Popup.PopupConnexion;
import com.alan.slidingmenu.R;

/**
 * Created by alanmocaer on 03/02/16.
 */
public class Menu {

    private Context context;

    public static void goTo(final Context context, int id) {
        Intent intent = null;
        UserPreferences pref = new UserPreferences(context);

        switch (id) {
            case R.id.nav_rediger_seance:
                intent = new Intent(context.getApplicationContext(), NouvelleSeanceActivity.class);
                break;
            case R.id.nav_seance:
                intent = new Intent(context.getApplicationContext(), SeanceTabhost.class);
                break;
            case R.id.nav_iwf:
                intent = new Intent(context.getApplicationContext(), IWFActivity.class);
                break;
            case R.id.nav_competition:
                if (pref.readUser() == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour accéder aux compétitions.")
                            .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else {
                    intent = new Intent(context.getApplicationContext(), CompetitionActivity.class);
                }
                break;
            case R.id.nav_abonnement:
                if (pref.readUser() == User.USER_VIDE) {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous devez vous connectez pour vous abonner.")
                            .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    PopupConnexion pop = new PopupConnexion(context);
                                    pop.show();
                                }
                            })
                            .show();
                } else {
                    PopupAbonnement pop = new PopupAbonnement(context);
                    pop.show();
                }
                break;
            case R.id.nav_connexion:
                if (User.USER_VIDE == pref.readUser()) {
                    PopupConnexion popupConnexion = new PopupConnexion(context);
                    popupConnexion.show();
                } else {
                    pref.writeUser(User.USER_VIDE);
                    Toast.makeText(context, "Déconnecté", Toast.LENGTH_SHORT).show();
                    ((NomHeader) context).setNomHeader(User.USER_VIDE);
                }
                break;
            case R.id.nav_chargement:
                intent = new Intent(context.getApplicationContext(), ChargementActivity.class);
                break;
            case R.id.nav_exo:
                intent = new Intent(context.getApplicationContext(), DicoExerciceActivity.class);
                break;
            case R.id.nav_compte:
                intent = new Intent(context.getApplicationContext(), CompteActivity.class);
                break;
            case R.id.nav_records:
                intent = new Intent(context.getApplicationContext(), RecordsActivity.class);
                break;
            case R.id.nav_minimas:
                intent = new Intent(context.getApplicationContext(), MinimaActivity.class);
                break;
            case R.id.nav_credit :
                intent = new Intent(context.getApplicationContext(), CreditActivity.class);
                break;
            case R.id.nav_aide:
                intent = new Intent(context.getApplicationContext(), AideActivity.class);
                break;
            case R.id.nav_accueil:
                intent = new Intent(context.getApplicationContext(), MainActivity.class);
                break;
        }

        if (intent != null)
            context.startActivity(intent);
    }
}
