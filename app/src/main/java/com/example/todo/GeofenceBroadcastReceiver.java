package com.example.todo;

import static com.example.todo.MapsActivity.funt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;
import java.util.Locale;

public class GeofenceBroadcastReceiver extends BroadcastReceiver  {


    private NoteViewModel noteViewModel;
    private static final String TAG = "GeofenceBroadcastReceiv";

    TextToSpeech textToSpeech;


    String message = "Notifomatic";



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // Toast.makeText(context, "Geofence triggered", Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence : geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }

//        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//                if (i == TextToSpeech.SUCCESS) {
//                    //SElect lang
//                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
//
//                }
//            }
//        });
//        Location location = geofencingEvent.getTriggeringLocation();
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:



//                int speech=textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null);

                funt();

                Toast.makeText(context, "You have entered Inside Geofence ;)", Toast.LENGTH_SHORT).show();

                notificationHelper.sendHighPriorityNotification(message, "Hii , This is Notifomatic Here!!\nAlert⚠️⚠️ You have a work🧑‍💻 here You Can Check Your Todo List📃 and Complete your task.\nThank You!!", MapsActivity.class);

                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "You are already inside Geofence :)", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification(message, "Hii , This is Notifomatic Here!!\nAlert⚠️⚠️ You have a work🧑‍💻 here You Can Check Your Todo List📃 and Complete your task.\nThank You!!", MapsActivity.class);

                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "You have exited  Geofence ;)", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification(message, "Hii , This is Notifomatic Here!!\nAlert⚠️⚠️ You have a work🧑‍💻 here You Can Check Your Todo List📃 and Complete your task.\nThank You!!", MapsActivity.class);

                break;
        }


    }


}