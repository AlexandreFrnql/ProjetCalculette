package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.s4web.R;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boutonCalculer = findViewById(R.id.bouton_calculer);
        boutonCalculer.setOnClickListener(view -> lancerActivityCalcul());

        Button boutonDernierCalcul = findViewById(R.id.bouton_dernier_calcul);
        boutonDernierCalcul.setOnClickListener(view -> lancerActivityDernierCalcul());
    }

    private void lancerActivityCalcul() {
        Intent intent = new Intent(this,CalculerActivity.class);
        startActivity(intent);
    }

    private void lancerActivityDernierCalcul() {
        Intent intent = new Intent(this, DernierCalculActivity.class);
        startActivity(intent);
    }

}