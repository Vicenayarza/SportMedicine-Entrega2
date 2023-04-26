package com.example.proyecto1_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


import java.util.concurrent.atomic.AtomicBoolean;

public class Registro extends AppCompatActivity {

    public EditText et_nombre,et_apellido, et_usuario, et_contraseña, et_contraseña2, et_telefono;
   // BaseDatos bdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        et_nombre = findViewById(R.id.et_nom);
        et_apellido = findViewById(R.id.et_apellido);
        et_usuario = findViewById(R.id.et_usuario);
        et_contraseña = findViewById(R.id.et_contraseña);
        et_contraseña2 = findViewById(R.id.et_contraseña2);
        et_telefono = findViewById(R.id.et_telefono);
    }
    public void buttonSignUp(View view){
        String nombres=et_nombre.getText().toString();
        String apellidos=et_apellido.getText().toString();
        String usuario=et_usuario.getText().toString();
        String telefono=et_telefono.getText().toString();
        String contra=et_contraseña.getText().toString();
        String contra2=et_contraseña2.getText().toString();
        //Comprobamos si los campos están bien rellenados
        if (nombres.isEmpty() ||apellidos.isEmpty() ||telefono.isEmpty() ||usuario.isEmpty()  ||contra.isEmpty() ||contra2.isEmpty()){ //si algun campo esta vacio
            Toast.makeText(getApplicationContext(), "Para continuar con el registro llene todos los campos solicitados",
                    Toast.LENGTH_LONG).show(); //mostrando mensaje de campo vacio a traves de un toast
        }
        else {

            if (NoNumeros(nombres) == false) {
                    Toast.makeText(getApplicationContext(), "El nombre no debe contener numeros",
                            Toast.LENGTH_LONG).show(); //mostrando error de nombre
            }
            else {
                if (NoNumeros(apellidos) == false) {
                        Toast.makeText(getApplicationContext(), "El apellido no debe contener numeros",
                                Toast.LENGTH_LONG).show(); //mostrando error de apellido
                }
                else {
                    if (validartelefono(telefono) == false) {
                            Toast.makeText(getApplicationContext(), "El numero de télefono debe tener 9 digitos, empezar con 6 o 7 y no tener letras ",
                                    Toast.LENGTH_LONG).show(); //mostrando error de número de telefono
                    }
                    else {
                        if (contra.length() < 8) {
                                    Toast.makeText(getApplicationContext(), "Ingrese una contraseña mínimo de 8 dígitos",
                                            Toast.LENGTH_LONG).show(); //mostrando mensaje de contraseña invalida
                        }
                        else {
                            if (validarcontra(contra) == false) {
                                        Toast.makeText(getApplicationContext(), "la contraseña debe tener numeros y letras",
                                                Toast.LENGTH_LONG).show(); //mostrando mensaje de contraseña invalida
                            }
                            else {
                                if (contra.equals(contra2)) {
                                    comprobar(usuario, contra, nombres, apellidos, telefono);
                                }

                                else {
                                            Toast.makeText(getApplicationContext(), "Las contraseñas ingresadas no coinciden",
                                                    Toast.LENGTH_LONG).show(); //mostrando un mensaje de error al no poder iniciar
                                }

                            }
                        }
                    }
                }
            }
        }
    }
//Utilizamos un fragment para la eleccion de la fecha de nacimiento


//Método para comprobar los nombres y apellidos
    public boolean NoNumeros(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c =='ñ' || c=='Ñ'
                    || c=='á' || c=='é' || c=='í' || c=='ó' || c=='ú'
                    || c=='Á' || c=='É' || c=='Í' || c=='Ó' || c=='Ú')) {
                return false;
            }
        }
        return true;
    }
    public boolean validartelefono(String telefono) {
        if (telefono.length() != 9){
            return false;
        } else{
            for (int x = 0; x < telefono.length(); x++) {
              char c = telefono.charAt(x);
               //si  no tiene numeros
                if (!(c >= '0' && c <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validarcontra(String password) {
        boolean numeros = false;
        boolean letras = false;
        for (int x = 0; x < password.length(); x++) {
            char c = password.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')  || c =='ñ' || c=='Ñ'
                    || c=='á' || c=='é' || c=='í' || c=='ó' || c=='ú'
                    || c=='Á' || c=='É' || c=='Í' || c=='Ó' || c=='Ú')) {
                letras = true;
            }
            if ((c >= '0' && c <= '9') ) {
                numeros = true;
            }

        }
        if (numeros == true && letras ==true){
            return true;
        }
        return false;
    }
    public void añadirusuario(String usuario,String contra,String nombres,String apellidos,String telefono){
        String[] keys =  new String[6];
        Object[] parametros = new String[6];
        keys[0] = "parametros";
        keys[1] = "usuario";
        keys[2] = "contrasena";
        keys[3] = "nombre";
        keys[4] = "apellido";
        keys[5] = "telefono";
        parametros[0] = "Registro";
        parametros[1] = usuario;
        parametros[2] = contra;
        parametros[3] = nombres;
        parametros[4] = apellidos;
        parametros[5] = telefono;

        Data param = BD.createParam(keys, parametros);
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(BD.class).setInputData(param).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        if (workInfo.getState() != WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(getApplicationContext(), "errror", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Data d = workInfo.getOutputData();
                            boolean b = d.getBoolean("exito",false);
                            if(b){
                                Toast.makeText(getApplicationContext(), "Registrado exitosamente",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);


    }
    public void comprobar(String usuario,String contra,String nombres,String apellidos,String telefono){
        AtomicBoolean resultado= new AtomicBoolean(true);
        String[] keys =  new String[2];
        Object[] parametros = new String[2];
        keys[0] = "parametros";
        keys[1] = "usuario";

        parametros[0] = "comprobar";
        parametros[1] = usuario;

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
                            boolean b = d.getBoolean("exito",false);
                            if(b){
                                Toast.makeText(getApplicationContext(), "existe un usuario", Toast.LENGTH_LONG).show();
                            }
                            else {
                                añadirusuario(usuario,contra,nombres,apellidos,telefono);
                                Toast.makeText(getApplicationContext(), "Usuario registrado correctamente",
                                        Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado

                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        }
                    }

                });

        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);




    }


}
