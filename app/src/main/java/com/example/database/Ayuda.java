package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Ayuda extends AppCompatActivity {
    private Spinner spinner;
    private ImageView image;
    private ImageView image2;
    private TextView text;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        currentUserEmail = getIntent().getStringExtra("userEmail");

        spinner= findViewById(R.id.spinner);
        image= findViewById(R.id.image);
        image2= findViewById(R.id.image2);
        text= findViewById(R.id.text);

        String[] options ={"seleccionar", "Registrarse", "Iniciar sesión", "Jugar", "Perfil", "Ranking", "Ajustes", "Salir"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,options);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        image.setImageResource(R.drawable.reg1);
                        image2.setImageResource(R.drawable.reg2);
                        text.setText("Para registrarse en caso de mostrar 'usuario ya registrado' " +
                                "es necesario registrarse con otro correo electrónico distinto." +
                                "Consecuentemente se mostraría 'usuario registrado correctamente'.");
                        break;
                    case 2:
                        image.setImageResource(R.drawable.ini1);
                        image2.setImageResource(R.drawable.ini2);
                        text.setText("Para iniciar sesión correctamente es preciso constar tanto" +
                                "de un correo electrónico previamente registrado con su contraseña" +
                                "correspondiente. En caso contrario se mostrará 'contraseña o contraseña incorrectos'." +
                                "En caso contrario 'usuario logeado'");
                        break;
                    case 3:
                        image.setImageResource(R.drawable.background);
                        image2.setImageResource(R.drawable.background);
                        text.setText(" ");
                        break;
                    case 4:
                        image.setImageResource(R.drawable.background);
                        image2.setImageResource(R.drawable.background);
                        text.setText(" ");
                        break;
                    case 5:
                        image.setImageResource(R.drawable.background);
                        image2.setImageResource(R.drawable.background);
                        text.setText(" ");
                        break;
                    case 6:
                        image.setImageResource(R.drawable.background);
                        image2.setImageResource(R.drawable.background);
                        text.setText(" ");
                        break;
                    case 7:
                        image.setImageResource(R.drawable.background);
                        image2.setImageResource(R.drawable.background);
                        text.setText(" ");
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

    public void goBack(View view){
        Intent main = new Intent(this, MainMenu.class);
        //Devolvemos al menú el email del usuario logeado para que mantenga la sesión iniciada
        main.putExtra("userEmail", this.currentUserEmail);
        startActivity(main);
    }
}