package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Restrictions extends AppCompatActivity {

    private String currentUserEmail = "";
    private String currentCommunity = "";
    private TextView tv1;
    private ListView lv1;
    private String communities[] = {"Andalucía", "Aragón", "Asturias", "Baleares", "Canarias",
            "Cantabria", "Castilla-La Mancha", "Castilla y León", "Cataluña", "Extremadura",
            "Galicia", "La Rioja", "Madrid", "Murcia", "Navarra", "País Vasco", "Valencia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restrictions);
        tv1 = (TextView) findViewById(R.id.tv1);
        lv1 = (ListView) findViewById(R.id.lv1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, communities);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //tv1.setText("Ha seleccionado " + lv1.getItemAtPosition(position));
                currentCommunity = lv1.getItemAtPosition(position).toString();
                goCommunityRestriction(view);
            }
        });
    }

    public void goCommunityRestriction(View view) {
        Intent i = new Intent(this, CommunityRestriction.class);
        i.putExtra("name", currentCommunity);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }

    public void goBack(View view) {
        Intent i = new Intent(this, MainMenu.class);
        //En caso de habernos logeado comunicamos al menu principal el email del usuario logeado
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }
}