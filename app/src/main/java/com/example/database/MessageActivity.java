package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity en el que se guardan y muestran los mensajes con temática COVID
 */
public class MessageActivity extends AppCompatActivity {

    //Email del usuario loggeado actualmente
    private String currentUserEmail;
    //Campo donde se mostrara el mensaje COVID en la pantalla
    private TextView messageText, waitingTime;
    private Temporizador timer;
    private MessagesDataBase ddbb;
    private static String TAG = "MainActivity ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recibimos el email del usuario loggeado desde el menu y lo guardamos
        currentUserEmail = getIntent().getStringExtra("userEmail");
        //Inicializamos la bbdd y linkamos con el layout del activity
        ddbb = new MessagesDataBase(this);
        setContentView(R.layout.activity_message);
        messageText = (TextView) findViewById(R.id.txtMessage);
        waitingTime = (TextView) findViewById(R.id.txtWaitingTime);
        showMessage();
        timer = new Temporizador(waitingTime,5000,"Cargando...");
        timer.star();
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                goGame();
            }
        }, 5000);
    }


    /**
     * Metodo para enseñar un mensaje temática COVID aleatorio llamando a la base de datos
     * de los mensajes, en caso de no haber mensajes se notifica al usuario
     */
    public void showMessage() {
        messageText.setText("");
        COVID_Message messageToShow = ddbb.getMessage();
        if (messageToShow != null) {
            messageText.setText(messageToShow.getText());
        } else
            Toast.makeText(this, "No hay mensajes en la base de datos para mostrar.", Toast.LENGTH_LONG).show();
    }

    public void goGame(){
        Intent juego = new Intent(this, Game.class);
        juego.putExtra("userEmail", currentUserEmail);
        startActivity(juego);
    }

}