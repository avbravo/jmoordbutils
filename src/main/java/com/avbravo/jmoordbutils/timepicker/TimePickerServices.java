/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.timepicker;


import com.avbravo.jmoordbutils.DateUtil;
import com.avbravo.jmoordbutils.JsfUtil;
import jakarta.ejb.Stateless;

import java.io.Serializable;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author avbravo
 */
@Stateless
public class TimePickerServices  {
    
    
 private Map<String, String> numbers;

    public Map<String, String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Map<String, String> numbers) {
        this.numbers = numbers;
    }
 
 
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    // <editor-fold defaultstate="collapsed" desc="fillNumerber()">
   
    public void fillNumerber() {



        numbers = new HashMap<String, String>();
        for (int i = 0; i <= 59; i++) {
            if (i < 10) {
                String n = "0" + i;
                numbers.put(n.trim(), n.trim());
            } else {
                numbers.put(String.valueOf(i).trim(), String.valueOf(i).trim());
            }
        }

    }
    
    
     // <editor-fold defaultstate="collapsed" desc="TimePicker convertToTimePicker(LocalTime localTime)">
    
    /**
     *Convierte un LocalTime a un objeto TimePicker
     * @param localTime
     * @return 
     */
    public TimePicker convertToTimePicker(LocalTime localTime){
        TimePicker timePicker = new TimePicker();
        try {
            String time=DateUtil.converterLocalTimeToStringAMPM(localTime);
          timePicker = convertToTimePicker(time);
        } catch (Exception e) {
            JsfUtil.errorDialog(JsfUtil.nameOfMethod(), e.getLocalizedMessage().toString());
                   
        }
        return timePicker;
    }
// </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="TimePicker convertToTimePicker(String time)">
    public TimePicker convertToTimePicker(String time){
        TimePicker timePicker = new TimePicker();
        try {
            
             String hour="";
        String minutes="";
        String second="";
        String ampm="";
          time = time.toUpperCase().trim();
        Integer idHour= time.indexOf(":");
      
        Integer idMinute= time.lastIndexOf(":");
        Integer idAMPM= time.indexOf(" " );
        Integer isAM = time.indexOf("AM");
        Integer isPM = time.indexOf("PM");
        
        if(idHour == -1){
            JsfUtil.warningDialog("TimePicker Component","no tiene : de las horas "+time);
        }
        if(idMinute ==-1){
               JsfUtil.warningDialog("TimePicker Component","no tiene : de los minutos "+time);
        }
        if(idAMPM == -1){
             JsfUtil.warningDialog("TimePicker Component","No tiene un espacio en blanco para separar AM/PM "+time);
        }
        if(isAM ==-1 && isPM ==-1){
            JsfUtil.warningDialog("TimePicker Component","No tiene letras AM o PM "+time);
        }
        
       
            hour = time.substring(0, 2);
            minutes = time.substring((idHour+1), idHour+3);
            if(idHour == idMinute){
               // System.out.println("no hay segundos en la cadena");
                 
            }else{
               // System.out.println("hay segundos en la cadena");
                second = time.substring((idMinute+1), idMinute+3);
            }
            
           if(idAMPM == -1){
               System.out.println(" La fecha no tiene espacios para AM/PM");
               JsfUtil.errorDialog("Advertencia", "La fecha no tiene espacios para AM/PM");
           }else{
               ampm = time.substring(idAMPM+1,time.length());
           }
           
           timePicker.setHour(hour);
           timePicker.setSecond(second);
           timePicker.setMinute(minutes);
           timePicker.setAmpm(ampm.trim());
        } catch (Exception e) {
JsfUtil.errorDialog(JsfUtil.nameOfMethod(), e.getLocalizedMessage().toString());
        }
        return timePicker;
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="String toString(TimePicker timePicker, Boolean...showSeconds)">
    public String toString(TimePicker timePicker, Boolean...showSeconds){
        try {
             Boolean show = false;
        if (showSeconds.length != 0) {
            show= showSeconds[0];

        }

         return timePicker.getHour()+ ":"+timePicker.getMinute()+ (show?":"+timePicker.getSecond()+" ":" ")+timePicker.getAmpm();
        } catch (Exception e) {
JsfUtil.errorDialog(JsfUtil.nameOfMethod(), e.getLocalizedMessage().toString());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String toString(TimePicker timePicker)">
    public String toStringWithoutSeconds(TimePicker timePicker){
        try {
          

         return timePicker.getHour()+ ":"+timePicker.getMinute()+ " "+timePicker.getAmpm();
        } catch (Exception e) {
                 JsfUtil.errorDialog(JsfUtil.nameOfMethod(), e.getLocalizedMessage().toString());
        }
        return "";
    }
// </editor-fold>
    
    
      // <editor-fold defaultstate="collapsed" desc="LocalTime convertToLocalTime(TimePicker timePicker)">
    
   /***
    * Convierte TimePicker a LocalTime
    * @param timePicker
    * @return 
    */
    public LocalTime convertToLocalTime(TimePicker timePicker){
        LocalTime localTime = LocalTime.now();
        try {
             Integer hour=Integer.parseInt(timePicker.getHour());
             Integer minute=0;
             Integer seconds=0;
            if(timePicker.getAmpm().toUpperCase().equals("PM")){
                if(hour.equals(12)){
                    hour=12;
                }else{
                          hour +=12; 
                }
         
                
            }else{
                if(timePicker.getAmpm().toUpperCase().equals("AM")){
                   if(hour.equals(12)){
                       hour=0;
                   }
                }
            }
            if(timePicker.getMinute() == null || timePicker.getMinute().equals("")){
                
            }else{
                minute=Integer.parseInt(timePicker.getMinute());
            }
            if(timePicker.getSecond()== null || timePicker.getSecond().equals("")){
                
            }else{
                seconds=Integer.parseInt(timePicker.getSecond());
            }
        localTime =localTime.of(hour, minute,seconds);
            System.out.println("Nuevo timepo "+localTime);
        } catch (Exception e) {
JsfUtil.errorDialog(JsfUtil.nameOfMethod(), e.getLocalizedMessage().toString());
        }
        return localTime;
    }
// </editor-fold>
}
