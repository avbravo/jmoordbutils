/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.logger;

import static com.avbravo.jmoordbutils.JsfUtil.errorDialog;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@SessionScoped
public class JmoordbLogger implements Serializable {

    @Inject
    JmoordbLoggerProducer jmoordbLoggerProducer;

    public JmoordbLogger() {
        try {
            System.out.println("-----------------JmoordbLogger()-----------------------------");
            String onlyfile = jmoordbLoggerProducer.readProperties().getOnlyfile();
            String path = jmoordbLoggerProducer.readProperties().getPath();
            System.out.println("==================================");
            System.out.println(" onlyfile " + onlyfile);
            System.out.println(" path " + path);
        } catch (Exception e) {
            errorDialog("JmoordbLogger()", e.getLocalizedMessage());
        }

    }

}
