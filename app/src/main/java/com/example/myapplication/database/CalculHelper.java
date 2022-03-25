package com.example.myapplication.database;


import com.example.myapplication.entity.Calcul;

public class CalculHelper<CalculDB> {
    private CalculDB CalculDB;

    public CalculHelper(CalculDB CalculDB) {
        this.CalculDB = CalculDB;
    }

    public void storeInDB(Calcul calcul){
        CalculDB.create(calcul);
    }
}