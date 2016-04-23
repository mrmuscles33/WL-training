package com.alan.slidingmenu.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alan.slidingmenu.Classe.MyListAdapter;
import com.alan.slidingmenu.Classe.User;
import com.alan.slidingmenu.Classe.UserPreferences;
import com.alan.slidingmenu.Popup.PopupAbonnement;
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
public class FragmentProchaineSeance extends Fragment {

    public static final String ARG_OBJECT = "object";
    private AppCompatActivity context;

    private MyListAdapter mSchedule;
    private ListView list;
    //private Seance seance;
    private int ids[];
    private String dates[];
    private String noms[];
    private int posDelete;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        context = (AppCompatActivity) getActivity();

        list = (ListView) getActivity().findViewById(R.id.listeViewProchaine);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;

        //Recuperation des donnees
        final UserPreferences pref = new UserPreferences(context);
        User u = pref.readUser();

        JSONObject json = new JSONObject();
        try {
            json.put("idUser", u.getIdUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.PROCHAINE_SEANCE);
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
        noms = new String[nbSeance];
        ids = new int[nbSeance];

        for (int i = 0; i < nbSeance; i++) {
            try {
                String date = lesSeances.getJSONObject(i).getString("DateSeance");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date convertedCurrentDate = sdf.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                dates[i] = format.format(convertedCurrentDate);

                noms[i] = lesSeances.getJSONObject(i).getString("pseudo");
                ids[i] = lesSeances.getJSONObject(i).getInt("idSeance");
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

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                System.out.println("Supprimer la seance du "+dates[pos]);

                posDelete = pos;

                if(pref.readUser().getPseudo().equals(noms[pos])){
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Voulez vous supprimer cette séance ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    JSONObject json = new JSONObject();
                                    try {
                                        json.put("idSeance",ids[posDelete]);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    UrlHTTPConnection url = new UrlHTTPConnection(context, UrlHTTPConnection.DELETE_SEANCE);
                                    url.execute(json.toString());

                                    context.finish();
                                    context.startActivity(context.getIntent());

                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Attention")
                            .setMessage("Vous ne pouvez pas supprimer cette séance car vous n'etes pas le rédacteur.")
                            .show();
                }

                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_prochaine_seance, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }

    public int[] getIds() {
        return ids;
    }

    public ListView getList() {
        return list;
    }
}
