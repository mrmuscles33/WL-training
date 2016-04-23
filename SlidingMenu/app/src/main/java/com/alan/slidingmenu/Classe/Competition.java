package com.alan.slidingmenu.Classe;

import java.util.Date;

/**
 * Created by alanmocaer on 07/02/16.
 */
public class Competition {

    private int id;
    private String libelle;
    private String lieu;
    private Date date;
    private double poids;
    private int arr1;
    private int arr2;
    private int arr3;
    private int epj1;
    private int epj2;
    private int epj3;

    public Competition() {
        this(0, "", "", new Date(), 0.0, 0, 0, 0, 0, 0, 0);
    }

    public Competition(int id, String libelle, String lieu, Date date, double poids, int arr1, int arr2, int arr3, int epj1, int epj2, int epj3) {
        this.id = id;
        this.libelle = libelle;
        this.lieu = lieu;
        this.date = date;
        this.poids = poids;
        this.arr1 = arr1;
        this.arr2 = arr2;
        this.arr3 = arr3;
        this.epj1 = epj1;
        this.epj2 = epj2;
        this.epj3 = epj3;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public int getArr1() {
        return arr1;
    }

    public void setArr1(int arr1) {
        this.arr1 = arr1;
    }

    public int getArr2() {
        return arr2;
    }

    public void setArr2(int arr2) {
        this.arr2 = arr2;
    }

    public int getArr3() {
        return arr3;
    }

    public void setArr3(int arr3) {
        this.arr3 = arr3;
    }

    public int getEpj1() {
        return epj1;
    }

    public void setEpj1(int epj1) {
        this.epj1 = epj1;
    }

    public int getEpj2() {
        return epj2;
    }

    public void setEpj2(int epj2) {
        this.epj2 = epj2;
    }

    public int getEpj3() {
        return epj3;
    }

    public void setEpj3(int epj3) {
        this.epj3 = epj3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        int maxArr = Math.max(getArr3(), Math.max(getArr2(), getArr1()));
        int maxEpj = Math.max(getEpj3(), Math.max(getEpj2(), getEpj1()));

        return maxArr + maxEpj;
    }
}
