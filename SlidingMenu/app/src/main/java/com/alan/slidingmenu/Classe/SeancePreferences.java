package com.alan.slidingmenu.Classe;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alanmocaer on 27/01/16.
 */
public class SeancePreferences {

    private Context context;

    public SeancePreferences(Context pContext) {
        this.context = pContext;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Seance readSeance() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String seanceString = preferences.getString("Seance", "");

        Seance s = new Seance();

        if (!seanceString.isEmpty()) {
            try {
                JSONObject json = new JSONObject(seanceString);

                int id = json.getInt("idSeance");
                s.setId(id);

                JSONArray array = json.getJSONArray("lesExercices");

                for (int i = 0; i < array.length(); i++) {
                    Exercice e = new Exercice();
                    e.setId(array.getJSONObject(i).getInt("idExercice"));
                    e.setNom(array.getJSONObject(i).getString("Nom"));
                    e.setSerie(array.getJSONObject(i).getInt("Serie"));
                    e.setRep(array.getJSONObject(i).getInt("Rep"));
                    e.setPourc(array.getJSONObject(i).getInt("Pourc"));

                    JSONArray lesSeries = array.getJSONObject(i).getJSONArray("lesSeries");

                    for (int j = 0; j < lesSeries.length(); j++)
                        e.getLesSeries()[j] = lesSeries.getInt(j);

                    s.getLesExos().add(e);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Seance en cours - id : "+s.getId());

        return s;
    }

    public void writeSeance(Seance s) {

        System.out.println("Enregistrement de la seance en cours");

        JSONObject json = new JSONObject();

        try {
            json.put("idSeance", s.getId());

            JSONArray lesExercices = new JSONArray();

            for (Exercice e : s.getLesExos()) {
                JSONObject exo = new JSONObject();

                exo.put("idExercice",e.getId());
                exo.put("Nom", e.getNom());
                exo.put("Serie", e.getSerie());
                exo.put("Rep", e.getRep());
                exo.put("Pourc", e.getPourc());

                JSONArray lesSeries = new JSONArray();

                for (int i : e.getLesSeries())
                    lesSeries.put(i);

                exo.put("lesSeries", lesSeries);

                lesExercices.put(exo);

            }

            json.put("lesExercices", lesExercices);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Seance", json.toString());

        editor.apply();
    }
}
