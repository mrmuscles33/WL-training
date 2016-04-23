package com.alan.slidingmenu.BDD;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ifigm on 10/03/2016.
 */
public class Minima implements Parcelable {
    private String categorie;
    private String niveaux;
    private int poids;
    private int sexe;
    private String categoriePoids;

    public Minima(String categorie, String niveaux, int poids,int sexe, String categoriePoids) {
        this.categorie = categorie;
        this.niveaux = niveaux;
        this.poids = poids;
        this.sexe =  sexe;
        this.categoriePoids = categoriePoids;

    }

    protected Minima(Parcel in) {
        categorie = in.readString();
        niveaux = in.readString();
        poids = in.readInt();
        sexe = in.readByte();
        categoriePoids = in.readString();
    }

    public static final Creator<Minima> CREATOR = new Creator<Minima>() {
        @Override
        public Minima createFromParcel(Parcel in) {
            return new Minima(in);
        }

        @Override
        public Minima[] newArray(int size) {
            return new Minima[size];
        }
    };

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(String niveaux) {
        this.niveaux = niveaux;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }


    public String getCategoriePoids() {
        return categoriePoids;
    }

    public void setCategoriePoids(String categoriePoids) {
        this.categoriePoids = categoriePoids;
    }

    public int getSexe() {
        return sexe;
    }

    public void setSexe(byte sexe) {
        this.sexe = sexe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categorie);
        dest.writeString(niveaux);
        dest.writeInt(poids);
        dest.writeInt(sexe);
        dest.writeString(categoriePoids);
    }
}
