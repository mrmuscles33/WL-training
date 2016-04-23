package com.alan.slidingmenu.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alan.slidingmenu.Classe.MyListAdapter;
import com.alan.slidingmenu.Classe.Seance;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.R;
import com.alan.slidingmenu.Serveur.UrlHTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by alanmocaer on 01/02/16.
 */
public class FragmentHistorique extends Fragment {

    public static final String ARG_OBJECT = "object";
    private Context context;

    private MyListAdapter mSchedule;
    private ListView list;
    private String ids[];
    private Seance seance;
    private String dates[];

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        context = (Context) getActivity();

        list = (ListView) getActivity().findViewById(R.id.listeViewHistorique);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;

        //Recuperation des donnees
        UserPreferences pref = new UserPreferences(context);
        User u = pref.readUser();

        JSONObject json = new JSONObject();
        try {
            json.put("idUser", u.getIdUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.HISTORIQUE);
        url.execute(json.toString());

        JSONArray lesSeances = new JSONArray();

        try {
            lesSeances = new JSONArray(url.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Format de la liste

        /*String[] dates = {"15/02/2016", "17/02/2016"};
        String[] noms = {"Alan", "Alan"};*/
        int nbSeance = lesSeances.length();

        dates = new String[nbSeance];
        String noms[] = new String[nbSeance];
        ids = new String[nbSeance];

        for (int i = 0; i < nbSeance; i++) {
            try {
                String date = (String) lesSeances.getJSONObject(i).get("DateSeance");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedCurrentDate = sdf.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                dates[i] = format.format(convertedCurrentDate);

                noms[i] = (String) lesSeances.getJSONObject(i).get("pseudo");
                ids[i] = (String) lesSeances.getJSONObject(i).get("idSeance");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < nbSeance; i++) {
            map = new HashMap<String, String>();
            map.put("date", dates[i]);
            map.put("nom", "by " + noms[i]);
            listItem.add(map);
        }

        mSchedule = new MyListAdapter(context, listItem,
                R.layout.liste_prochaine_seance, new String[]{"date", "nom"}, new int[]{
                R.id.listeViewProchaineDate, R.id.listeViewProchaineName});

        list.setAdapter(mSchedule);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_historique_seance, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }

    public String[] getIds() {
        return ids;
    }

    public String[] getDates() {
        return dates;
    }

    public ListView getList() {
        return list;
    }
}
