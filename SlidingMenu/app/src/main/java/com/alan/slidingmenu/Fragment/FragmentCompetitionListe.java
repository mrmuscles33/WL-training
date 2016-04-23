package com.alan.slidingmenu.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alan.slidingmenu.Activity.CompetitionActivity;
import com.alan.slidingmenu.Classe.Competition;
import com.alan.slidingmenu.Classe.MyListAdapter;
import com.alan.slidingmenu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class FragmentCompetitionListe extends Fragment {

    public static final String ARG_OBJECT = "object";
    private CompetitionActivity context;

    private MyListAdapter mSchedule;
    private ListView list;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        context = (CompetitionActivity) getActivity();

        list = (ListView) getActivity().findViewById(R.id.listeViewCompetition);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;

        String[] lesLibelles = new String[context.getLesCompet().size()];
        String[] lesLieux = new String[context.getLesCompet().size()];
        String[] lesDates = new String[context.getLesCompet().size()];
        double[] lesPoids = new double[context.getLesCompet().size()];
        int[] lesA1 = new int[context.getLesCompet().size()];
        int[] lesA2 = new int[context.getLesCompet().size()];
        int[] lesA3 = new int[context.getLesCompet().size()];
        int[] lesEPJ1 = new int[context.getLesCompet().size()];
        int[] lesEPJ2 = new int[context.getLesCompet().size()];
        int[] lesEPJ3 = new int[context.getLesCompet().size()];

        for (int i = 0; i < context.getLesCompet().size(); i++) {

            Competition compet = context.getLesCompet().get(i);

            lesLibelles[i] = compet.getLibelle();
            lesLieux[i] = compet.getLieu();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            lesDates[i] = sdf.format(compet.getDate());
            lesPoids[i] = compet.getPoids();
            lesA1[i] = compet.getArr1();
            lesA2[i] = compet.getArr2();
            lesA3[i] = compet.getArr3();
            lesEPJ1[i] = compet.getEpj1();
            lesEPJ2[i] = compet.getEpj2();
            lesEPJ3[i] = compet.getEpj3();
        }

        for (int i = 0; i < context.getLesCompet().size(); i++) {
            map = new HashMap<String, String>();
            map.put("Libelle", lesLibelles[i]);
            map.put("Lieu", lesLieux[i]);
            map.put("Date", lesDates[i]);
            map.put("PoidsDeCorps", Double.toString(lesPoids[i]));
            map.put("Arr1", Integer.toString(lesA1[i]));
            map.put("Arr2", Integer.toString(lesA2[i]));
            map.put("Arr3", Integer.toString(lesA3[i]));
            map.put("EPJ1", Integer.toString(lesEPJ1[i]));
            map.put("EPJ2", Integer.toString(lesEPJ2[i]));
            map.put("EPJ3", Integer.toString(lesEPJ3[i]));
            listItem.add(map);
        }

        mSchedule = new MyListAdapter(context, listItem,
                R.layout.liste_competition, new String[]{"Libelle", "Lieu", "Date", "PoidsDeCorps", "Arr1", "Arr2", "Arr3", "EPJ1", "EPJ2", "EPJ3"}, new int[]{
                R.id.listeCompetLibelle, R.id.listeCompetLieu, R.id.listeCompetDate, R.id.listeCompetPoids, R.id.listeCompetA1, R.id.listeCompetA2, R.id.listeCompetA3, R.id.listeCompetEPJ1, R.id.listeCompetEPJ2, R.id.listeCompetEPJ3,});

        list.setAdapter(mSchedule);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_competition_liste, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }

    public ListView getList() {
        return list;
    }
}
