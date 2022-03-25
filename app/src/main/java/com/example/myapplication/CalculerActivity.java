package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import com.example.myapplication.database.CalculHelper;
import com.example.myapplication.entity.Calcul;
import com.example.s4web.R;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class CalculerActivity extends AppCompatActivity {
    public class CalculActivity extends AppCompatActivity {
        Integer premierElement = 0;
        Integer deuxiemeElement = 0;
        private GenerationDifficultéCalculActivity GenerationDifficulté;
        private Difficulte difficulte;
        private Integer score = 0;
        com.example.myapplication.database.CalculHelper CalculHelper;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calcul);

            CalculHelper = new CalculHelper(new CalculDB(new CalculBaseHelper(this)));
            difficulte = Difficulte.Facile;

            generationCalcul();
            initBoutons();
            Button bouton_validation = findViewById(R.id.bouton_validation);
            bouton_validation.setOnClickListener(view -> verification(view));

            Button boutonDernierCalcul = findViewById(R.id.bouton_retour1);
            boutonDernierCalcul.setOnClickListener(view -> retourMenu());
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.toolbar, menu);

            MenuItem boutonCalculer = menu.findItem(R.id.bouton_calculer);
            boutonCalculer.setOnMenuItemClickListener(menuItem -> ouvrirDernierCalcul());

            MenuItem boutonEffacer = menu.findItem(R.id.bouton_effacer);
            boutonEffacer.setOnMenuItemClickListener(menuItem -> viderCalcul());

            return super.onCreateOptionsMenu(menu);
        }

        private void retourMenu() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        public void initBoutons() {
            ArrayList<Button> listeBoutons = new ArrayList<>();
            listeBoutons.add(findViewById(R.id.bouton_zero));
            listeBoutons.add(findViewById(R.id.bouton_un));
            listeBoutons.add(findViewById(R.id.bouton_deux));
            listeBoutons.add(findViewById(R.id.bouton_trois));
            listeBoutons.add(findViewById(R.id.bouton_quatre));
            listeBoutons.add(findViewById(R.id.bouton_cinq));
            listeBoutons.add(findViewById(R.id.bouton_six));
            listeBoutons.add(findViewById(R.id.bouton_sept));
            listeBoutons.add(findViewById(R.id.bouton_huit));
            listeBoutons.add(findViewById(R.id.bouton_neuf));
            listeBoutons.add(findViewById(R.id.bouton_moins));
            for (Button b : listeBoutons) {
                b.setOnClickListener(view -> saisie(view));
            }
            Button bouton_suppr = findViewById(R.id.bouton_supprimer);
            bouton_suppr.setOnClickListener(view -> deleteInput(view));
            TextView textView = findViewById(R.id.msg_txt);
            textView.setVisibility(View.INVISIBLE);
        }

        public void generationCalcul() {
            TextView textView = findViewById(R.id.affichagecalcul);
            GenerationDifficulté = new GenerationDifficultéCalculActivity(difficulte);
            textView.setText(GenerationDifficulté.toString());
        }

        public void deleteInput(View view) {
            TextView saisir = findViewById(R.id.saisir);
            String text = (String) saisir.getText();
            int length = text.length();
            if (length > 0) {
                String text2 = text.substring(0, length - 1);
                saisir.setText(text2);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void saisie(View view) {
            TextView saisir = findViewById(R.id.saisir);
            Button btn = (Button) view;
            saisir.setText((String) saisir.getText() + btn.getText());
            TextView resultat = findViewById(R.id.msg_txt);
            resultat.setVisibility(View.INVISIBLE);

            Vibrator vibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            VibrationEffect effetVibration = VibrationEffect.createOneShot(100, 1);
            vibration.vibrate(effetVibration);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void verification(View view) {
            TextView saisir = findViewById(R.id.saisir);
            String texte = (String) saisir.getText();
            boolean result;
            try {
                result = (Double.parseDouble(texte)) == (GenerationDifficulté.getResultat());
            } catch (Exception e) {
                result = false;
            }
            TextView resultat = findViewById(R.id.msg_txt);
            final MediaPlayer sonErreur = MediaPlayer.create(this, R.raw.failure);
            final MediaPlayer sonSucces = MediaPlayer.create(this, R.raw.success);
            Vibrator vibration = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            VibrationEffect effetVibration;
            if (result) {
                sonSucces.start();
                resultat.setTextColor(getResources().getColor(R.color.succes, this.getTheme()));
                resultat.setText(getResources().getString(R.string.succes));
                effetVibration = VibrationEffect.createOneShot(150, 1);
                score++;
            } else {
                sonErreur.start();
                resultat.setTextColor(getResources().getColor(R.color.echec, this.getTheme()));
                resultat.setText(getResources().getString(R.string.echec));
                effetVibration = VibrationEffect.createOneShot(500, 5);
                if (score - 1 > 0) {
                    score--;
                } else {
                    score = 0;
                }
            }
            if (score <= 10) {
                difficulte = Difficulte.Facile;
            } else if (score <= 20) {
                difficulte = Difficulte.Moyen;
            } else {
                difficulte = Difficulte.Difficile;
            }
            vibration.vibrate(effetVibration);
            resultat.setVisibility(View.VISIBLE);
            saisir.setText("");
            generationCalcul();
        }

        private boolean ouvrirDernierCalcul() {
            Intent intent = new Intent(this, DernierCalculActivity.class);
            intent.putExtra("premierElement", premierElement);
            intent.putExtra("deuxiemeElement", deuxiemeElement);
            intent.putExtra("symbol", GenerationDifficulté.getOperateur());
            intent.putExtra("resultat", GenerationDifficulté.getResultat());
            Calcul Calcul = new Calcul();
            Calcul.setPremierElement(premierElement);
            Calcul.setDeuxiemeElement(deuxiemeElement);
            Calcul.setSymbole(GenerationDifficulté.getOperateur());
            Calcul.setResultat(GenerationDifficulté.getResultat());
            CalculHelper.storeInDB(Calcul);

            return true;
        }

        private boolean viderCalcul() {
            TextView saisir = findViewById(R.id.saisir);
            saisir.setText("");
            return true;
        }
    }
}