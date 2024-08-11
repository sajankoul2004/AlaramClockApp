package com.sajan.alarmclockapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button saveAlarmButton, selectToneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        timePicker = findViewById(R.id.timePicker);
        saveAlarmButton = findViewById(R.id.saveAlarmButton);
        selectToneButton = findViewById(R.id.selectToneButton);

        saveAlarmButton.setOnClickListener(v -> saveAlarm());
        selectToneButton.setOnClickListener(v -> selectAlarmTone());
    }

    private void saveAlarm() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String alarmTime = String.format("%02d:%02d", hour, minute);

        // Save the alarm to SharedPreferences or database
        // Setup alarm with AlarmManager
        // Example code for setting an alarm

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Alarm set for " + alarmTime, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void selectAlarmTone() {
        // Launch a tone picker or use system file picker
        // Save the selected tone
    }
}
