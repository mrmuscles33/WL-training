package com.alan.slidingmenu.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alan.slidingmenu.Fragment.FragmentHistorique;
import com.alan.slidingmenu.Fragment.FragmentProchaineSeance;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {

    private FragmentProchaineSeance prochaineSeance = new FragmentProchaineSeance();
    private FragmentHistorique historique = new FragmentHistorique();

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return prochaineSeance;
            case 1:
            default:
                return historique;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Prochaine séance";
            case 1:
            default:
                return "Historique";
        }
    }

}
