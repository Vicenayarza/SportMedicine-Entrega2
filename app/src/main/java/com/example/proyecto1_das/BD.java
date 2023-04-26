package com.example.proyecto1_das;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BD extends Worker{
    public BD(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String accion = getInputData().getString("parametros");
        assert accion != null;
        if (accion.equals("Registro")) {
            String dir = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/vayarza001/WEB/basedatos.php";
            HttpURLConnection urlConnection = null;
            String usuario = getInputData().getString("usuario");
            String contrasena = getInputData().getString("contrasena");
            String nombre = getInputData().getString("nombre");
            String apellido = getInputData().getString("apellido");
            String telefono = getInputData().getString("telefono");

            try {
                URL dest = new URL(dir);
                urlConnection = (HttpURLConnection) dest.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                JSONObject paramJson = new JSONObject();
                paramJson.put("parametro","registro");
                paramJson.put("usuario", usuario);
                paramJson.put("contrasena", contrasena);
                paramJson.put("nombre", nombre);
                paramJson.put("apellido", apellido);
                paramJson.put("telefono", telefono);
                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(paramJson.toString());
                out.close();
                int statusCode = urlConnection.getResponseCode();
                Log.d("Prueba","Titooos"+paramJson);
                Log.d("respuesta","Titooos"+statusCode);
                if (statusCode == 200) {
                    BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line;
                    StringBuilder result = new StringBuilder();
                    Log.d("respuesta","Titooos ha entrado2");
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                        Log.d("respuesta","Titooos ha entrado3");
                    }
                    Log.d("respuesta","Titooos"+result);
                    inputStream.close();
                    Log.d("respuesta","Titooos ha entrado5");
                    boolean exito = result.toString().equals("true");

                    Data.Builder b = new Data.Builder();
                    return Result.success(b.putBoolean("exito", exito).build());
                }
            } catch (Exception e) {
                Log.e("EXCEPTION", "doWork: ", e);
                return Result.failure();
            }

        } else if (accion.equals("comprobar")) {
            String dir = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/vayarza001/WEB/comprobar.php";
            HttpURLConnection urlConnection = null;
            String usuario = getInputData().getString("usuario");
            String parametro = "comprobar";

            try {
                URL dest = new URL(dir);
                urlConnection = (HttpURLConnection) dest.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                JSONObject paramJson = new JSONObject();
                paramJson.put("parametro",parametro);
                paramJson.put("usuario", usuario);

                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(paramJson.toString());
                out.close();
                int statusCode = urlConnection.getResponseCode();
                Log.d("Prueba","Titooos"+paramJson);
                Log.d("respuesta","Titooos"+statusCode);
                if (statusCode == 200) {
                    BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line;
                    StringBuilder result = new StringBuilder();
                    Log.d("respuesta","Titooos ha entrado2");
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                        Log.d("respuesta","Titooos ha entrado3");
                    }
                    Log.d("respuesta","Titooos"+result);
                    inputStream.close();
                    Log.d("respuesta","Titooos ha entrado5");
                    boolean exito = result.toString().equals("true");

                    Data.Builder b = new Data.Builder();
                    return Result.success(b.putBoolean("existe", exito).build());
                }
            } catch (Exception e) {
                Log.e("EXCEPTION", "doWork: ", e);
                return Result.failure();
            }

        } else if (accion.equals("registrado"))  {
            String dir = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/vayarza001/WEB/Comprobaruyc.php";
            HttpURLConnection urlConnection = null;
            String usuario = getInputData().getString("usuario");
            String contrasena = getInputData().getString("contrasena");
            try {
                URL dest = new URL(dir);
                urlConnection = (HttpURLConnection) dest.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                JSONObject paramJson = new JSONObject();
                paramJson.put("parametro","comprobar");
                paramJson.put("usuario", usuario);
                paramJson.put("contrasena",contrasena);

                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(paramJson.toString());
                out.close();
                int statusCode = urlConnection.getResponseCode();
                Log.d("Prueba","Titooos"+paramJson);
                Log.d("respuesta","Titooos"+statusCode);
                if (statusCode == 200) {
                    BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line;
                    StringBuilder result = new StringBuilder();
                    Log.d("respuesta","Titooos ha entrado2");
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                        Log.d("respuesta","Titooos ha entrado3");
                    }
                    Log.d("respuesta","Titooos"+result);
                    inputStream.close();
                    Log.d("respuesta","Titooos ha entrado5");
                    boolean exito = result.toString().equals("true");

                    Data.Builder b = new Data.Builder();
                    return Result.success(b.putBoolean("existe", exito).build());
                }
            } catch (Exception e) {
                Log.e("EXCEPTION", "doWork: ", e);
                return Result.failure();
            }

        }

        return Result.success();
    }
    public static Data createParam(String[] keys, Object[] params) {
        Data.Builder b = new Data.Builder();
        for (int i = 0; i < keys.length; i++) {
            if (params[i] instanceof Integer) {
                b.putInt(keys[i], (Integer) params[i]);
            } else if (params[i] instanceof String) {
                b.putString(keys[i], (String) params[i]);
            }
        }
        return b.build();
    }

}
