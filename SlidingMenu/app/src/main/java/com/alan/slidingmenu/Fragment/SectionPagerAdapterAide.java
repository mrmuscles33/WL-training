package com.alan.slidingmenu.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class SectionPagerAdapterAide extends FragmentPagerAdapter {

    private FragmentAideConnexion connexion = new FragmentAideConnexion();
    private FragmentAideNewSeance newSeance = new FragmentAideNewSeance();
    private FragmentAideSeance seance = new FragmentAideSeance();
    private FragmentAideCompetition competition = new FragmentAideCompetition();
    private FragmentAideExercice exercice = new FragmentAideExercice();

    public SectionPagerAdapterAide(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return connexion;
            case 1:
                return newSeance;
            case 2:
                return seance;
            case 3:
                return competition;
            case 4:
            default:
                return exercice;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Connexion";
            case 1:
                return "Nouvelle séance";
            case 2:
                return "Séance";
            case 3:
                return "Compétition";
            case 4:
            default:
                return "Exercices";
        }
    }

}
