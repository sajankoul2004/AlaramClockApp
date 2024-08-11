package com.sajan.alarmclockapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Show a toast message when the alarm goes off
        Toast.makeText(context, "Alarm ringing!", Toast.LENGTH_LONG).show();

        // Play the default alarm sound
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            // Fallback to notification sound if alarm sound is unavailable
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Log.d("AlarmReceiver", "Alarm sound not found, using notification sound");
        }

        ringtone = RingtoneManager.getRingtone(context, alarmUri);

        if (ringtone != null) {
            ringtone.play();
        } else {
            Log.e("AlarmReceiver", "Ringtone is null, unable to play sound");
        }
    }
}
