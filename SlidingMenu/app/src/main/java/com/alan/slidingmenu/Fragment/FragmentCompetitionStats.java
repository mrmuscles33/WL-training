package com.alan.slidingmenu.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alan.slidingmenu.Activity.CompetitionActivity;
import com.alan.slidingmenu.Activity.IWFActivity;
import com.alan.slidingmenu.Classe.Competition;
import com.alan.slidingmenu.R;

import java.util.ArrayList;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class FragmentCompetitionStats extends Fragment {

    public static final String ARG_OBJECT = "object";
    private CompetitionActivity context;
    private ArrayList<Competition> lesCompet;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        context = (CompetitionActivity) getActivity();

        lesCompet = context.getLesCompet();

        TextView bestArr = (TextView) context.findViewById(R.id.bestArr);
        bestArr.setText("Meilleur arraché : " + bestArr() + " kg");

        TextView bestEpj = (TextView) context.findViewById(R.id.bestEpj);
        bestEpj.setText("Meilleur épaulé jeté : " + bestEPJ() + " kg");

        TextView bestTot = (TextView) context.findViewById(R.id.bestTot);
        bestTot.setText("Meilleur total : " + bestTot() + " kg");

        TextView bestIwfFemme = (TextView) context.findViewById(R.id.bestIwfFille);
        bestIwfFemme.setText("Meilleur total IWF (femme) : " + bestIWFFille() + " pts");

        TextView bestIwfHomme = (TextView) context.findViewById(R.id.bestIwfHomme);
        bestIwfHomme.setText("Meilleur total IWF (homme) : " + bestIWFHomme() + " pts");

        TextView ptsMoyenFemme = (TextView) context.findViewById(R.id.PtsMoyenFille);
        ptsMoyenFemme.setText("Points moyen (femme) : " + ptsMoyenFemme() + " pts");

        TextView ptsMoyenHomme = (TextView) context.findViewById(R.id.PtsMoyenHomme);
        ptsMoyenHomme.setText("Points moyen (homme) : " + ptsMoyenHomme() + " pts");

        TextView fail = (TextView) context.findViewById(R.id.failMoyen);
        fail.setText("Nombre de barre(s) ratée(s) en moyenne : " + failMoyen());

        TextView poidsMoyen = (TextView) context.findViewById(R.id.poidsMoyen);
        poidsMoyen.setText("Poids de corps moyen : " + poidsMoyen());

        TextView poidsMin = (TextView) context.findViewById(R.id.poidsLeger);
        poidsMin.setText("Plus léger : " + poidsMin());

        TextView poidsMax = (TextView) context.findViewById(R.id.poidsLourd);
        poidsMax.setText("Plus lourd : " + poidsMax());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_competition_stats, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }

    public int bestArr() {

        int best = 0;

        for (Competition compet : lesCompet) {
            if (compet.getArr1() > best)
                best = compet.getArr1();
            if (compet.getArr2() > best)
                best = compet.getArr2();
            if (compet.getArr3() > best)
                best = compet.getArr3();
        }

        return best;
    }

    public int bestEPJ() {

        int best = 0;

        for (Competition compet : lesCompet) {
            if (compet.getEpj1() > best)
                best = compet.getEpj1();
            if (compet.getEpj2() > best)
                best = compet.getEpj2();
            if (compet.getEpj3() > best)
                best = compet.getEpj3();
        }

        return best;
    }

    public int bestTot() {

        int best = 0;

        for (Competition compet : lesCompet) {
            /*int maxArr = Math.max(compet.getArr3(), Math.max(compet.getArr2(), compet.getArr1()));
            int maxEpj = Math.max(compet.getEpj3(), Math.max(compet.getEpj2(), compet.getEpj1()));*/

            /*if (maxArr + maxEpj > best)
                best = maxArr + maxEpj;*/

            if (compet.getTotal() > best)
                best = compet.getTotal();
        }

        return best;
    }

    public double bestIWFFille() {

        double best = 0;

        for (Competition compet : lesCompet) {

            double result = 0;

            if (compet.getPoids() <= IWFActivity.B_WOMEN) {
                double x = Math.log10(compet.getPoids() / IWFActivity.B_WOMEN);
                result = Math.pow(10, IWFActivity.A_WOMEN * x * x);
            } else result = 1;
            double sinclairTotal = compet.getTotal() * result;

            if (sinclairTotal > best)
                best = sinclairTotal;
        }

        best = (int) (1000 * best);
        best = best / 1000;

        return best;
    }

    public double bestIWFHomme() {

        double best = 0;

        for (Competition compet : lesCompet) {

            double result = 0;

            if (compet.getPoids() <= IWFActivity.B_MEN) {
                double x = Math.log10(compet.getPoids() / IWFActivity.B_MEN);
                result = Math.pow(10, IWFActivity.A_MEN * x * x);
            } else result = 1;
            double sinclairTotal = compet.getTotal() * result;

            if (sinclairTotal > best)
                best = sinclairTotal;
        }

        best = (int) (1000 * best);
        best = best / 1000;

        return best;

    }

    public double ptsMoyenHomme() {

        double total = 0;

        for (Competition compet : lesCompet) {
            total += compet.getTotal() - 2 * compet.getPoids();
        }

        total = (int) (total / lesCompet.size() * 10);
        total /= 10;

        return total;
    }

    public double ptsMoyenFemme() {
        double total = 0;

        for (Competition compet : lesCompet)
            total += compet.getTotal() - compet.getPoids();

        total = (int) (total / lesCompet.size() * 10);
        total /= 10;

        return total;
    }

    public double failMoyen() {
        double nbFail = 0;

        for (Competition compet : lesCompet) {
            if (compet.getArr1() < 0)
                nbFail++;
            if (compet.getArr2() < 0)
                nbFail++;
            if (compet.getArr3() < 0)
                nbFail++;
            if (compet.getEpj1() < 0)
                nbFail++;
            if (compet.getEpj2() < 0)
                nbFail++;
            if (compet.getEpj3() < 0)
                nbFail++;
        }

        nbFail = (int) (nbFail / lesCompet.size() * 10);
        nbFail /= 10;

        return nbFail;
    }

    public double poidsMoyen() {
        double poids = 0;

        for (Competition compet : lesCompet) {
            poids += compet.getPoids();
        }

        poids = (int) (poids / lesCompet.size() * 100);
        poids /= 100;

        return poids;
    }

    public double poidsMax() {
        double poids = 0;

        for (Competition compet : lesCompet) {
            if (compet.getPoids() > poids)
                poids = compet.getPoids();
        }

        return poids;
    }

    public double poidsMin() {
        double poids = 1000;

        for (Competition compet : lesCompet) {
            if (compet.getPoids() < poids)
                poids = compet.getPoids();
        }

        return poids;
    }

}
