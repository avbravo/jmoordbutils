/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @authoravbravo
 */
@Named
@SessionScoped
public class JmoordbLanguages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    JmoordbResourcesFiles rf;
    private String locale = Locale.getDefault().getDisplayLanguage();
    Locale infoLocaleDate = Locale.getDefault();

    //private String bandera;
    private String icono;

    
    // <editor-fold defaultstate="collapsed" desc="get/set()">
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public synchronized String getLocale() {
        if (locale == null) {
            locale = "es";
        }
        return locale;
    }

    public synchronized String changeLanguage() {
        return "changed";
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

// </editor-fold>
    
    @PostConstruct
    public void init() {
       icono = "panama.png";
        spanishAction();
    }

    
   
   
    
    
    
    public JmoordbLanguages() {
        this.locale = "es";
    }

    // <editor-fold defaultstate="collapsed" desc="String englishAction()">
     public String englishAction() {
       icono = "estadosunidos.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        infoLocaleDate = Locale.ENGLISH;
        this.locale = "en";
        rf.saveLocale();
        return null;
    }
// </editor-fold>
   
// <editor-fold defaultstate="collapsed" desc="String spanishAction()">
      public String spanishAction() {
         icono= "panama.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("es"));
        infoLocaleDate = new Locale("es");
        this.locale = "es";
        rf.saveLocale();
        return null;
    }
// </editor-fold>  
   

      // <editor-fold defaultstate="collapsed" desc="String portugueseAction()">
       public String portugueseAction() {
       icono = "brasil.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pt", "BR"));
        infoLocaleDate = new Locale("pt", "BR");
        this.locale = "es";
        rf.saveLocale();
        return null;

    }
// </editor-fold>
   
// <editor-fold defaultstate="collapsed" desc="String spanishSpainAction()">
        public String spanishSpainAction() {
       icono = "espana.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("es", "ES"));
        infoLocaleDate = new Locale("es", "ES");
        this.locale = "es";
        rf.saveLocale();
        return null;
    }
// </editor-fold>
   
// <editor-fold defaultstate="collapsed" desc="String francesAction()">
    public String francesAction() {
      icono= "francia.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.FRANCE);
        infoLocaleDate = Locale.FRANCE;
        this.locale = "fr";
        rf.saveLocale();
        return null;
    }

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="String alemaniaAction()">
    public String alemaniaAction() {
       icono = "alemania.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.FRANCE);
        infoLocaleDate = Locale.GERMAN;
        this.locale = "gr";
        rf.saveLocale();
        return null;
    }
    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="String italiaAction()">
    public String italiaAction() {
       icono = "brasil.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ITALIAN);
        infoLocaleDate = Locale.ITALIAN;
        this.locale = "il";
        rf.saveLocale();
        return null;
    }
    
    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc=" Locale infoLocale()">
    public Locale infoLocale() {
        try {

            // Locale.getDefault().getLanguage();//       ---> en      
//Locale.getDefault().getISO3Language()   ---> eng 
//Locale.getDefault().getCountry()        ---> US 
//Locale.getDefault().getISO3Country()    ---> USA 
//Locale.getDefault().getDisplayCountry() ---> United States 
//Locale.getDefault().getDisplayName()    ---> English (United States) 
//Locale.getDefault().toString()          ---> en_US
//Locale.getDefault().getDisplayLanguage()---> English
        } catch (Exception e) {
        }
        return infoLocaleDate;
    }
    // </editor-fold>

}
