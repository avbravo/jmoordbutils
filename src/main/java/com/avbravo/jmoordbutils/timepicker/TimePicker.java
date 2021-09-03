/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.timepicker;

import java.time.LocalTime;

/**
 *
 * @author avbravo
 */
public class TimePicker {
    private String hour;
    private String minute;
    private String second;
    private String ampm;
    private LocalTime time;
    
 
    public TimePicker() {
    }

    public TimePicker(String hour, String minute, String second, String ampm, LocalTime time) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.ampm = ampm;
        this.time = time;
    }

   


    
    
    
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TimePicker{" + "hour=" + hour + ", minute=" + minute + ", second=" + second + ", ampm=" + ampm + ", time=" + time + '}';
    }
    
    
    
}
