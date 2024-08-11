package com.sajan.alarmclockapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView currentTimeTextView;
    private Handler handler = new Handler();
    private Runnable timeUpdaterRunnable;

    private ListView alarmListView;
    private AlarmAdapter alarmAdapter;
    private ArrayList<Alarm> alarmList;
    private Button setAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarmButton = findViewById(R.id.setAlarmButton);

        // Initialize the current time TextView and start updating the time
        currentTimeTextView = findViewById(R.id.currentTimeTextView);
        updateTime();

        // Initialize the ListView for alarms
        alarmListView = findViewById(R.id.alarmsListView);
        alarmList = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(this, alarmList);
        alarmListView.setAdapter(alarmAdapter);
        setAlarmButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
            startActivityForResult(intent, 1);
        });


        // Load previously set alarms
        loadAlarms();
    }

    private void updateTime() {
        timeUpdaterRunnable = new Runnable() {
            @Override
            public void run() {
                // Get the current time
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentTime = sdf.format(new Date());

                // Update the TextView with the current time
                currentTimeTextView.setText(currentTime);

                // Re-run the update after 1 second
                handler.postDelayed(this, 1000);
            }
        };

        // Start the runnable
        handler.post(timeUpdaterRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the runnable when the activity is destroyed to prevent memory leaks
        handler.removeCallbacks(timeUpdaterRunnable);
    }

    public void addAlarm(String time, boolean enabled) {
        Alarm alarm = new Alarm(time, enabled);
        alarmList.add(alarm);
        alarmAdapter.notifyDataSetChanged();

        if (enabled) {
            setAlarm(time);
        } else {
            cancelAlarm(time);
        }
    }

    public void removeAlarm(String time) {
        for (int i = 0; i < alarmList.size(); i++) {
            if (alarmList.get(i).getTime().equals(time)) {
                alarmList.remove(i);
                break;
            }
        }
        alarmAdapter.notifyDataSetChanged();
        cancelAlarm(time);
    }

    private void setAlarm(String time) {
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Log.d("AlarmDebug", "Setting alarm for: " + calendar.getTime().toString());

        Intent intent = new Intent(this, AlarmReceiver.class);
        int requestCode = (int) System.currentTimeMillis();  // Unique request code
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm set for " + time, Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelAlarm(String time) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm canceled for " + time, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAlarms() {
        // Logic to load previously set alarms from storage (e.g., SharedPreferences)
        // This is just a placeholder and should be implemented according to your storage method.
        alarmList.clear();

        // Example of adding a mock alarm
        alarmList.add(new Alarm("07:00", true));

        alarmAdapter.notifyDataSetChanged();
    }
}
