/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.logger;



import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@SessionScoped
public class JmoordbLoggerProducer implements Serializable {

    private String path = null;
    JmoordbLoggerProperties  jmoordbLogger = new JmoordbLoggerProperties("true", "");

   

    // <editor-fold defaultstate="collapsed" desc="OrientdbProperties readProperties()">
    /**
     * Lee los archivos de configuracion de los properties
     *
     * @return
     */
    public JmoordbLoggerProperties readProperties() {

        try {

            if (path== null) {

                InputStream inputStream = getClass()
                        .getClassLoader().getResourceAsStream("orientdb.properties");
                Properties prop = new Properties();

                if (inputStream != null) {
                     prop.load(inputStream);


                    if (prop.getProperty("onlyfile") == null) {
                        System.out.println("Advertencia: " + "No se encontro el onlyfile del archivo jmoordblogger.prppertoes");
                    } else {
                       jmoordbLogger.setOnlyfile(prop.getProperty("onlyfile"));
                    }
                  
                    if (prop.getProperty("path") == null) {
                        System.out.println("Advertencia: " + "No se encontro el nombre de la base de datos del servidor de la base de datos");
                    } else {
                      jmoordbLogger.setPath(prop.getProperty("path"));
                    }

                

                } else {
                    System.out.println("No se puede cargar el archivo connection.properties");
                }

            }
        } catch (Exception e) {
            System.out.println("ConnectionProducer.readProperties() " + e.getLocalizedMessage());
        }
        return jmoordbLogger;
    }
    // </editor-fold>

}
