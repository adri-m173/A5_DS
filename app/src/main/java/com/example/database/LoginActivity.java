package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //Atributo donde guardamos el email del usuario logeado actual
    private String currentUserEmail = "";
    //La base de datos de los usuarios
    private UsersDataBase ddbb;
    //Campos de la pantalla a rellenar por el usuario para iniciar sesión
    private EditText etEmail, etPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializacion de la bbdd y linkado de la parte logica y el layout del activity
        ddbb = new UsersDataBase(this);
        currentUserEmail = getIntent().getStringExtra("userEmail");
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.txtLoginEmail);
        etPassword = (EditText) findViewById(R.id.txtLoginPassword);
    }


    /**
     * Acción de volver al menú principal
     *
     * @param view
     */
    public void goBack(View view) {
        Intent i = new Intent(this, MainMenu.class);
        //En caso de habernos logeado comunicamos al menu principal el email del usuario logeado
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }


    /**
     * Metodo que coge los datos introducidos por el usuario en los campos correspondientes
     * y lo busca en la base de datos llamando al logIn de esta y notificando al usuario el exito,
     * o el error habido durante el inicio de sesión.
     *
     * @param view
     */
    public void logIn(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (checkFields(email, password)) {//Comprobamos que los campos estan rellenos
            User u = ddbb.logIn(email, password);
            if (u != null) {//En caso de exito notificamos y editamos el email del usuario actual al del usuario loggeado
                Toast.makeText(this, "Usuario loggeado.", Toast.LENGTH_LONG).show();
                currentUserEmail = u.getEmail();
                goBack(view);
            } else
                Toast.makeText(this, "Contraseña o email incorrectos", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Campos solicictados vacios.", Toast.LENGTH_LONG).show();
    }

    /**
     * Metodo auxiliar usado para comprobar que los campos email y password no estan vacios
     *
     * @param email
     * @param password
     * @return true en caso de que no esten vacios y false si lo estan
     */
    private boolean checkFields(String email, String password) {
        if (email.isEmpty() || password.isEmpty())
            return false;
        return true;
    }

    public void gosigIn(View view){
        Intent i = new Intent(this, SignActivity.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }
}