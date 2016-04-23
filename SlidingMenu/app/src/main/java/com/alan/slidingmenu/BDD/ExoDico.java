package com.alan.slidingmenu.BDD;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.EventLogTags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 07/12/2015.
 */
public class ExoDico implements Parcelable {
    private String stringExo;
    private String categorie;
    private String description;
    private String objectif;
    private String variante;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;


    public ExoDico(String stringExo, String categorie, String description, String objectif, String variante,String image1,String image2, String image3,String image4, String image5) {

        this.stringExo = stringExo;
        this.categorie = categorie;
        this.description = description;
        this.objectif = objectif;
        this.variante = variante;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }

    protected ExoDico(Parcel in) {
        stringExo = in.readString();
        categorie = in.readString();
        description = in.readString();
        objectif = in.readString();
        variante = in.readString();

       image1 = in.readString();
        image2 = in.readString();
        image3 = in.readString();
        image4 = in.readString();
        image5 = in.readString();
    }

    public static final Creator<ExoDico> CREATOR = new Creator<ExoDico>() {
        @Override
        public ExoDico createFromParcel(Parcel in) {
            return new ExoDico(in);
        }

        @Override
        public ExoDico[] newArray(int size) {
            return new ExoDico[size];
        }
    };

    public String getStringExo() {
        return stringExo;
    }


    public String getCategorie() {
        return categorie;
    }


    public String getDescription() {
        return description;
    }


    public String getObjectif() {
        return objectif;
    }


    public String getVariante() {
        return variante;
    }


    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }
    public String getImage5() {
        return image5;
    }
    @Override
    public String toString() {
        return "ExoDico{" +
                "stringExo='" + stringExo + '\'' +
                ", categorie='" + categorie + '\'' +
                ", description='" + description + '\'' +
                ", objectif='" + objectif + '\'' +
                ", variante='" + variante + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stringExo);
        dest.writeString(categorie);
        dest.writeString(description);
        dest.writeString(objectif);
        dest.writeString(variante);
        dest.writeString(image1);
        dest.writeString(image2);
        dest.writeString(image3);
        dest.writeString(image4);
        dest.writeString(image5);

    }



}
