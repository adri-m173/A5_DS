package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CommunitiesDataBase extends SQLiteOpenHelper {

    //Nuestra BBDD
    private SQLiteDatabase db;

    /*Sentencia SQL de creacion de la tabla de mensajes,
    sus claves primarias son un entero que se autoincrementa a medida que se insertan en la tabla*/
    private static final String COMMUNITIES_TABLE_CREATE
            = "CREATE TABLE communities(_name TEXT PRIMARY KEY, mobility TEXT, curfew TEXT, groupLimit TEXT)";

    //Nombre de la BBDD de mensajes
    private static final String DB_COMMUNITIES_NAME = "communities.sqlite";
    //Version de la BBDD
    private static final int DB_VERSION = 1;

    //Constructor del gestor de la BBDD de mensajes
    public CommunitiesDataBase(Context context) {
        super(context, DB_COMMUNITIES_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
        inicializarBD();
    }

    private void inicializarBD() {
        this.insert("Andalucía", "Sí", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Aragón", "No", "Entre las 23:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Asturias", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Baleares", "No", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Canarias", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 4 personas");
        this.insert("Cantabria", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Castilla-La Mancha", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Castilla y León", "Si", "Entre las 24:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Cataluña", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Extremadura", "No", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Galicia", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("La Rioja", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 4 personas");
        this.insert("Madrid", "No", "Entre las 24:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Murcia", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Navarra", "Si", "Entre las 23:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("País Vasco", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");
        this.insert("Valencia", "Si", "Entre las 22:00 y las 6:00 horas", "Máximo 6 personas");

    }

    //Creacion de la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMUNITIES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Inserta en la base de datos un nuevo mensaje con el texto pasado por argumento
     *
     * @return false en caso de fallo en la inserción y true en caso de exito
     */
    public boolean insert(String name, String mobility, String curfew, String groupLimit) {
        //inserta el nuevo usuario en la tabla y notifica true
        ContentValues cv = new ContentValues();
        cv.put("_name", name);
        cv.put("mobility", mobility);
        cv.put("curfew", curfew);
        cv.put("groupLimit", groupLimit);
        if (db.insert("communities", null, cv) == -1)
            return false;
        return true;
    }

    /**
     * Metodo para extraer un mensaje aleatorio de la base de datos de mensajes
     *
     * @return null en caso de que la base de datos esté vacia o un mensaje aleatorio
     * previamente guardado
     */
    public Community getCommunity(String name) {
        Community community = null;
        //Consulta en la BBDD si hay alguna comunidad con dicho nombre
        Cursor c = db.rawQuery("SELECT _name, mobility, curfew, groupLimit FROM communities WHERE _name=?", new String[]{name});
        if (c.moveToFirst()) {//En caso de encontrarlo devuelve al usuario
            String cName = c.getString(c.getColumnIndex("_name")),
                    cMobility = c.getString(c.getColumnIndex("mobility")),
                    cCurfew = c.getString(c.getColumnIndex("curfew")),
                    cGroupLimit = c.getString(c.getColumnIndex("groupLimit"));
            community = new Community(cName, cMobility, cCurfew, cGroupLimit);
        }
        //sino devuelve null
        return community;
    }

}
