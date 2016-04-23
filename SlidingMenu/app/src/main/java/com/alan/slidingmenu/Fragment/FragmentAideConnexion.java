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
public class FragmentAideConnexion extends Fragment {

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_aide_connexion, container, false);
        Bundle args = getArguments();
       /* ((TextView) rootView.findViewById(R.id.TextFragment1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;

    }
}
