package com.alan.slidingmenu.Classe;

/**
 * Created by alanmocaer on 27/01/16.
 */
public class User {

    private int idUser;
    private String email;
    private String pseudo;
    private String password;

    public static final User USER_VIDE = new User(0,"","","");

    public User(int id, String pEmail, String pPseudo, String pPwd) {
        idUser = id;
        email = pEmail;
        pseudo = pPseudo;
        password = pPwd;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
