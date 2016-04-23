package com.alan.slidingmenu.Classe;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alanmocaer on 27/01/16.
 */
public class UserPreferences {


    private Context context;

    public UserPreferences(Context pContext) {
        this.context = pContext;
    }

    public User readUser() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userString = preferences.getString("User", "");

        User u = User.USER_VIDE;

        if(!userString.isEmpty()) {
            try {
                JSONObject json = new JSONObject(userString);

                int id = json.getInt("idUser");
                String email = (String) json.get("email");
                String pseudo = (String) json.get("pseudo");
                String password = (String) json.get("password");

                u = new User(id, email, pseudo, password);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (u.getIdUser()==0)
            u = User.USER_VIDE;

        return u;
    }

    public void writeUser(User u) {

        JSONObject json = new JSONObject();

        try {
            json.put("idUser", u.getIdUser());
            json.put("email", u.getEmail());
            json.put("pseudo", u.getPseudo());
            json.put("password", u.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("User", json.toString());

        editor.apply();
    }
}
