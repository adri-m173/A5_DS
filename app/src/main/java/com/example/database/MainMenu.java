package com.example.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    //Email del usuario con sesion iniciada, inicialmente a null
    private String currentUserEmail = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //En caso de haber iniciado sesión necesitamos recibir el email del usuario loggeado para
        //comunicarselo al resto de activities
        currentUserEmail = getIntent().getStringExtra("userEmail");
        setContentView(R.layout.activity_main);
    }


    /**
     * Accion que nos lleva a la pantalla que muestra los mensajes tematica COVID
     *
     * @param view
     */
    public void message(View view) {
        Intent i = new Intent(this, MessageActivity.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }

    /**
     * Accion que nos lleva a la pantalla que muestra el ranking de los usuarios
     *
     * @param view
     */
    public void ranking(View view) {
        Intent i = new Intent(this, RankingActivity.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }


    /**
     * Accion que nos lleva a la pantalla del perfil del usuario
     *
     * @param view
     */
    public void Profile(View view) {
        if(currentUserEmail==null){
            Intent signIn = new Intent(this, LoginActivity.class);
            signIn.putExtra("userEmail",this.currentUserEmail);
            startActivity(signIn);
        }else {
            Intent profile = new Intent(this, Profile.class);
            //Mandamos el email del usuario loggeado para que muestre sus datos
            profile.putExtra("userEmail", this.currentUserEmail);
            startActivity(profile);
        }
    }


    public void goHelp(View view){
        Intent help = new Intent(this, Ayuda.class);
        help.putExtra("userEmail",this.currentUserEmail);
        startActivity(help);
    }

    public void restrictions(View view){
        Intent restrictions = new Intent(this, Restrictions.class);
        restrictions.putExtra("userEmail",this.currentUserEmail);
        startActivity(restrictions);
    }

}