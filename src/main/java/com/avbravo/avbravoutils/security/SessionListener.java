/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils.security;

import com.avbravo.avbravoutils.JsfUtil;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author avbravo
 */
@WebListener
public class SessionListener implements HttpSessionListener {
//public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static int numberOfSession = 0;
    private static List<BrowserSession> browserSessionList = new ArrayList<>();

    // <editor-fold defaultstate="collapsed" desc="get/set"> 
    public static List<BrowserSession> getBrowserSessionList() {
        return browserSessionList;
    }

    public static void setBrowserSessionList(List<BrowserSession> browserSessionList) {
        SessionListener.browserSessionList = browserSessionList;
    }

    public static int getNumberOfSession() {
        return numberOfSession;
    }
//
//    public static int getMaximosSegundosInactividad() {
//        return maximosSegundosInactividad;
//    }
//
//    public static void setMaximosSegundosInactividad(int maximosSegundosInactividad) {
//        SessionListener.maximosSegundosInactividad = maximosSegundosInactividad;
//    }

    public static void setNumberOfSession(int numberOfSession) {
        SessionListener.numberOfSession = numberOfSession;
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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SessionListener"> 
    public SessionListener() {
        System.out.println("call SessionListener a las " + JsfUtil.getTiempo());

        numberOfSession = 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="inicializar"> 
    public static void inicializar() {

        browserSessionList = new ArrayList<>();
        numberOfSession = 0;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sessionCreated"> 
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        session.setMaxInactiveInterval(2100);
        session.setAttribute("id", session.getId());
        LocalTime time = JsfUtil.getTiempo();
        session.setAttribute("time", time);
        session.setAttribute("ipcliente", JsfUtil.getIp());
        session.setAttribute("browser", JsfUtil.getBrowserName());
        synchronized (this) {
            numberOfSession++;
        }

        System.out.println("===========================================");
        System.out.println("------Sesion Creada-------");
        System.out.println("......# (" + numberOfSession + ")");
        System.out.println(".......id " + session.getId());
        System.out.println(".......MaxInactiveInterval " + session.getMaxInactiveInterval());

        System.out.println(".......time " + session.getAttribute("time"));
        System.out.println(".......ipcliente" + session.getAttribute("ipcliente"));
        System.out.println(".......browser" + session.getAttribute("browser"));
        System.out.println("===========================================");
        BrowserSession browserSession = new BrowserSession(session.getId(), time, JsfUtil.getIp(), JsfUtil.getBrowserName(), "", "", session);
        browserSessionList.add(browserSession);

        JsfUtil.successMessage("Se creo una sesion " + session.getId());

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sessionDestroyed"> 
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(15);

        synchronized (this) {
            if (numberOfSession > 0) {
                numberOfSession--;
            }
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("Session Destroyed ");
        System.out.println("........username " + session.getAttribute("username"));
        System.out.println("........id: " + session.getAttribute("id"));
        System.out.println("........Segundos para inactividad " + session.getMaxInactiveInterval());
        System.out.println("........ipcliente: " + session.getAttribute("ipcliente"));
        System.out.println("........time of creation: " + session.getAttribute("time"));
        System.out.println("........browser: " + session.getAttribute("browser"));
        System.out.println("--------------------------------------------------------");
        Boolean found = false;

        //Voy a renoverlo del browser
        if (removeBrowserSession(session)) {
            System.out.println("!!! quitandolo del browserSessionList");
        } else {
            System.out.println("!!! No se quitandolo del browserSessionList");
        }

//        validateUsernameWithSession();
        // printAll();
//       JsfUtil.successMessage("Se destruyo la sesion "+se.getSession().getId());
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="removeBrowserSession"> 
    private Boolean removeBrowserSession(HttpSession session) {
        Boolean found = false;
        try {

            for (BrowserSession p : browserSessionList) {
                if (p.getId().equals(session.getId())) {
                    browserSessionList.remove(p);
                    System.out.println("!!! quitandolo del browserSessionList");
                    found = true;
                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("removeBrowserSession" + e.getLocalizedMessage());
        }
        return found;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="isUserLoged"> 
    public static Boolean isUserLogged(String username) {
        Boolean found = false;
        try {
            for (BrowserSession browserSession : browserSessionList) {
                if (browserSession.getUsername().equals(username)) {
                    found = true;
                    break;
                }

            }

        } catch (Exception e) {
            JsfUtil.errorMessage("isUserLoged " + e.getLocalizedMessage());
        }
        return found;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="addUsername"> 
    /**
     * agrega el username logeado a la lista
     *
     * @param username
     * @return
     */
    public static Boolean addUsername(String username, HttpSession session, String token) {
        Boolean add = false;
        try {
            if (isUserLogged(username)) {
                return false;
            }

            Integer c = 0;

            for (BrowserSession p : browserSessionList) {
                if (p.getId().equals(session.getId())) {
                    browserSessionList.get(c).setUsername(username);
                    browserSessionList.get(c).setToken(token);
                }
                c++;
            }

            // System.out.println("---> Agregado a la sesion" + username);
        } catch (Exception e) {
            JsfUtil.errorMessage("addUsername " + e.getLocalizedMessage());
        }
        return add;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="killAllSesion()">  
// <editor-fold defaultstate="collapsed" desc="killAllSesion">  
    /**
     * mata todas las sesiones
     *
     * @return
     */
    public static Boolean cancelAllSesion() {
        try {
            for (BrowserSession browserSession : browserSessionList) {

                browserSession.getSession().invalidate();
            }

            inicializar();
            return true;
        } catch (Exception e) {
            JsfUtil.errorMessage("killAllSesion()" + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="inactiveSession"> 
    public static Boolean inactiveSession(BrowserSession browserSession) {
        try {
            if (browserSession.session == null) {
                return true;
            }
            browserSession.session.invalidate();
            browserSessionList.remove(browserSession);
            return true;
        } catch (Exception e) {
            JsfUtil.errorMessage("inactiveSession() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="inactiveSessionByToken"> 
   /**
    * Elimina la sesion por el token es util cuando un usuario no 
    * cierra su sesion y esta queda activa puede recibir un email con el token
    * @param token
    * @return 
    */
    public static Boolean inactiveSessionByToken(String token) {
        try {
            for (BrowserSession b : browserSessionList) {
                if (b.session != null) {
                    if(b.getToken().equals(token)){
                          b.session.invalidate();
                    browserSessionList.remove(b);
                    return true;
                    }
                  
                }
            }

            return false;
        } catch (Exception e) {
            JsfUtil.errorMessage("inactiveSessionByToken() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="isUsernameHaveSession">  
    /**
     * indica si tiene un session con ese username
     *
     * @param username
     * @return
     */
    public static Boolean isUsernameHaveSession(String username) {
        Boolean found = false;
        try {
            for (BrowserSession browserSession : browserSessionList) {
                if (username.equals(browserSession.getUsername())) {
                    found = true;
                    break;
                }
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("usernameHaveSession()" + e.getLocalizedMessage());
        }
        return found;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getSesionOfUsername">  
    /**
     * devuelve el session de un username
     *
     * @param username
     * @return
     */
    public static HttpSession getSesionOfUsername(String username) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        try {
            for (BrowserSession browserSession : browserSessionList) {
                if (username.equals(browserSession.getUsername())) {
                    session = browserSession.getSession();
                    break;
                }

            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getSession()" + e.getLocalizedMessage());
        }
        return session;
    }// </editor-fold>
   

}
