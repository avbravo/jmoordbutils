/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.logger;

/**
 *
 * @author avbravo
 */
public class JmoordbLoggerProperties {
    private String onlyfile;
    private String path;

    public JmoordbLoggerProperties() {
    }

    public JmoordbLoggerProperties(String onlyfile, String path) {
        this.onlyfile = onlyfile;
        this.path = path;
    }

    public String getOnlyfile() {
        return onlyfile;
    }

    public void setOnlyfile(String onlyfile) {
        this.onlyfile = onlyfile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

   
    
    
    
    
}
