package com.sajan.alarmclockapp;

public class Alarm {
    private String time;   // Alarm time in "HH:mm" format
    private boolean enabled; // Whether the alarm is enabled

    public Alarm() {
        // Default constructor required for calls to DataSnapshot.getValue(Alarm.class)
    }

    public Alarm(String time, boolean enabled) {
        this.time = time;
        this.enabled = enabled;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
