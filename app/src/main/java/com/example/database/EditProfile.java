package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    private String currentUserEmail;
    private UsersDataBase dataBase;
    private TextView passwordTxt,confirmPasswordTxt,nameTxt,surnameTxt,emailTxt;
    private int currentUserScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        currentUserEmail = getIntent().getStringExtra("userEmail");
        passwordTxt = (TextView) findViewById(R.id.txtPassword2);
        confirmPasswordTxt = (TextView) findViewById(R.id.txtConfirmPassword2);
        nameTxt = (TextView) findViewById(R.id.txtName2);
        surnameTxt = (TextView) findViewById(R.id.txtSurname2);
        emailTxt = (TextView) findViewById(R.id.txtEmail2);
        dataBase = new UsersDataBase(this);

        if(currentUserEmail!=null) {
            User currentUser = dataBase.getUser(currentUserEmail);
            passwordTxt.setHint(currentUser.getPassword());
            confirmPasswordTxt.setHint(currentUser.getPassword());
            nameTxt.setHint(currentUser.getName());
            surnameTxt.setHint(currentUser.getSurname());
            emailTxt.setHint(currentUser.getEmail());
            currentUserScore = dataBase.getUser(currentUserEmail).getScore();
        }else
            Toast.makeText(this,"Tiene que haber iniciado sesion previamente.",Toast.LENGTH_SHORT).show();
    }

    public void saveChanges(View view){
        if(currentUserEmail!=null) {
            if(checkPasswordFieldsArentEmpty()){
                if(passwordTxt.getText().equals(confirmPasswordTxt.getText())) {
                    dataBase.updateUser("password", passwordTxt.getText().toString(), currentUserEmail);
                    if(!nameTxt.getText().toString().isEmpty())
                        dataBase.updateUser("name",nameTxt.getText().toString(),currentUserEmail);
                    if(!surnameTxt.getText().toString().isEmpty())
                        dataBase.updateUser("surname", surnameTxt.getText().toString(),currentUserEmail);
                    if(!emailTxt.getText().toString().isEmpty()){
                        dataBase.updateUser("_email", emailTxt.getText().toString(), currentUserEmail);
                        currentUserEmail = emailTxt.getText().toString();
                    }
                    Toast.makeText(this,"Cambios realizados correctamente.",Toast.LENGTH_SHORT).show();
                    goMain(view);
                }
                else
                    Toast.makeText(this,"Las contraseñas no coinciden.",Toast.LENGTH_SHORT).show();
            }else {
                if(checkOnePasswordFieldIsEmptyAndOtherNot()){
                    Toast.makeText(this,"Las contraseñas no coinciden.",Toast.LENGTH_SHORT).show();
                }else {
                    if (!nameTxt.getText().toString().isEmpty())
                        dataBase.updateUser("name", nameTxt.getText().toString(), currentUserEmail);
                    if (!surnameTxt.getText().toString().isEmpty())
                        dataBase.updateUser("surname", surnameTxt.getText().toString(), currentUserEmail);
                    if (!emailTxt.getText().toString().isEmpty()) {
                        dataBase.updateUser("_email", emailTxt.getText().toString(), currentUserEmail);
                        currentUserEmail = emailTxt.getText().toString();
                    }
                    Toast.makeText(this, "Cambios realizados correctamente.", Toast.LENGTH_SHORT).show();
                    goMain(view);
                }
            }
        }else
            Toast.makeText(this,"Tiene que haber iniciado sesion previamente.",Toast.LENGTH_SHORT).show();
    }

    private boolean checkOnePasswordFieldIsEmptyAndOtherNot() {
        if(passwordTxt.getText().toString().isEmpty()&&!confirmPasswordTxt.getText().toString().isEmpty())
            return true;
        if(!passwordTxt.getText().toString().isEmpty()&&confirmPasswordTxt.getText().toString().isEmpty())
            return true;
        return false;
    }

    private boolean checkPasswordFieldsArentEmpty() {
        return !passwordTxt.getText().toString().isEmpty()&&!confirmPasswordTxt.toString().isEmpty();
    }

    public void goMain(View view){
        Intent i = new Intent(this, MainMenu.class);
        i.putExtra("userEmail", this.currentUserEmail);
        startActivity(i);
    }
}