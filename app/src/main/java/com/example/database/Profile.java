package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase para la activity de la pantalla del perfil del usuario.
 */
public class Profile extends AppCompatActivity {

    //La BBDD de los usuarios necesaria para consultar los datos del usuario
    private UsersDataBase ddbb;
    //Email del usuario loggeado actualmente
    private String currentUserEmail;
    //Elementos de la pantalla en la que se muestra la informacion del usuario
    private TextView userName, userSurname, userScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Inicializado de la bbdd y linkado con el layout del activity
        userName = (TextView) findViewById(R.id.userName);
        userSurname = (TextView) findViewById(R.id.userSurname);
        userScore = (TextView) findViewById(R.id.userScore);
        ddbb = new UsersDataBase(this);
        //Recibimos del menú el usuario loggeado
        currentUserEmail = getIntent().getStringExtra("userEmail");
        //Si no se ha iniciado sesión se le notifica al usuario y no se muestra nada
        if (currentUserEmail == null) {
            Toast.makeText(this, "Debe iniciar sesión previamente para poder acceder a su perfil", Toast.LENGTH_SHORT).show();
        } else {//sino se muestran sus datos por pantalla
            User currentUser = ddbb.getUser(currentUserEmail);
            userName.setText(currentUser.getName());
            userSurname.setText(currentUser.getSurname());
            userScore.setText(String.valueOf(currentUser.getScore()));
        }
    }

    /**
     * Acción de volver al menu principal
     *
     * @param view
     */
    public void goMain(View view) {
        Intent main = new Intent(this, MainMenu.class);
        main.putExtra("userEmail", currentUserEmail);
        startActivity(main);
    }

    public void deleteAcount(View view) {
        if(currentUserEmail==null){
            Toast.makeText(this,"Debe iniciar sesión previamente para poder borrar su perfil.", Toast.LENGTH_SHORT).show();
        }else{
            if(!ddbb.deleteUser(currentUserEmail)){
                Toast.makeText(this,"Error al borrar usuario.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Ha borrado su cuenta con exito.", Toast.LENGTH_SHORT).show();
                userName.setText("Nombre");
                userSurname.setText("Apellidos");
                userScore.setText("0");
                currentUserEmail=null;
            }
        }
    }

    public void goEditPorfile(View view){
        if(currentUserEmail!=null) {
            Intent editProfile = new Intent(this, EditProfile.class);
            editProfile.putExtra("userEmail", currentUserEmail);
            startActivity(editProfile);
        }else
            Toast.makeText(this,"Debe iniciar sesión previamente para poder editar su perfil.", Toast.LENGTH_SHORT).show();
    }

    public void logOut(View view){
        currentUserEmail = null;
        Toast.makeText(this, "Has cerrado sesion correctamente.", Toast.LENGTH_SHORT).show();
        goMain(view);
    }

}