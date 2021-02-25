package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase de la pantalla del juego en el que aparece un punto verde y se gana al pulsarlo.
 * Cada vez que se acierte se le suma 1 punto a la puntuación de la partida y una vez salgamos del
 * juego se añadira a la puntuación total del usuario si éste ha iniciado sesión anteriormente.
 */

public class Game extends AppCompatActivity {
    //Temporizador para llevar la cuenta del juego
    private Temporizador timer;
    //Usuario que está jugando y su email
    private User currentUser;
    private String currentUserEmail;
    //Base de datos de nuestros usuarios, necesaria para actualizar las puntuaciones
    private UsersDataBase ddbb;
    //showValue es el textview correspondiente a la puntuación que me va a ir variando en función de los clicks dados al círculo
    TextView showValue;
    // el counter es el contador de los clicks dados al círculo necesarios para contar la puntuación
    public int counter;
    private TextView timeRemining;
    private TextView recordText;
    private ArrayList<ImageButton> noContagiados= new ArrayList<>();
    private ArrayList<Integer> notInfectedSource, infectedSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        //Linkamos el texto para la puntuación de la partida con su correspondiente en el layout
        showValue = (TextView) findViewById(R.id.counterValue);
        timeRemining = (TextView) findViewById(R.id.timerText);
        recordText = (TextView) findViewById(R.id.recordText);
        //inicializamos la BBDD
        ddbb = new UsersDataBase(this);
        //Tomamos el email del usuario logeado que nos manda el menú
        currentUserEmail = getIntent().getStringExtra("userEmail");
        //innicializamos counter a 0
        counter = 0;
        timer = new Temporizador(timeRemining,30000,"Perdiste :(");
        //En caso de que haya un usuario loggeado mostramos su record por pantalla, en caso
        //de ser nuevo o no estar loggeado se muestra 0
        if(this.currentUserEmail!=null){
            String usrRecord = String.valueOf(ddbb.getUser(currentUserEmail).getScore());
            recordText.setText(usrRecord);
        }else
            recordText.setText("0");
        notInfectedSource=new ArrayList<>();
        infectedSource=new ArrayList<>();
        infectedSource.add(R.drawable.malo1);
        infectedSource.add(R.drawable.malo2);
        notInfectedSource.add(R.drawable.bueno1);
        notInfectedSource.add(R.drawable.bueno2);
        notInfectedSource.add(R.drawable.bueno3);
        //Empezar el juego
        timer.star();
    }

    private void placeInfected() {
        placeCharacter(findViewById(R.id.button12));
    }

    private void placeCharacter(ImageButton button){
        button.setX(randomX(button));
        button.setY(randomY()+button.getHeight());
    }


    private int randomY() {
        Random r = new Random();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int newY =  r.nextInt(metrics.heightPixels-1350)+500;
        return newY;
    }

    private int randomX(ImageButton button) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        Random r = new Random();

        return r.nextInt(width-button.getWidth());
    }


    /**
     * Acción del botón volver a inicio
     *
     * @param view
     */
    public void goMain(View view) {
        Intent main = new Intent(this, MainMenu.class);
        //Cuando salimos del juego y es un usuario loggeado tenemos que actualiar su puntuación
        updateScore();
        //Devolvemos al menú el email del usuario logeado para que mantenga la sesión iniciada
        main.putExtra("userEmail", this.currentUserEmail);
        startActivity(main);
        counter = 0;
        showValue.setText(Integer.toString(counter));
    }

    /**
     * Acción al pulsar el círculo que mostrará una notificación de victoria seguido
     * por un incremento del contador para las puntuaciones
     *
     * @param view
     */
    public void clickOnObjetive(View view) {
        if (!timer.hasEnd()) {
            //en cada click de este botón el counter se incrementará
            counter++;
            timer.acertar();
            showValue.setText(Integer.toString(counter));
            placeInfected();
            colocarNoContagiados(counter);
        }else
            Toast.makeText(this, "El tiempo se ha acabado pulsa jugar otra vez para volver a jugar!", Toast.LENGTH_LONG).show();
    }

    private void colocarNoContagiados(int level) {
        removeRestCharacters();
        noContagiados = new ArrayList<>();
        int dificultad = generateCharactersByDifficulty(level);
        ConstraintLayout parent = findViewById(R.id.l1_parent);
        for(int i=0; i<dificultad;i++){
            ImageButton b = new ImageButton(Game.this);
            b.setId(i+1);
            b.setTag(i);
            b.setOnClickListener(this::clickOnWrongCharacter);
            b.setBackgroundResource(randomNotInfectedImage());
            placeCharacter(b);
            parent.addView(b);
            noContagiados.add(b);
        }
    }

    private int randomNotInfectedImage() {
        Random r = new Random();
        return notInfectedSource.get(r.nextInt(notInfectedSource.size()-1));
    }

    private int randomInfectedImage() {
        Random r = new Random();
        return infectedSource.get(r.nextInt(infectedSource.size()-1));
    }

    private int generateCharactersByDifficulty(int level) {
        Random r = new Random();
        int characterNumber = r.nextInt(level)+level;
        //Faltaria refinar un poco la generacion de personajes en funcion de la dificultad
        return characterNumber;
    }

    public void clickOutObjetive(View view){
        if (!timer.hasEnd()) {
            timer.fallar();
            showValue.setText(Integer.toString(counter));
            Toast.makeText(this, "Te has equivado, sigue intentandolo!", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this, "El tiempo se ha acabado pulsa jugar otra vez para volver a jugar!", Toast.LENGTH_LONG).show();
    }

    public void clickOnWrongCharacter(View view){
        if (!timer.hasEnd()) {
            timer.fallar();
            showValue.setText(Integer.toString(counter));
            Toast.makeText(this, "Te has equivado, sigue intentandolo!", Toast.LENGTH_LONG).show();
            ConstraintLayout parent =findViewById(R.id.l1_parent);
            parent.removeView(view);
        }else
            Toast.makeText(this, "El tiempo se ha acabado pulsa jugar otra vez para volver a jugar!", Toast.LENGTH_LONG).show();
    }


    public void playAgain(View view){
        updateScore();
        if(this.currentUserEmail!=null){
            String usrRecord = String.valueOf(ddbb.getUser(currentUserEmail).getScore());
            recordText.setText(usrRecord);
        }else
            recordText.setText("0");
        timer.restart();
        showValue.setText("0");
        removeRestCharacters();
        findViewById(R.id.button12).setBackgroundResource(randomInfectedImage());
        placeCharacter(findViewById(R.id.button12));
        counter = 0;
    }

    private void removeRestCharacters() {
        if(!this.noContagiados.isEmpty()){
            for (ImageButton b:
                    noContagiados) {
                ConstraintLayout layout = findViewById(R.id.l1_parent);
                layout.removeView(b);
            }
        }
    }

    private void updateScore(){
        if (currentUserEmail != null) {
            currentUser = ddbb.getUser(currentUserEmail);
            if(currentUser.getScore()<counter) {
                ddbb.updateScore(currentUser.getEmail(), counter);
                Toast.makeText(this, "Felicidades tienes un nuevo record de "+counter+"!", Toast.LENGTH_LONG).show();
            }
        }
    }
}