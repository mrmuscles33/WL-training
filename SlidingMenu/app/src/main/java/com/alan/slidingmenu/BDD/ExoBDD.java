package com.alan.slidingmenu.BDD;

/**
 * Created by Georg on 07/11/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExoBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "exo.db";
    private static final String TABLE_EXO = "ExoDico";
    private static final String EXO_ID = "Id";
    private static final String EXO_NAME = "Name";
    private static final String EXO_CATEGORIE = "Categorie";
    private static final String EXO_DESCRIPTION = "Description";
    private static final String EXO_OBJECTIF = "Objectif";
    private static final String EXO_VARIANTE = "Variante";
    private static final String EXO_IMAGE_1 = "Image_1";
    private static final String EXO_IMAGE_2 = "Image_2";
    private static final String EXO_IMAGE_3 = "Image_3";
    private static final String EXO_IMAGE_4 = "Image_4";
    private static final String EXO_IMAGE_5 = "Image_5";
    private static final int NUM_EXO_NAME = 0;
    private static final int NUM_EXO_CATEGORIE = 1;
    private static final int NUM_EXO_DESCRIPTION = 2;
    private static final int NUM_EXO_OBJECTIF = 3;
    private static final int NUM_EXO_VARIANTE = 4;
    private static final int NUM_EXO_IMAGE_1 = 5;
    private static final int NUM_EXO_IMAGE_2 = 6;
    private static final int NUM_EXO_IMAGE_3 = 7;
    private static final int NUM_EXO_IMAGE_4 = 8;
    private static final int NUM_EXO_IMAGE_5 = 9;
    private static final int NUM_EXO_CATEGORIE_SELECT = 0;

    private SQLiteDatabase bdd;

    private WordHandler maBaseSQLite;

    public ExoBDD(Context context) {
        //On créer la BDD et sa table
        maBaseSQLite = new WordHandler(context, NOM_BDD, null, VERSION_BDD);
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

    public WordHandler getMaBaseSQLite() {
        return maBaseSQLite;
    }

    public long insertWord(ExoDico exo) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(EXO_NAME, exo.getStringExo());
        values.put(EXO_CATEGORIE, exo.getCategorie());
        values.put(EXO_DESCRIPTION, exo.getDescription());
        values.put(EXO_OBJECTIF, exo.getObjectif());
        values.put(EXO_VARIANTE, exo.getVariante());
        values.put(EXO_IMAGE_1, exo.getImage1());
        values.put(EXO_IMAGE_2, exo.getImage2());
        values.put(EXO_IMAGE_3, exo.getImage3());
        values.put(EXO_IMAGE_4, exo.getImage4());
        values.put(EXO_IMAGE_5, exo.getImage5());
        //on insère l'objet dans la BDD via le ContentValue
        return bdd.insert(TABLE_EXO, null, values);
    }


    public List<ExoDico> getExo() {
        List<ExoDico> list = new ArrayList<ExoDico>();
        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD
        Cursor c = bdd.query(TABLE_EXO, new String[]{EXO_NAME, EXO_CATEGORIE, EXO_DESCRIPTION,EXO_OBJECTIF,EXO_VARIANTE,EXO_IMAGE_1,EXO_IMAGE_2,EXO_IMAGE_3,EXO_IMAGE_4,EXO_IMAGE_5}, EXO_ID + " IS NOT NULL", null, null, null, null);

        if (c.moveToFirst()) {
            list.add(cursorToWord(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToWord(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }



    public List<String> getCategorie() {
        List<String> list = new ArrayList<String>();
        //Récupère dans un Cursor les valeur correspondant à un mot contenu dans la BDD
        Cursor c = bdd.query(true,TABLE_EXO, new String[]{EXO_CATEGORIE}, EXO_ID + " IS NOT NULL",null,null,null,null,null);


        if (c.moveToFirst()) {
            list.add(cursorToCategorie(c));
        }

        while (c.moveToNext()) {
            list.add(cursorToCategorie(c));
        }
        //On ferme le cursor
        c.close();
        return list;
    }
    //Cette méthode permet de convertir un cursor en un score
    private String cursorToCategorie(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {
            String categorie = c.getString(NUM_EXO_CATEGORIE_SELECT);
            //On créé un score


            //On retourne le score
            return categorie;
        }
    }


    private ExoDico cursorToWord(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        } else {

            //On créé un score
            ExoDico exo = new ExoDico(c.getString(NUM_EXO_NAME),
                    c.getString(NUM_EXO_CATEGORIE),
                    c.getString(NUM_EXO_DESCRIPTION),
                    c.getString(NUM_EXO_OBJECTIF),
                    c.getString(NUM_EXO_VARIANTE),
                    c.getString(NUM_EXO_IMAGE_1),
                    c.getString(NUM_EXO_IMAGE_2),
                    c.getString(NUM_EXO_IMAGE_3),
                    c.getString(NUM_EXO_IMAGE_4),
                    c.getString(NUM_EXO_IMAGE_5));

            //On retourne le score
            return exo;
        }
    }
}