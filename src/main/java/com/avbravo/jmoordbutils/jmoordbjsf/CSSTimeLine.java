/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.jmoordbjsf;

import java.util.ArrayList;

/**
 *
 * @author avbravo
 */
public class CSSTimeLine {

    static String[] colorDisponibles = {
        "rojo", "verde", "azul", "celeste", "naranja", "amarillo",
        "morado", "unavailable", "available",
        "maybe", "norealizado", "realizado", "aprobado",
        "cancelado", "anulado", "solicitado", "rechazado"};

    public static String rojo = "rojo";
    public static String verde = "verde";
    public static String azul = "azul";
    public static String celeste = "celeste";
    public static String naranaja = "naranja";
    public static String amarillo = "amarillo";
    public static String morado = "morado";

    public static String unavailable = "unavailable";
    public static String available = "available";
    public static String maybe = "maybe";
    public static String norealizado = "norealizado";
    public static String realizado = "realizado";
    public static String aprobado = "aprobado";
    public static String cancelado = "cancelado";
    public static String anulado = "anulado";
    public static String solicitado = "solicitado";
    public static String rechazado = "rechazado";

    // <editor-fold defaultstate="collapsed" desc="print()">
    public static void print() {
        System.out.println("-------------------------------------");
        System.out.println("Colores que puede utilizar:");
        try {
            for (int i = 0; i < colorDisponibles.length; i++) {
                System.out.println(" " + colorDisponibles[i]);

            }
        } catch (Exception e) {
            System.out.println("isValid() " + e.getLocalizedMessage());
        }
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="Boolean isValid(String color)">

    /**
     * Valida si un color es valido para csstimeline
     *
     * @param color
     * @return
     */
    public static Boolean isValid(String color) {
        Boolean valid = false;
        try {
            for (int i = 0; i < colorDisponibles.length; i++) {
                if (color.equals(colorDisponibles[i])) {
                    valid = true;
                }

            }
        } catch (Exception e) {
            System.out.println("isValid() " + e.getLocalizedMessage());
        }
        return valid;
    }
    // </editor-fold>  

}
