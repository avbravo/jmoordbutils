/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils.security;

import com.avbravo.avbravoutils.JsfUtil;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author avbravo
 */
public interface SecurityInterface {

//    public String doLogin();
//
//    public String doLogout();
    // public String showAllSessions();
    // public String killAllSessions();
    // public void verifySesionLocal();
    // public String cancelSelectedSession(BrowserSession browserSesssion);
    // <editor-fold defaultstate="collapsed" desc="addUsername"> 
    default public Boolean addUsername(String username, HttpSession session, String token,Integer maxSegundosParaInactividad) {
        return SessionListener.addUsername(username, session, token,maxSegundosParaInactividad);
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
    // <editor-fold defaultstate="collapsed" desc="sessionOfUsername">

    default public HttpSession sessionOfUsername(String username) {
        return SessionListener.sesionOfUsername(username);
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="inactiveSessionByToken"> 

    default public Boolean inactiveSessionByToken(String token, String username) {
        return SessionListener.inactiveSessionByToken(token, username);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="inactiveSessionByUsername"> 
        default public Boolean inactiveSessionByUsername( String username) {
        return SessionListener.inactiveSessionByUsername( username);
    }
// </editor-fold>
    
    default public Boolean destroyByUsername(String username){
        Boolean destroyed = false;
        try {

            HttpSession httpSession = sessionOfUsername(username);

            if (httpSession != null) {
            

                    if (inactiveSessionByUsername(username)) {
                        JsfUtil.successMessage("Se inactivo la sesion para el usuario." + username + "  Intente ingresar ahora");

                        return true;
                    } else {
                        JsfUtil.warningMessage("No se puede inactivar la session para el usuario "+username);
                        return false;
                    }
               
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("destroyByUsername() " + e.getLocalizedMessage());
        }
        return false;
    }
    // <editor-fold defaultstate="collapsed" desc="destroyWithToken"> 
    default public Boolean destroyByToken(String username, String mytoken) {
        Boolean destroyed = false;
        try {

            HttpSession httpSession = sessionOfUsername(username);

            if (httpSession != null) {
                String token = httpSession.getAttribute("token").toString();
                if (mytoken.equals(token)) {

                    if (inactiveSessionByToken(token, username)) {
                        JsfUtil.successMessage("Se inactivo la sesion para el usuario." + username + "  Intente ingresar ahora");

                        return true;
                    } else {
                        JsfUtil.warningMessage("No se puede inactivar la session para el token");
                        return false;
                    }
                } else {
                    JsfUtil.warningMessage("El token no coincide con el enviado a su email");
                    return false;
                }
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("destroyWithToken() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tokenOfUsername"> 
    default public String tokenOfUsername(String username) {
        String token = "";
        try {
            HttpSession httpSession = sessionOfUsername(username);
            if (httpSession != null) {
                token = httpSession.getAttribute("token").toString();
            } else {
                JsfUtil.warningMessage("No se pudo localizar una sesion activa para el usuario " + username);
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getTokenOfUsername() " + e.getLocalizedMessage());
        }
        return token;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="usernameRecoveryOfSession"> 
    default public String usernameRecoveryOfSession() {
        String usernameRecover = "";
        try {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession();
            if (session != null) {
                if (session.getAttribute("username") != null) {
                    usernameRecover = session.getAttribute("username").toString();
                }//                
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("verifySesionLocal() " + e.getLocalizedMessage());
        }
        return usernameRecover;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="invalidateMySession("> 

    /**
     * invalida la sesion actual
     *
     * @return
     */
    default public Boolean invalidateMySession() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession();
            session.invalidate();
            return true;
        } catch (Exception e) {
            JsfUtil.successMessage("invalidateMySession() " + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="saveUserInSession"> 
    /**
     * guarda los datos del usuario logeado en la sesion
     *
     * @return
     */
    default public Boolean saveUserInSession(String username, Integer maxSegundosParaInactividad) {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession();

            session.setAttribute("username", username);

        session.setMaxInactiveInterval(maxSegundosParaInactividad);
            String token = JsfUtil.getUUID();
            token = token.substring(0, 6);

            session.setAttribute("token", token);
            //indicar el tiempo de la sesion predeterminado 2100segundos

            addUsername(username, session, token,maxSegundosParaInactividad);
            return true;
        } catch (Exception e) {
            JsfUtil.successMessage("saveUserInSession() " + e.getLocalizedMessage());
        }
        return false;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nombre_metodo"> 
    default public Date getDateTiemExpiration(HttpSession session) {
        // Integer restante = 0;
        Date expiry = new Date();
        try {
            //   Integer limite = JsfUtil.milisegundosToSegundos(session.getCreationTime()) + session.getMaxInactiveInterval();

            expiry = new Date(session.getCreationTime() + session.getMaxInactiveInterval() * 1000);

            // restante = inactivatePeriodo - JsfUtil.milisegundosToSegundos(milisegundos);
        } catch (Exception e) {
            JsfUtil.errorMessage("getDateTiemExpiration() " + e.getLocalizedMessage());
        }
        return expiry;
    }// </e
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="secondsForInactivate"> 
    default public Long milisegundosForInactivate(HttpSession session) {
        
       Integer resultado=0;


        try {
            
           Long  inactivatePeriodo =session.getCreationTime() + ( session.getMaxInactiveInterval()/1000);
          //  Long milisegundos = session.getLastAccessedTime() - session.getCreationTime();
            Long actual = JsfUtil.fechaActualEnMilisegundos();
        
            if(inactivatePeriodo > actual){
                System.out.println(" inactivatePeriodo "+ inactivatePeriodo + " actual" +actual);
                return (inactivatePeriodo - actual);
          
            }else{
                return resultado.longValue();
            }
        

        } catch (Exception e) {
            JsfUtil.errorMessage("getSecondsForInactivate() " + e.getLocalizedMessage());
        }
        return resultado.longValue();
    }// <
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="miliSecondsOfConnection"> 
    default public Integer miliSecondsOfConnection(HttpSession session) {
        Integer segundos = 0;
        try {
//            Long diferences =  session.getLastAccessedTime() - session.getCreationTime();
         Long diferences = JsfUtil.fechaActualEnMilisegundos()- session.getCreationTime();
            
            return diferences.intValue();
        } catch (Exception e) {
            JsfUtil.errorMessage("getMiliSecondsOfConnection() " + e.getLocalizedMessage());
        }
        return segundos;
    }
// </editor-fold>
}