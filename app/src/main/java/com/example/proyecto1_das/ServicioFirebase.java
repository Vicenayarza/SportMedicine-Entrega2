package com.example.proyecto1_das;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {
    public ServicioFirebase() {
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("fcm", "Refreshed token: " + token);
        FirebaseMessaging.getInstance().subscribeToTopic("ALERTS");
    }


    //notificacion que se verá en el móvil
    public void onMessageReceived (RemoteMessage remoteMessage){

        if (remoteMessage.getData().size() > 0){
            Log.d("Prueba_Mensaje", "El mensaje 1 --> " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null){
            Log.d("Prueba_Mensaje", "El mensaje 2 --> " + remoteMessage.getNotification().getBody());

            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(ServicioFirebase.this, "id_canal");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel elCanal = new NotificationChannel("id_canal", "Mensajeria_FCM", NotificationManager.IMPORTANCE_DEFAULT);
                elManager.createNotificationChannel(elCanal);
                elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(remoteMessage.getNotification().getTitle()) //Titulo del mensaje FCM
                        .setContentText(remoteMessage.getNotification().getBody()) //Cuerpo del mensaje FCM
                        .setVibrate(new long[] {0, 1000, 500, 1000})
                        .setAutoCancel(false);
                elManager.notify(1, elBuilder.build());
            }


        }
    }
}
