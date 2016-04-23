package com.alan.slidingmenu.Classe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alan on 14/10/15.
 */

public class Exercice implements Parcelable {

    private String nom;
    private int serie;
    private int rep;
    private int pourc;
    private int[] lesSeries;
    private int id;

    public Exercice() {
        super();
        nom = "";
        serie = 0;
        rep = 0;
        pourc = 0;
        id = 0;
    }

    public Exercice(Parcel source) {
        id = source.readInt();
        nom = source.readString();
        serie = source.readInt();
        rep = source.readInt();
        pourc = source.readInt();

        lesSeries = new int[serie];

        for (int i = 0; i < serie; i++)
            lesSeries[i] = source.readInt();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;

        lesSeries = new int[serie];

        for (int i = 0; i < serie; i++)
            lesSeries[i] = 0;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public int getPourc() {
        return pourc;
    }

    public void setPourc(int pourc) {
        this.pourc = pourc;
    }

    public int[] getLesSeries() {
        return lesSeries;
    }

    public void setLesSeries(int[] lesSeries) {
        this.lesSeries = lesSeries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeInt(serie);
        dest.writeInt(rep);
        dest.writeInt(pourc);

        for (int i = 0; i < serie; i++)
            dest.writeInt(lesSeries[i]);
    }

    public static final Parcelable.Creator<Exercice> CREATOR = new Parcelable.Creator<Exercice>() {

        @Override
        public Exercice createFromParcel(Parcel source) {
            return new Exercice(source);
        }

        @Override
        public Exercice[] newArray(int size) {
            return new Exercice[size];
        }
    };
}
