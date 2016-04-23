package com.alan.slidingmenu.Classe;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alanmocaer on 27/01/16.
 */
public class RecordsPreferences {

    private Context context;

    public RecordsPreferences(Context pContext) {
        this.context = pContext;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Records readRecords() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recordsString = preferences.getString("Records", "");

        Records r = new Records();

        if (!recordsString.isEmpty()) {
            try {
                JSONObject json = new JSONObject(recordsString);

                r.setArr1(json.getInt("arr1"));
                r.setArr2(json.getInt("arr2"));
                r.setArrD(json.getInt("arrD"));
                r.setArrT(json.getInt("arrT"));

                r.setEpj1(json.getInt("epj1"));
                r.setEpj2(json.getInt("epj2"));
                r.setEpjD(json.getInt("epjD"));
                r.setEpjT(json.getInt("epjT"));

                r.setSD1(json.getInt("SD1"));
                r.setSD2(json.getInt("SD2"));
                r.setSD3(json.getInt("SD3"));

                r.setSN1(json.getInt("SN1"));
                r.setSN2(json.getInt("SN2"));
                r.setSN3(json.getInt("SN3"));

                r.setDC1(json.getInt("DC1"));
                r.setDI1(json.getInt("DI1"));
                r.setDD1(json.getInt("DD1"));
                r.setDN1(json.getInt("DN1"));

                r.setBic1(json.getInt("Bic1"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return r;
    }

    public void writeRecords(Records r) {

        JSONObject json = new JSONObject();

        try {
            json.put("arr1", r.getArr1());
            json.put("arr2", r.getArr2());
            json.put("arrD", r.getArrD());
            json.put("arrT", r.getArrT());

            json.put("epj1", r.getEpj1());
            json.put("epj2", r.getEpj2());
            json.put("epjD", r.getEpjD());
            json.put("epjT", r.getEpjT());

            json.put("SD1", r.getSD1());
            json.put("SD2", r.getSD2());
            json.put("SD3", r.getSD3());

            json.put("SN1", r.getSN1());
            json.put("SN2", r.getSN2());
            json.put("SN3", r.getSN3());

            json.put("DC1", r.getDC1());
            json.put("DI1", r.getDI1());
            json.put("DD1", r.getDD1());
            json.put("DN1", r.getDN1());

            json.put("Bic1", r.getBic1());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Records", json.toString());

        editor.apply();
    }
}
