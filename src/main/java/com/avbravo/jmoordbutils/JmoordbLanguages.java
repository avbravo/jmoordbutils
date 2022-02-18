/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;

import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @authoravbravo
 */
@Named
@SessionScoped
public class JmoordbLanguages implements Serializable {
// <editor-fold defaultstate="collapsed" desc=" field()">
    private static final long serialVersionUID = 1L;
    @Inject
    JmoordbResourcesFiles rf;
    private String locale = Locale.getDefault().getDisplayLanguage();
    Locale infoLocaleDate = Locale.getDefault();

    //private String bandera;
    private String icono;
    private String returnTo="/faces/index.xhtml?faces-redirect=true";
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="get/set()">

    public String getReturnTo() {
        return returnTo;
    }

    public void setReturnTo(String returnTo) {
        this.returnTo = returnTo;
    }
    
    
    
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        if (locale == null) {
            System.out.println("----> locale es nulll");
            locale = "es";
        }else  {
            System.out.println("-----> locale es");
        }
        return locale;
    }

    public  String changeLanguage() {
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
       icono = "banderas/panama.png";       
        System.out.println("------> init locale"+locale);
        spanishAction();
    }

    
   
   
    
    
    
    public JmoordbLanguages() {
        this.locale = "es";
    }

    // <editor-fold defaultstate="collapsed" desc="String englishAction()">
     public String englishAction() {
       icono = "banderas/estadosunidos.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        infoLocaleDate = Locale.ENGLISH;
        this.locale = "en";
         System.out.println("--------------> cambio a english locale");
        rf.saveLocale();
        return returnTo;
       // return null;
    }
// </editor-fold>
   
// <editor-fold defaultstate="collapsed" desc="String spanishAction()">
      public String spanishAction() {
         icono= "banderas/panama.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("es"));
        infoLocaleDate = new Locale("es");
        this.locale = "es";
        rf.saveLocale();
        return returnTo;
    }
// </editor-fold>  
   

      // <editor-fold defaultstate="collapsed" desc="String portugueseAction()">
       public String portugueseAction() {
       icono = "banderas/brasil.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pt", "BR"));
        infoLocaleDate = new Locale("pt", "BR");
        this.locale = "es";
        rf.saveLocale();
     return returnTo;

    }
// </editor-fold>
   
// <editor-fold defaultstate="collapsed" desc="String spanishSpainAction()">
        public String spanishSpainAction() {
       icono = "banderas/espana.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("es", "ES"));
        infoLocaleDate = new Locale("es", "ES");
        this.locale = "es";
        rf.saveLocale();
        return returnTo;
    }
// </editor-fold>
   
// <editor-fold defaultstate="collapsed" desc="String francesAction()">
    public String francesAction() {
      icono= "banderas/francia.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.FRANCE);
        infoLocaleDate = Locale.FRANCE;
        this.locale = "fr";
        rf.saveLocale();
     return returnTo;
    }

    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="String alemaniaAction()">
    public String alemaniaAction() {
       icono = "banderas/alemania.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.FRANCE);
        infoLocaleDate = Locale.GERMAN;
        this.locale = "gr";
        rf.saveLocale();
       return returnTo;
    }
    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="String italiaAction()">
    public String italiaAction() {
       icono = "banderas/brasil.png";
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ITALIAN);
        infoLocaleDate = Locale.ITALIAN;
        this.locale = "il";
        rf.saveLocale();
          return returnTo;
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
