package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Clase para la pantalla del ranking de usuarios registrados
 */
public class RankingActivity extends AppCompatActivity {

    //Email del usuario loggeado actualmente
    private String currentUserEmail;
    //BBDD de usuarios necesaria para mostrar sus nombres y puntuaciones
    private UsersDataBase BBDD;
    //Elemento del layout para mostrar la lista de usuarios
    private ListView dynamic;
    //Array usado para ordenar a los usuarios por sus puntuaciones
    private LinkedList<User> users = new LinkedList<>();
    //Array que guarda la información mostrada en cada fila del ranking
    private LinkedList<String> infoToShow = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        currentUserEmail = getIntent().getStringExtra("userEmail");

        //Inicializamos nuestra base de datos
        BBDD = new UsersDataBase(this);

        //Inicializamos elementos de la interfaz
        dynamic = (ListView) findViewById(R.id.rankList);
        //Mostramos el ranking
        showRanking();
    }

    /**
     * Metodo para volver al menu principal
     *
     * @param view
     */
    public void goBack(View view) {
        Intent i = new Intent(this, MainMenu.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }


    /**
     * Metodo para cargar la informacion de los jugadores y mostrarla en la tabla
     */
    public void showRanking() {
        User userAux;
        String name, surname, email, password;
        int score;
        //Cogemos todos los usuarios de nuestra base de datos
        users = BBDD.getAllUsers();
        if(users.isEmpty()){
            Toast.makeText(this,"No hay usuarios registrados aun en la aplicacion",Toast.LENGTH_SHORT).show();
            return ;
        }
        //Ordenamos los usuarios
        sort(users);
        //Guardamos la informacion que queremos de cada uno
        for (User u : users) {
            infoToShow.add(u.getName() + "\t\t\tScore:" + u.getScore());
        }
        //Linkamos el array de la informacion a la tabla mostrada en pantalla
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infoToShow);
        dynamic.setAdapter(adapter);
    }

    /**
     * Metodo auxiliar para ordenar los usuarios de mayor a menor respecto a sus puntuaciones
     *
     * @param users
     */
    private void sort(LinkedList<User> users) {
        quicksort(users, 0, users.size() - 1);
    }

    /**
     * Metodo auxiliar para ordenar mediante el algoritmo quicksort
     *
     * @param A   es el array a ordenar
     * @param izq es el inicio del array a ordenar
     * @param der es el fin del array a ordenar
     */
    private static void quicksort(LinkedList<User> A, int izq, int der) {

        User pivote = A.get(izq); // tomamos primer elemento como pivote
        int i = izq;         // i realiza la búsqueda de izquierda a derecha
        int j = der;         // j realiza la búsqueda de derecha a izquierda
        User aux;

        while (i < j) {                          // mientras no se crucen las búsquedas
            while (A.get(i).getScore() >= pivote.getScore() && i < j)
                i++; // busca elemento mayor que pivote
            while (A.get(j).getScore() < pivote.getScore())
                j--;           // busca elemento menor que pivote
            if (i < j) {                        // si no se han cruzado
                aux = A.get(i);                      // los intercambia
                A.set(i, A.get(j));
                A.set(j, aux);
            }
        }

        A.set(izq, A.get(j));      // se coloca el pivote en su lugar de forma que tendremos
        A.set(j, pivote);      // los menores a su izquierda y los mayores a su derecha

        if (izq < j - 1)
            quicksort(A, izq, j - 1);          // ordenamos subarray izquierdo
        if (j + 1 < der)
            quicksort(A, j + 1, der);          // ordenamos subarray derecho

    }

}