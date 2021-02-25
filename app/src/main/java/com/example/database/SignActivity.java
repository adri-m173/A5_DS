package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Clase para nuestra pantalla de registro de nuevos usuarios
 */
public class SignActivity extends AppCompatActivity {

    //Email del usuario loggeado
    private String currentUserEmail;
    //BBDD de usuarios necesaria para introducir el nuevo usuario
    private UsersDataBase ddbb;
    //Campos para rellenar por el usuario en la pantalla
    private EditText etName, etSurname, etEmail, etPassword, etConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        //Recibimos del menu el usuario loggeado para mantener la sesion iniciada
        currentUserEmail = getIntent().getStringExtra("userEmail");
        //Inicializamos nuestra BBDD de usuarios
        ddbb = new UsersDataBase(this);
        //Linkamos los elementos de la interfaz
        etEmail = (EditText) findViewById(R.id.txtEmail);
        etPassword = (EditText) findViewById(R.id.txtPassword);
        etConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        etName = (EditText) findViewById(R.id.txtName);
        etSurname = (EditText) findViewById(R.id.txtSurname);
    }

    /**
     * Accion de volver al menu principal
     *
     * @param view
     */
    public void goBack(View view) {
        Intent i = new Intent(this, MainMenu.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }

    /**
     * Metodo para registrar un nuevo usuario, comprobando que los campos han sido rellenados
     * correctamente y llamando al metodo registrarse de la base de datos de usuarios.
     *
     * @param view
     */
    public void sigIn(View view) {
        //Tomamos los valores introducidos por pantalla
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        //Comprobamos que haya rellenado todos los campos vacios y notificamos del exito o del
        //error cometido al usuario
        if (comprobarCampos(email, password, confirmPassword, name, surname)) {
            if (confirmPassword.equals(password)) {
                boolean correctSignIn = ddbb.sigIn(email, name, surname, password);
                if (correctSignIn) {
                    Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_LONG).show();
                    currentUserEmail = email;   //si el usuario se registra correctamente mantiene la sesion iniciadaç
                    goBack(view);
                } else
                    Toast.makeText(this, "Nombre de usuario ya registrado anteriormente.", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "Confirme la contraseña correctamente.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Falta algun campo del registro por rellenar.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Método auxiliar para comprobar que los campos para el registro de usuarios no esten vacios
     *
     * @param email
     * @param password
     * @param confirmPassword
     * @param name
     * @param surname
     * @return true en caso de que los campos esten correctamente sino devuelve false
     */
    private boolean comprobarCampos(String email, String password, String confirmPassword, String name, String surname) {
        return !(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || surname.isEmpty());
    }
}