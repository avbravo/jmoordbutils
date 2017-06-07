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
public interface SessionInterface {

//    public String doLogin();
//
//    public String doLogout();

   // public String showAllSessions();

   // public String killAllSessions();
   // public void verifySesionLocal();

   // public String cancelSelectedSession(BrowserSession browserSesssion);

    // <editor-fold defaultstate="collapsed" desc="addUsername"> 
    default public Boolean addUsername(String username, HttpSession session) {
        return SessionListener.addUsername(username, session);
    } // </editor-fold>

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

    // <editor-fold defaultstate="collapsed" desc="inactiveSession"> 
    default public Boolean inactiveSession(BrowserSession browserSession) {
        return SessionListener.inactiveSession(browserSession);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cancelAllSesion"> 
    default public Boolean cancelAllSesion() {
        try {
            return SessionListener.cancelAllSesion();
           

        } catch (Exception e) {
            JsfUtil.errorMessage("killAllSesion() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isUserLogged"> 
    default public Boolean isUserLogged(String username) {
        return SessionListener.isUserLogged(username);

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="allBrowserSessionList()"> 
    default List<BrowserSession> allBrowserSessionList() {
        return SessionListener.getBrowserSessionList();
    }// </editor-fold>

}
