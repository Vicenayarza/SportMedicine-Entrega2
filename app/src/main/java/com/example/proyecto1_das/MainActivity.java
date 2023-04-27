package com.example.proyecto1_das;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    public EditText et_usuario, et_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new
                        String[]{POST_NOTIFICATIONS}, 11);
            }
        }

        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);


    }

    public void cambiarIdioma(View view) {//Metodo para cambiar idioma
        boolean isChecked = ((Switch) view).isChecked();//si esta seleccionado estaremos en castellano, sino en inglés
        if (isChecked) {
            setLocale("es");
        } else {
            setLocale("en");
        }

    }
    private void setLocale(String languageCode) {//metodo para cambiar la configuración del idioma en funcion de los ficheros srings.xml
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    public void buttonLogin(View view) {

        String usuario, password;
        usuario = et_usuario.getText().toString();
        password = et_password.getText().toString();
        validarUsuario(usuario,password);

    }
    //Metodo para el boton de Registro, nos conduce al formulario
    public void buttonRegistro(View view) {
        Intent intent = new Intent(this, Registro.class);

        startActivity(intent);
    }
    public void validarUsuario(String usuario,String contra){

        String[] keys =  new String[3];
        Object[] parametros = new String[3];
        keys[0] = "parametros";
        keys[1] = "usuario";
        keys[2] = "contrasena";

        parametros[0] = "registrado";
        parametros[1] = usuario;
        parametros[2] = contra;

        Data param = BD.createParam(keys, parametros);
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(BD.class).setInputData(param).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        if (workInfo.getState() != WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                        }
                        else{

                            Data d = workInfo.getOutputData();
                            boolean b = d.getBoolean("existe",false);
                            if(b){
                                Toast.makeText(getApplicationContext(), "existe un usuario", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),MenuInicio.class);
                                intent.putExtra("usuarioBdd",usuario); //pasando el usuario como parametro
                                //abrir el activity del menu de opciones
                                startActivity(intent);
                                finish();
                            }
                            else {
                                et_password.setText(""); //limpiamos el campo de la contraseña
                                //Lanzamos una alerta que nos de la posibilidad de registrarnos o volver a intentarlo
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle(R.string.no_cuenta);
                                builder.setMessage(usuario + getResources().getString(R.string.no_cuenta_mensaje));
                                builder.setNegativeButton(R.string.reintentar, (dialog, which) -> {

                                });
                                builder.setPositiveButton(R.string.registro, (dialog, which) -> {
                                    // Si pulsas registrar te lleva a la ventana de registro
                                    Intent intent = new Intent(this, Registro.class);
                                    startActivity(intent);
                                });
                                builder.show();


                            }

                        }
                    }

                });

        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

}
