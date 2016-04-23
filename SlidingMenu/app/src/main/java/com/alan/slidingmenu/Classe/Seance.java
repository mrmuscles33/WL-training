package com.alan.slidingmenu.Classe;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alan on 15/10/15.
 */
public class Seance implements Parcelable {

    private int id;
    private ArrayList<Exercice> lesExos;
    private Date date;

    public Seance() {
        super();
        lesExos = new ArrayList<>();
        date = new Date();
        id = 0;
    }

    public Seance(Parcel source) {
        /*Exercice[] objects = (Exercice[]) source.readParcelableArray(Exercice.class.getClassLoader());

        for (Exercice e : objects)
            lesExos.add(e);*/

        id = source.readInt();
        int nbExos = source.readInt();

        lesExos = new ArrayList<Exercice>();

        for (int i = 0; i < nbExos; i++) {
            Exercice e = source.readParcelable(Exercice.class.getClassLoader());
            lesExos.add(e);
        }

        date = new Date(source.readInt(), source.readInt(), source.readInt());
    }

    public ArrayList<Exercice> getLesExos() {
        return lesExos;
    }

    public void setLesExos(ArrayList<Exercice> lesExos) {
        this.lesExos = lesExos;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateFormatee() {
        System.out.println(date);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public String getDateSQL() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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
        //dest.writeParcelableArray(lesExos.toArray(), flags);

        dest.writeInt(id);
        dest.writeInt(lesExos.size());

        for (Exercice e : lesExos)
            dest.writeParcelable(e, flags);

        dest.writeInt(date.getYear());
        dest.writeInt(date.getMonth());
        dest.writeInt(date.getDate());
    }

    public static final Parcelable.Creator<Seance> CREATOR = new Parcelable.Creator<Seance>() {

        @Override
        public Seance createFromParcel(Parcel source) {
            return new Seance(source);
        }

        @Override
        public Seance[] newArray(int size) {
            return new Seance[size];
        }
    };
}
