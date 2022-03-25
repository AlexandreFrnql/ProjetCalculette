package com.example.myapplication.entity;

public class Calcul{
    Integer premierElement;
    Integer deuxiemeElement;
    String symbole;
    double resultat;

    public Integer getPremierElement() {
        return premierElement;
    }

    public Integer getDeuxiemeElement() {
        return deuxiemeElement;
    }

    public String getSymbole() {
        return symbole;
    }

    public double getResultat() {
        return resultat;
    }

    public void setPremierElement(Integer premierElement) {
        this.premierElement = premierElement;
    }

    public void setDeuxiemeElement(Integer deuxiemeElement) {
        this.deuxiemeElement = deuxiemeElement;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public void setResultat(double resultat) {
        this.resultat = resultat;
    }
}
