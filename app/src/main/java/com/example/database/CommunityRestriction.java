package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CommunityRestriction extends AppCompatActivity {

    private String communityName = "";
    private String currentUserEmail = "";
    private EditText etName, etMobility, etCurfew, etGroupLimit;
    private CommunitiesDataBase ddbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_restriction);
        ddbb = new CommunitiesDataBase(this);
        communityName = getIntent().getStringExtra("name");
        currentUserEmail = getIntent().getStringExtra("userEmail");
        etName = (EditText) findViewById(R.id.tvName);
        etMobility = (EditText) findViewById(R.id.tvMobility2);
        etCurfew = (EditText) findViewById(R.id.tvCurfew2);
        etGroupLimit = (EditText) findViewById(R.id.tvGroupLimit2);
        etName.setText(communityName);
        Community c = ddbb.getCommunity(communityName);
        if (c != null) {//En caso de exito notificamos y editamos los correspondientes campos
            etMobility.setText(c.getMobility());
            etCurfew.setText(c.getCurfew());
            etGroupLimit.setText(c.getGroupLimit());
        } else
            Toast.makeText(this, "Comunidad no encontrada", Toast.LENGTH_LONG).show();
    }

    public void goBack(View view) {
        Intent i = new Intent(this, Restrictions.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }
}