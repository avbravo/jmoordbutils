/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils.security;

import com.avbravo.avbravoutils.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author avbravo
 */
public interface LoginInterface {
 
    public String doLogin();
    public String doLogout();
    public String showAllSessions();
    public String killAllSessions();
    public String validateAllSessions();
    public String loadAllUser();
    
    // <editor-fold defaultstate="collapsed" desc="addUsername"> 

    default public Boolean addUsername(String username, HttpSession session){
      return  SessionListener.addUsername(username, session);
    }
    // </editor-fold>
    
  
    // <editor-fold defaultstate="collapsed" desc="irLogin"> 
      default public String irLogin() {
        return "/faces/login";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="logout">
 default public String logout(String path) {
        Boolean loggedIn = false;
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null) {
                session.invalidate();
            }
            String url = (path);
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.redirect(url);
            return path;
        } catch (Exception e) {
            JsfUtil.errorMessage(e, "logout()");
        }
        return path;
    }   // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="killSesionByUserName"> 

    default public Boolean killSesionByUserName(String usernameSelected)
    {
        try {
           
      return SessionListener.killSesionByUsername(usernameSelected);
                
        } catch (Exception e) {
             JsfUtil.errorMessage("killSessionByUserName() " + e.getLocalizedMessage());
        }
        return false;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="showUser"> 
     default public List<String> _getAllUser() {
       List<String> usernameList = new ArrayList<>();
        try {
            if (SessionListener.getUsernameList().isEmpty()) {
                JsfUtil.warningMessage("No hay usuarios online registrados");
                return usernameList;
            }
   
            usernameList = SessionListener.getUsernameOnline();
        } catch (Exception e) {
            JsfUtil.errorMessage("showUser() " + e.getLocalizedMessage());
        }
        return usernameList;
    }
   
// </editor-fold>
  
  
    // <editor-fold defaultstate="collapsed" desc="killAllSesion"> 
   default public String _killAllSesion() {
        try {
            if (SessionListener.killAllSesion()) {
                JsfUtil.successMessage("Se eliminaron todas las sesiones");
            } else {
                JsfUtil.successMessage("(No) se eliminaron todas las sesiones");
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("killAllSesion() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="_showAllSession"> 
   default public String _showAllSessions() {
        try {
            System.out.println("======================================");
            System.out.println("Mostrando las sesiones");
            for (HttpSession h : SessionListener.getSessionList()) {
                if(h == null){
                    System.out.println("---->  sesion es null");
                }else{
                      System.out.println("------->id " + h.getId());
                
                JsfUtil.successMessage("id " + h.getId() + " username " + h.getAttribute("username") );

                }
                

              
            }

            JsfUtil.successMessage("Sesiones mostradas");
        } catch (Exception e) {
            JsfUtil.errorMessage("showAllSession() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="validateAllUserInSesion()"> 

  default public Boolean _validateAllSesion(){
        try {
            return SessionListener.validateUsernameWithSession();
        } catch (Exception e) {
            JsfUtil.errorMessage("validateUserInSesion() "+e.getLocalizedMessage());
        }
       
        return false;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isUserLogged"> 

  default public Boolean isUserLogged(String username){
  return SessionListener.isUserLogged(username);

  }// </editor-fold>
}
