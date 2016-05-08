package com.alan.slidingmenu.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifigm on 10/03/2016.
 */
public class MinimaBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "minima.db";
    private static final String TABLE_MINIMA = "Minima";
    private static final String MINIMA_ID = "Id";
    private static final String MINIMA_CATEGORIE = "Categorie";
    private static final String MINIMA_NIVEAUX = "Niveaux";
    private static final String MINIMA_POIDS = "Poids";
    private static final String MINIMA_SEXE = "Sexe";
    private static final String MINIMA_CATEGORIE_POIDS = "CategoriePoids";
    private static final int MINIMA_CATEGORIE_NUM = 0;
    private static final int MINIMA_NIVEAUX_NUM = 1;
    private static final int MINIMA_POIDS_NUM = 2;
    private static final int MINIMA_SEXE_NUM = 3;
    private static final int MINIMA_CATEGORIE_POIDS_NUM = 4;


    private SQLiteDatabase bdd;

    private MinimaHandler maBaseSQLite;

    public MinimaBDD(Context context) {
        //On créer la BDD et sa table
        maBaseSQLite = new MinimaHandler(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public MinimaHandler getMaBaseSQLite() {
        return maBaseSQLite;
    }

    public long insertMinima(Minima min) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(MINIMA_CATEGORIE, min.getCategorie());
        values.put(MINIMA_NIVEAUX, min.getNiveaux());
        values.put(MINIMA_POIDS, min.getPoids());
        values.put(MINIMA_CATEGORIE_POIDS, min.getCategoriePoids());
        values.put(MINIMA_SEXE, min.getSexe());


        //on insère l'objet dans la BDD via le ContentValue
        return bdd.insert(TABLE_MINIMA, null, values);
    }


    public List<Minima> getMinima() {
        List<Minima> list = new ArrayList<Minima>();
        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD
        Cursor c = bdd.query(TABLE_MINIMA, new String[]{MINIMA_CATEGORIE, MINIMA_NIVEAUX, MINIMA_POIDS, MINIMA_CATEGORIE_POIDS, MINIMA_SEXE}, MINIMA_ID + " IS NOT NULL", null, null, null, null);

        if (c.moveToFirst()) {
            list.add(cursorToMinima(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToMinima(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }

    public List<Minima> getMinima(int sexe, String age, String poids) {
        List<Minima> list = new ArrayList<Minima>();
        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD
        Cursor c = bdd.query(TABLE_MINIMA, new String[]{MINIMA_CATEGORIE, MINIMA_NIVEAUX, MINIMA_POIDS, MINIMA_CATEGORIE_POIDS, MINIMA_SEXE}, MINIMA_ID + " IS NOT NULL AND " + MINIMA_SEXE + " =" + sexe + " AND " + MINIMA_CATEGORIE + " ='" + age + "' AND " + MINIMA_CATEGORIE_POIDS + " ='" + poids + "'", null, null, null, null);

        if (c.moveToFirst()) {
            list.add(cursorToMinima(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToMinima(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }

    private Minima cursorToMinima(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {

            //On créé un score
            Minima minima = new Minima(c.getString(MINIMA_CATEGORIE_NUM),
                    c.getString(MINIMA_NIVEAUX_NUM),
                    c.getInt(MINIMA_POIDS_NUM),
                    (byte) c.getInt(MINIMA_SEXE_NUM),
                    c.getString(MINIMA_CATEGORIE_POIDS_NUM));


            //On retourne le score
            return minima;
        }
    }

    public List getCategorie(int sexe) {
        List<String> list = new ArrayList<String>();

        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD
        //Cursor c = bdd.query(true,TABLE_MINIMA, new String[]{MINIMA_CATEGORIE}, MINIMA_ID + " IS NOT NULL",null,null,null,null,null);
        Cursor c = bdd.query(true, TABLE_MINIMA, new String[]{MINIMA_CATEGORIE}, MINIMA_ID + " IS NOT NULL AND " + MINIMA_SEXE + "= " + sexe, null, null, null, null, null);
        if (c.moveToFirst()) {
            list.add(cursorToMinimaCategorie(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToMinimaCategorie(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }

    private String cursorToMinimaCategorie(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {
            return c.getString(0);

        }
    }

    public List getCategoriePoids(int sexe, String categorie) {
        List<String> list = new ArrayList<String>();
        Log.e("test", sexe + " " + categorie.toString() + " " + bdd.toString());

        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD

        Cursor c = bdd.query(true, TABLE_MINIMA, new String[]{MINIMA_CATEGORIE_POIDS}, MINIMA_ID + " IS NOT NULL AND " + MINIMA_SEXE + "= " + sexe + " AND " + MINIMA_CATEGORIE + " = '" + categorie + "'", null, null, null, null, null);
        if (c.moveToFirst()) {
            list.add(cursorToMinimaCategoriePoids(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToMinimaCategoriePoids(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }

    private String cursorToMinimaCategoriePoids(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {
            return c.getString(0);

        }
    }

    public List<Integer> getPoids(int sexe, String categorie, String categoriePoids) {
        List<Integer> list = new ArrayList<Integer>();


        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD

        Cursor c = bdd.query(true, TABLE_MINIMA, new String[]{MINIMA_POIDS}, MINIMA_ID + " IS NOT NULL AND " + MINIMA_SEXE + "= " + sexe + " AND " + MINIMA_CATEGORIE + " = '" + categorie + "'" + " AND " + MINIMA_CATEGORIE_POIDS + " ='" + categoriePoids + "'", null, null, null, MINIMA_POIDS + " ASC", null);

        if (c.moveToFirst()) {
            list.add(cursorToMinimaPoids(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToMinimaPoids(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }

    private int cursorToMinimaPoids(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return 0;
        } else {
            return c.getInt(0);

        }
    }

}
