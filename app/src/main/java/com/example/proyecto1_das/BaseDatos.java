package com.example.proyecto1_das;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String tablaUsuario = "create table usuario(usuario text unique primary key," +
            "url text)";

    private static final String tablaCita = "create table cita(usuario text ," +
            "tipo text, fecha_cita text ,primary key(usuario,fecha_cita))";
    public BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaUsuario);
        db.execSQL(tablaCita);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS usuario");//eliminacion de la version anterior de la tabla usuarios
        db.execSQL(tablaUsuario); //Ejecucion del codigo para crear la tabla usuaios con su nueva estructura

        db.execSQL("DROP TABLE IF EXISTS cita");//eliminacion de la version anterior de la tabla cita
        db.execSQL(tablaCita); //Ejecucion del codigo para crear la tabla cita con su nueva estructura
    }



    //metodo para añadir una cita
    public boolean agregarCita(String usuario, String tipo, String fecha_cita) {
        SQLiteDatabase miBdd = getWritableDatabase(); //llamando a la base de datos en el objeto mi Ddd
        if (miBdd != null) { //validando que la base de datos exista(q no sea nula)
            boolean hay=buscarCita(fecha_cita);
            if (!hay) {
                miBdd.execSQL("insert into cita(usuario, tipo, fecha_cita) " +
                        "values('" + usuario + "','" + tipo + "','" + fecha_cita + "')");    //ejecutando la sentencia de insercion de SQL
                miBdd.close(); //cerrando la conexion a la base de datos.
                return true; // valor de retorno si se inserto exitosamente.
            }
        }
        return false; //retorno cuando no existe la BDD
    }
    //método para obtener las citas asociadas a un usuario
    public ArrayList<Cita> obtenerCita(String usuario){
        SQLiteDatabase miBdd = getWritableDatabase(); // llamado a la base de datos
        ArrayList<Cita> citas = new ArrayList<>();

        //crear un cursor donde inserto la consulta sql y almaceno los resultados
        Cursor c= miBdd.rawQuery("select " +
                "tipo , fecha_cita " +
                "from cita  " +
                "where usuario =  '"+usuario+ "';", null);
        //validar si existe o no la consulta
        while (c.moveToNext()) {
            String tipo = c.getString(0);
            String fecha = c.getString(1);
            Cita cita = new Cita(usuario,tipo, fecha);
            citas.add(cita);
        }
        miBdd.close();
        return citas;
    }
    //metodo para eliminar una cita de la base de datos
    public void eliminarCita(String fecha, String usuario) {
        SQLiteDatabase miBdd = getWritableDatabase(); // objeto para manejar la base de datos
        if (miBdd != null) { //validando que la bdd realmente exista
            miBdd.execSQL("DELETE FROM cita WHERE fecha_cita='" + fecha + "' AND usuario='" + usuario + "'"); //ejecucion de la sentencia Sql para eliminar
            miBdd.close();

        }
    }
    //método para buscar una cita dad auna fecha
    public boolean buscarCita(String fecha){
            SQLiteDatabase miBdd = getWritableDatabase();
            boolean haycita=false;
             Cursor c=  miBdd.rawQuery("select * from cita where " +
                     "fecha_cita='" + fecha+ "';", null);
        //validar si existe o no la consulta
        if (c.moveToFirst()) { //metodo movetofirst nueve al primer elemento encontrado si hay
            haycita=true; //retornamos los datos encontrados
        } else {

            haycita=false;
        }

            return haycita;
    }



}
