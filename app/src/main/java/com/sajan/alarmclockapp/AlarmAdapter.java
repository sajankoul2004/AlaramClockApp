package com.sajan.alarmclockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    public AlarmAdapter(@NonNull Context context, List<Alarm> alarms) {
        super(context, 0, alarms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_alarm, parent, false);
        }

        Alarm alarm = getItem(position);

        TextView alarmTimeTextView = convertView.findViewById(R.id.alarmTimeTextView);
        Switch alarmSwitch = convertView.findViewById(R.id.alarmSwitch);

        if (alarm != null) {
            alarmTimeTextView.setText(alarm.getTime());
            alarmSwitch.setChecked(alarm.isEnabled());

            alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                MainActivity activity = (MainActivity) getContext();

                if (isChecked) {
                    // Set the alarm
                    activity.addAlarm(alarm.getTime(), true);
                    Toast.makeText(getContext(), "Alarm set for " + alarm.getTime(), Toast.LENGTH_SHORT).show();
                } else {
                    // Cancel the alarm
                    activity.removeAlarm(alarm.getTime());
                    Toast.makeText(getContext(), "Alarm canceled for " + alarm.getTime(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return convertView;
    }
}



