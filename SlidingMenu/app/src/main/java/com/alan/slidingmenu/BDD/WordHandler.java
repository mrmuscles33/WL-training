package com.alan.slidingmenu.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Georg on 07/11/2015.
 */
public class WordHandler extends SQLiteOpenHelper {
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


    public static final String WORD_TABLE_NAME = "ExoDico";
    public static final String WORD_TABLE_CREATE =
            "CREATE TABLE " + WORD_TABLE_NAME + " ( " +
                    EXO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EXO_NAME + " NVARCHAR(100) NOT NULL, " +
                    EXO_CATEGORIE +" NVARCHAR(100) NOT NULL, " +
                    EXO_DESCRIPTION+ " NVARCHAR(100) NOT NULL," +
                    EXO_OBJECTIF+ " NVARCHAR(100) ," +
                    EXO_VARIANTE+ " NVARCHAR(100)," +
                    EXO_IMAGE_1+ " NVARCHAR(100) NOT NULL," +
                    EXO_IMAGE_2+ " NVARCHAR(100)NOT NULL," +
                    EXO_IMAGE_3+ " NVARCHAR(100)," +
                    EXO_IMAGE_4+ " NVARCHAR(100)," +
                    EXO_IMAGE_5+ " NVARCHAR(100)" +
                    "); ";
    public static final String WORD_TABLE_DROP = "DROP TABLE IF EXISTS " + WORD_TABLE_NAME + ";";

    public WordHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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



