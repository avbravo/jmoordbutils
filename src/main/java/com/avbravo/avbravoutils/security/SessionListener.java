/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils.security;

import com.avbravo.avbravoutils.JsfUtil;
import com.avbravo.avbravoutils.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author avbravo
 */
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static int numberOfSession = 0;
    private static List<String> usernameList = new ArrayList<>();
    private static int maximosSegundosInactividad = 60;

    private static List<HttpSession> sessionList = new ArrayList<>();
   
    
    
    public static List<HttpSession> getSessionList() {
        return sessionList;
    }

    public static void setSessionList(List<HttpSession> sessionList) {
        SessionListener.sessionList = sessionList;
    }

    public SessionListener() {

        usernameList = new ArrayList<>();
        numberOfSession = 0;
    }

    public static void inicializar() {
        sessionList = new ArrayList<>();
      
        usernameList = new ArrayList<>();
    }

    public static int getNumberOfSession() {
        return numberOfSession;
    }

    public static int getMaximosSegundosInactividad() {
        return maximosSegundosInactividad;
    }

    public static void setMaximosSegundosInactividad(int maximosSegundosInactividad) {
        SessionListener.maximosSegundosInactividad = maximosSegundosInactividad;
    }

//    public int getNumberOfSession() {
//        return numberOfSession;
//    }
//
//    public void setNumberOfSession(int numberOfSession) {
//        this.numberOfSession = numberOfSession;
//    }
    public static void setNumberOfSession(int numberOfSession) {
        SessionListener.numberOfSession = numberOfSession;
    }

    public static List<String> getUsernameList() {
        return usernameList;
    }

    public static void setUsernameList(List<String> usernameList) {
        SessionListener.usernameList = usernameList;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sessionList.add(session);
        if (maximosSegundosInactividad < 0) {
            maximosSegundosInactividad = 60;
        }
        session.setMaxInactiveInterval(maximosSegundosInactividad);
        // System.out.println("id = " + session.getId());
        session.setAttribute("id", session.getId());
        synchronized (this) {
            numberOfSession++;
        }

//        System.out.println("===================================");
//        System.out.println("Sesion Creaada");
//        System.out.println("Number of Session " + numberOfSession);
//        System.out.println("id " + session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(60);

        synchronized (this) {
            if (numberOfSession > 0) {
                numberOfSession--;
            }
        }
        usernameList.remove(session.getAttribute("username"));
//        System.out.println("--------------------------------------------------------");
//        System.out.println("Session Destroyed " + numberOfSession);
//        System.out.println("username " + session.getAttribute("username"));
//        System.out.println("d: " + session.getAttribute("id"));
//        System.out.println("--------------------------------------------------------");
    }

    public void attributeAdded(HttpSessionBindingEvent arg0) {

        //   System.out.println("value is added ");
    }

    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // System.out.println("value is removed");

    }

    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        //System.out.println("value has been replaced");

    }

    public static Boolean isUserLoged(String username) {
        Boolean found = false;
        try {
            for (String s : SessionListener.getUsernameList()) {
                if (s.equals(username)) {
                    found = true;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("isUserLoged " + e.getLocalizedMessage());
        }
        return found;
    }

    /**
     * agrega el username logeado a la lista
     *
     * @param username
     * @return
     */
    public static Boolean addUsername(String username) {
        Boolean add = false;
        try {
            if (isUserLoged(username)) {
                return false;
            }
            usernameList.add(username);
        } catch (Exception e) {
            JsfUtil.errorMessage("addUsername " + e.getLocalizedMessage());
        }
        return add;
    }

    public static List<String> getUsernameOnline() {
        return usernameList;
    }

    /**
     * mata todas las sesiones
     *
     * @return
     */
    public static Boolean killAllSesion() {
        try {
            sessionList.forEach((s) -> {
                s.invalidate();
            });
            inicializar();
            return true;
        } catch (Exception e) {
            JsfUtil.errorMessage("killAllSesion()" + e.getLocalizedMessage());
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="killSesionById">  
    /**
     *
     * @param id
     * @return
     */
    public static Boolean killSesionById(String id) {
        Boolean kill = false;
        try {
            for (HttpSession s : sessionList) {
                if (s.getId().equals(id)) {
                    s.invalidate();
                    sessionList.remove(s);
                    kill = true;
                    break;
                }
            }

            return kill;
        } catch (Exception e) {
            JsfUtil.errorMessage("killSesionById()" + e.getLocalizedMessage());
        }
        return kill;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="killSesionById">  
    /**
     *
     * @param id
     * @return
     */
    public static Boolean killSesionByUsername(String username) {
        Boolean kill = false;
        try {
            for (HttpSession s : sessionList) {
                if (s.getAttribute("username").equals(username)) {
                    s.invalidate();
                    sessionList.remove(s);
                    kill = true;
                    break;
                }
            }

            return kill;
        } catch (Exception e) {
            JsfUtil.errorMessage("killSesionByUsername()" + e.getLocalizedMessage());
        }
        return kill;
    }// </editor-fold>
    
    
}
