package com.alan.slidingmenu.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ifigm on 10/03/2016.
 */
public class MinimaHandler extends SQLiteOpenHelper {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "minima.db";
    private static final String TABLE_MINIMA = "Minima";
    private static final String MINIMA_ID = "Id";
    private static final String MINIMA_CATEGORIE = "Categorie";
    private static final String MINIMA_NIVEAUX = "Niveaux";
    private static final String MINIMA_POIDS = "Poids";
    private static final String MINIMA_SEXE = "Sexe";
    private static final String MINIMA_CATEGORIE_POIDS = "CategoriePoids";


    public static final String WORD_TABLE_NAME = "Minima";
    public static final String WORD_TABLE_CREATE =
            "CREATE TABLE " + WORD_TABLE_NAME + " ( " +
                    MINIMA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MINIMA_CATEGORIE +" NVARCHAR(100) NOT NULL, " +
                    MINIMA_NIVEAUX+" NVARCHAR(100) NOT NULL, "+
                    MINIMA_POIDS+" INT NOT NULL, "+
                    MINIMA_CATEGORIE_POIDS+" NVARCHAR(100) NOT NULL, "+
                    MINIMA_SEXE+" BYTE NOT NULL"+
                    "); ";
    public static final String WORD_TABLE_DROP = "DROP TABLE IF EXISTS " + WORD_TABLE_NAME + ";";

    public MinimaHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(WORD_TABLE_CREATE);
        db.execSQL(WORD_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(WORD_TABLE_DROP);
        onCreate(db);
    }

    public void reinitializeTable(SQLiteDatabase db){
        db.execSQL(WORD_TABLE_DROP);
        onCreate(db);
    }
}
