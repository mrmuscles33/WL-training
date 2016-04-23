package com.alan.slidingmenu.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alan.slidingmenu.Activity.CompetitionActivity;
import com.alan.slidingmenu.Classe.Competition;
import com.alan.slidingmenu.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class FragmentCompetitionProgres extends Fragment {

    public static final String ARG_OBJECT = "object";
    private CompetitionActivity context;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        context = (CompetitionActivity) getActivity();

        ArrayList<Competition> lesCompet = context.getLesCompet();

        int min = 1000, max = 0;

        for (Competition compet : lesCompet) {

            int minArr = Math.min(Math.abs(compet.getArr3()), Math.min(Math.abs(compet.getArr2()), Math.abs(compet.getArr1())));
            int minEpj = Math.min(Math.abs(compet.getEpj3()), Math.min(Math.abs(compet.getEpj2()), Math.abs(compet.getEpj1())));

            if ((int) Math.min(compet.getPoids(), Math.min(minArr, minEpj)) < min)
                min = (int) Math.min(compet.getPoids(), Math.min(minArr, minEpj));
            if (min < 0)
                min = 0;

            int maxArr = Math.max(compet.getArr3(), Math.max(compet.getArr2(), compet.getArr1()));
            int maxEpj = Math.max(compet.getEpj3(), Math.max(compet.getEpj2(), compet.getEpj1()));

            if (Math.max((int) compet.getPoids(), (maxArr + maxEpj)) > max)
                max = Math.max((int) compet.getPoids(), (maxArr + maxEpj));

            System.out.print(compet.getDate().toString() + " max A : " + maxArr + ", max E : " + maxEpj + ", max : " + max);
        }

        if (lesCompet.size() > 0) {
            GraphView graph = (GraphView) getActivity().findViewById(R.id.graph);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(min - 5);
            graph.getViewport().setMaxY(max + 5);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(lesCompet.size() - 1);
            //graph.setBackgroundColor(0xFFFFFF);


        /*LineGraphSeries<DataPoint> arrache = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 60),
                new DataPoint(1, 63),
                new DataPoint(2, 67),
                new DataPoint(3, 65),
                new DataPoint(4, 70)
        });
        arrache.setColor(Color.RED);

        LineGraphSeries<DataPoint> epj = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 75),
                new DataPoint(1, 81),
                new DataPoint(2, 87),
                new DataPoint(3, 92),
                new DataPoint(4, 88)
        });
        epj.setColor(Color.BLUE);

        LineGraphSeries<DataPoint> total = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 135),
                new DataPoint(1, 144),
                new DataPoint(2, 154),
                new DataPoint(3, 157),
                new DataPoint(4, 158)
        });
        total.setColor(Color.BLACK);*/

            DataPoint[] lesArraches = new DataPoint[lesCompet.size()];
            DataPoint[] lesEPJ = new DataPoint[lesCompet.size()];
            DataPoint[] lesTotaux = new DataPoint[lesCompet.size()];
            DataPoint[] lesPoids = new DataPoint[lesCompet.size()];

            int a = 0;

        for (int i = lesCompet.size() - 1; i >= 0; i--) {

            Competition compet = lesCompet.get(i);

            int arr = Math.max(compet.getArr3(), Math.max(compet.getArr2(), compet.getArr1()));
            lesArraches[lesCompet.indexOf(compet)] = new DataPoint(a, arr);

            int epj = Math.max(compet.getEpj3(), Math.max(compet.getEpj2(), compet.getEpj1()));
            lesEPJ[lesCompet.indexOf(compet)] = new DataPoint(a, epj);

            int tot = arr + epj;
            lesTotaux[lesCompet.indexOf(compet)] = new DataPoint(a, tot);

            lesPoids[lesCompet.indexOf(compet)] = new DataPoint(a, compet.getPoids());

            a++;
        }

            LineGraphSeries<DataPoint> arrache = new LineGraphSeries<DataPoint>(lesArraches);
            LineGraphSeries<DataPoint> epj = new LineGraphSeries<DataPoint>(lesEPJ);
            LineGraphSeries<DataPoint> total = new LineGraphSeries<DataPoint>(lesTotaux);
            LineGraphSeries<DataPoint> poids = new LineGraphSeries<DataPoint>(lesPoids);

            arrache.setColor(Color.RED);
            epj.setColor(Color.BLUE);
            total.setColor(Color.BLACK);
            poids.setColor(Color.WHITE);

            arrache.setDrawDataPoints(true);
            epj.setDrawDataPoints(true);
            total.setDrawDataPoints(true);
            poids.setDrawDataPoints(true);

            graph.addSeries(arrache);
            graph.addSeries(epj);
            graph.addSeries(total);
            graph.addSeries(poids);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_competition_progres, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }
}
