package com.example.proyecto1_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuInicio extends AppCompatActivity {
    public TextView textViewNombre;
    String  usuarioBdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);
        //Recepcion de parametros
        Bundle parametrosExtra = getIntent().getExtras();
        if (parametrosExtra != null){
            try {
                //usamos las variables declaradas

                usuarioBdd = parametrosExtra.getString("usuarioBdd");

            }catch (Exception ex){//ex recibe el tipo de error
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud "+ex.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        textViewNombre = findViewById(R.id.textViewNombre);

        textViewNombre.setText(getResources().getString(R.string.saludo) +""+ usuarioBdd);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_desplegable, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent=new Intent(getApplicationContext(),MenuInicio.class);
                intent.putExtra("usuarioBdd", usuarioBdd);

                startActivity(intent); //solicitamos que abra el menu
                finish(); //cerrando la activity
                return true;
            case R.id.item2:
                Intent intentt=new Intent(getApplicationContext(),Perfil.class);
                intentt.putExtra("usuarioBdd", usuarioBdd);

                startActivity(intentt); //solicitamos que abra el menu
                finish(); //cerrando la activity
                return true;


            case R.id.item3:
                SharedPreferences sharedPref = this.getSharedPreferences("usuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();

                Intent intenttt = new Intent(this, MainActivity.class);
                intenttt.putExtra("usuarioBdd", usuarioBdd);

                startActivity(intenttt);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Dirigirnos a la ventana de citar
    public void buttonCitar(View view) {
        Intent intent = new Intent(this, PedirCitas.class);
        intent.putExtra("usuarioBdd", usuarioBdd);
        //intent.putExtra("nombreBdd", nombreBdd);
        startActivity(intent);
        finish();
    }
    //Dirigirnos a la ventana de ver mis citas, para poder cancelarlas
    public void buttonCancelar(View view) {
        Intent intent = new Intent(this, MisCitas.class);
        intent.putExtra("usuarioBdd", usuarioBdd);
        //intent.putExtra("nombreBdd", nombreBdd);
        startActivity(intent);
        finish();

    }
    // Método que dirige a salir de la sesion
    public void buttonSalir(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();

    }
    // Método que dirige a la información del Servicio de Medicina
    public void buttonConocenos(View view) {
        Intent intent = new Intent(this, Info.class) ;
        intent.putExtra("usuarioBdd", usuarioBdd);
       // intent.putExtra("nombreBdd", nombreBdd);
        startActivity(intent);
        finish();
    }


}