/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.logger;



import static com.avbravo.jmoordbutils.JsfUtil.errorDialog;
import jakarta.faces.bean.SessionScoped;
import jakarta.inject.Named;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

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
                        .getClassLoader().getResourceAsStream("jmoordblogger.properties");
                Properties prop = new Properties();

                if (inputStream != null) {
                     prop.load(inputStream);


                    if (prop.getProperty("onlyfile") == null) {
                         errorDialog("JmoordbLoggerProducer.readProperties()", "No se encontro el onlyfile del archivo jmoordblogger.prppertoes");
                        System.out.println("Advertencia: " + "No se encontro el onlyfile del archivo jmoordblogger.prppertoes");
                    } else {
                       jmoordbLogger.setOnlyfile(prop.getProperty("onlyfile"));
                    }
                  
                    if (prop.getProperty("path") == null) {
                        errorDialog("JmoordbLoggerProducer.readProperties()",  "No se encontro el nombre de la base de datos del servidor de la base de datos");
                        System.out.println("Advertencia: " + "No se encontro el nombre de la base de datos del servidor de la base de datos");
                    } else {
                      jmoordbLogger.setPath(prop.getProperty("path"));
                    }

                

                } else {
                    errorDialog("JmoordbLoggerProducer.readProperties()", "No se puede cargar el archivo jmoordblogger.properties");
                    System.out.println("No se puede cargar el archivo jmoordblogger.properties");
                }

            }
        } catch (Exception e) {
            System.out.println("ConnectionProducer.readProperties() " + e.getLocalizedMessage());
             errorDialog("JmoordbLoggerProducer.readProperties()", e.getLocalizedMessage());
        }
        return jmoordbLogger;
    }
    // </editor-fold>

}
