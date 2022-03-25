package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import com.example.s4web.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class DernierCalculActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dernier_calcul);

        Button boutonRetour = findViewById(R.id.bouton_retour);
        boutonRetour.setOnClickListener(view -> lancerMainActivity());

        TextView derniersCalculs = findViewById(R.id.derniersCalculs);
        Intent intent = getIntent();

        if(intent.getStringExtra("Symbole")!=null){
            derniersCalculs.setText(intent
                    .getDoubleExtra("PremierElement", 0)
                    + " " +
                    intent.getStringExtra("Symbole")
                    + " " +
                    intent.getDoubleExtra("DeuxiemeElement", 0)
                    + " = " +
                    intent.getDoubleExtra("Résultat",0)
            );
        }else{
            derniersCalculs.setText("");
        }
    }

    private void lancerMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}