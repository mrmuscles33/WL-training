package com.alan.slidingmenu.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class SectionPagerAdapterCompetition extends FragmentPagerAdapter {

    private FragmentCompetitionListe liste = new FragmentCompetitionListe();
    private FragmentCompetitionProgres progres = new FragmentCompetitionProgres();
    private FragmentCompetitionStats stats = new FragmentCompetitionStats();

    public SectionPagerAdapterCompetition(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return liste;
            case 1:
                return  progres;
            case 2:
            default:
                return stats;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Compétitions";
            case 1:
                return "Progrès";
            case 2:
            default:
                return "Statistiques";
        }
    }

}
