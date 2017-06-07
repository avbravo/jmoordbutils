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
public class SessionListener_old implements HttpSessionListener {
//public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static int numberOfSession = 0;
    private static List<String> usernameList = new ArrayList<>();
//    private static int maximosSegundosInactividad = 2100;

    private static List<HttpSession> sessionList = new ArrayList<>();
    private static List<BrowserSession> browserSessionList = new ArrayList<>();

    // <editor-fold defaultstate="collapsed" desc="get/set"> 
    public static List<BrowserSession> getBrowserSessionList() {
        return browserSessionList;
    }

    public static void setPojoSessionList(List<BrowserSession> browserSessionList) {
        SessionListener_old.browserSessionList = browserSessionList;
    }

    public static List<HttpSession> getSessionList() {
        return sessionList;
    }

    public static void setSessionList(List<HttpSession> sessionList) {
        SessionListener_old.sessionList = sessionList;
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
        SessionListener_old.numberOfSession = numberOfSession;
    }

    public static List<String> getUsernameList() {
        return usernameList;
    }

    public static void setUsernameList(List<String> usernameList) {
        SessionListener_old.usernameList = usernameList;
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
    public SessionListener_old() {
        System.out.println("call SessionListener a las " + JsfUtil.getTiempo());
        usernameList = new ArrayList<>();
        numberOfSession = 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="inicializar"> 
    public static void inicializar() {
        sessionList = new ArrayList<>();

        usernameList = new ArrayList<>();
        browserSessionList = new ArrayList<>();
        numberOfSession = 0;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sessionCreated"> 
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sessionList.add(session);
//        session.setMaxInactiveInterval(2100);
        session.setMaxInactiveInterval(180);

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
        System.out.println("......# " + numberOfSession);
        System.out.println(".......Segundos para inactividad " + session.getMaxInactiveInterval());
        System.out.println(".......id " + session.getId());
        System.out.println(".......time " + session.getAttribute("time"));
        System.out.println(".......ipcliente" + session.getAttribute("ipcliente"));
        System.out.println(".......browser" + session.getAttribute("browser"));
        System.out.println("===========================================");
        BrowserSession browserSession = new BrowserSession(session.getId(), time, JsfUtil.getIp(), JsfUtil.getBrowserName(), "", session);
        browserSessionList.add(browserSession);

        JsfUtil.successMessage("Se creo una sesion " + session.getId());

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sessionDestroyed"> 
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(5);

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

        if (session.getAttribute("username") != null) {
            String user = session.getAttribute("username").toString();
            if (removeUsernameOfUserList(user)) {
                System.out.println(user + " !!! fue encontrado en la lista voy a quitarlo");
            } else {
                System.out.println("!!!! no se quito del usersenameList");
            }

        }
        if (removeOfSessionList(se.getSession())) {
            System.out.println("!!! se removio el session de sessionList");
        } else {
            System.out.println("!!! No se removio el session de sessionList");
        }

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
// </editor-fold>
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
    }

    // <editor-fold defaultstate="collapsed" desc="complete"> 
    private Boolean removeOfSessionList(HttpSession session) {
        Boolean found = false;
        try {
            for (HttpSession s : sessionList) {
                if (s.getId().equals(session.getId())) {
                    sessionList.remove(session);
                    found = true;

                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("removeOfSessionList() " + e.getLocalizedMessage());
        }
        return found;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="complete"> 
// </editor-fold>
    private void printAll() {
        try {
            System.out.println("#######################################3");
            System.out.println("---- printAll");
            System.out.println("---- sessionList()");

            for (HttpSession session : sessionList) {
//             System.out.println("--------------------------------------------------------");
                System.out.println("........id: " + session.getAttribute("id") + "  ........username " + session.getAttribute("username"));
//        System.out.println("........id: " + session.getAttribute("id"));
//        System.out.println("........Segundos para inactividad " + session.getMaxInactiveInterval());
//        System.out.println("........ipcliente: " + session.getAttribute("ipcliente"));
//        System.out.println("........time of creation: " + session.getAttribute("time"));
//        System.out.println("........browser: " + session.getAttribute("browser"));
//        System.out.println("--------------------------------------------------------");
            }

            System.out.println("---- usernameeList()");

            for (String s : usernameList) {
                System.out.println("........username " + s);
            }
            System.out.println("============== FIN printAll()============");

        } catch (Exception e) {
            JsfUtil.errorMessage("printAll() " + e.getLocalizedMessage());
        }
    }

// <editor-fold defaultstate="collapsed" desc="isUserLoged"> 
    public static Boolean isUserLogged(String username) {
        Boolean found = false;
        try {
            if (usernameList.isEmpty()) {
                return false;
            }
            for (String s : usernameList) {
                if (s.equals(username)) {
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
    public static Boolean addUsername(String username, HttpSession session) {
        Boolean add = false;
        try {
            if (isUserLogged(username)) {
                return false;
            }
            usernameList.add(username);
            Integer c = 0;
            //Actualizo en user name
//            for (HttpSession s : sessionList) {
//                if (s.getId().equals(session.getId())) {
//                    c = 0;
//                    for (BrowserSession p : browserSessionList) {
//                        if (p.getId().equals(s.getId())) {
//                            browserSessionList.get(c).setUsername(username);
//                        }
//                        c++;
//                    }
//                }
//            }
             for (BrowserSession p : browserSessionList) {
                        if (p.getId().equals(session.getId())) {
                            browserSessionList.get(c).setUsername(username);
                        }
                        c++;
                    }
            
            

            // System.out.println("---> Agregado a la sesion" + username);
        } catch (Exception e) {
            JsfUtil.errorMessage("addUsername " + e.getLocalizedMessage());
        }
        return add;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getUsernameOnline"> 
    public static List<String> getUsernameOnline() {

        return usernameList;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="killAllSesion()">  
// <editor-fold defaultstate="collapsed" desc="killAllSesion">  

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
    }// </editor-fold>

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
    
    
    public static Boolean inactiveSession(BrowserSession browserSession){
        try {
            browserSession.session.invalidate();
            browserSessionList.remove(browserSession);
            return true;
        } catch (Exception e) {
             JsfUtil.errorMessage("inactivarSession() " + e.getLocalizedMessage());
        }
        return false;
    }
    // <editor-fold defaultstate="collapsed" desc="killSesionByUsername">  

    /**
     *
     * @param id
     * @return
     */
    public static Boolean killSesionByUsername(String username) {
        Boolean kill = false;
        try {
            System.out.println("-----------------------------------------");
            System.out.println("killSessionByUsername()");
            System.out.println("-----------------------------------------");
            if (username == null || username.equals("")) {
                JsfUtil.warningMessage("El username es null o vacio ");
                System.out.println("El username es null o vacio ");
                return false;
            }
            if (!isUsernameHaveSession(username)) {
                JsfUtil.warningMessage("El username " + username + "(No) tiene asiganda ninguna sesion");
                System.out.println("El username " + username + "(No) tiene asiganda ninguna sesion");
                return false;
            }
            HttpSession sessionKill = getSesion(username);
            if (sessionKill != null) {
                sessionList.remove(sessionKill);
                if (!removeUsernameOfUserList(username)) {
                    System.out.println("---> el username " + username + " no esta en la lista de usernameList");
                }
                //usernameList.remove(username);
                System.out.println("Elimnando sesion id() :" + sessionKill.getId());
                sessionKill.invalidate();
                kill = true;
            } else {
                System.out.println("No hay una sesion con ese username");
                JsfUtil.warningMessage("No hay una sesion con ese username");
            }

//            System.out.println("-------<voy a recorrer el sessionList>----");
//            Integer c = 0;
//            for (HttpSession s : sessionList) {
//                System.out.println("----c: " + c);
//                System.out.println("s.getId() " + s.getId());
//                System.out.println("*** Voy a verificar el s.getAttribute(\"username \")");
//                if (s.getAttribute("username") == null) {
//                    System.out.println("==== Esa sesion no tiene username asignado voy a invalidarla");
//                    JsfUtil.errorMessage(" Esa sesion no tiene username asignado voy a invalidarla");
//                    s.invalidate();
//                    return false;
//                }
//                System.out.println("***el usermame no es null");
//                if (s.getAttribute("username").equals(username)) {
//                    JsfUtil.warningMessage("===voy a eliminar la sesion " + s.getId());
//                    System.out.println(" invalidado");
//                    Boolean found = false;
//                    for (HttpSession s1 : sessionList) {
//                        System.out.println("--->comparo s.equals(s1)");
//                        if (s.equals(s1)) {
//                            System.out.println("----->es igual voy a removerlo de sessionList");
//                            sessionList.remove(s);
//                            System.out.println("-------->lo removi de sessionList");
//                            found = true;
//                            break;
//                        }
//                    }
//                    if (!found) {
//                        JsfUtil.warningMessage("no pudo sacar el Session de sessionList");
//                        System.out.println("no pudo sacar el Session de sessionList");
//                    } else {
//                        System.out.println("............ se saco de sessionList");
//                    }
//                    System.out.println("....Ahora voy a sacarlo de username");
//                    found = false;
//                    for (String u : usernameList) {
//                        if (u.equals(username)) {
//                            System.out.println("----> el u.equals(username) voy removerlo de usernameList");
//                            usernameList.remove(username);
//                            System.out.println(".........> se removiopo de usernameList");
//                            found = true;
//                            break;
//                        }
//                    }
//                    if (!found) {
//                        JsfUtil.warningMessage("No se pudo sacar el username de usernameList");
//                        System.out.println("No se pudo sacar el username de usernameList");
//                    }
//                    //----
//                    System.out.println("###### voy a ejecutar invalidate");
//                    s.invalidate();
//                    System.out.println("##### invalidate exitoso");
//                    System.out.println(" voy a asigarle true a kill");
//                    kill = true;
//                    return kill;
//                }
//            }
            return kill;
        } catch (Exception e) {
            JsfUtil.errorMessage("killSesionByUsername()" + e.getLocalizedMessage());
        }
        return kill;
    }// </editor-fold>
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
            for (HttpSession s : sessionList) {
                if (s.getAttribute("username") == null) {

                } else {
                    if (s.getAttribute("username").equals(username)) {
                        found = true;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            JsfUtil.errorMessage("usernameHaveSession()" + e.getLocalizedMessage());
        }
        return found;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getSession">  
    /**
     * devuelve el session de un username
     *
     * @param username
     * @return
     */
    private static HttpSession getSesion(String username) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        try {
            for (HttpSession s : sessionList) {
                if (s.getAttribute("username") == null) {

                } else {
                    if (s.getAttribute("username").equals(username)) {
                        session = s;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            JsfUtil.errorMessage("getSession()" + e.getLocalizedMessage());
        }
        return session;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="removeUsernameOfUserList">  
    /**
     * remueve el username de la lista
     *
     * @param username
     * @return
     */
    private static Boolean removeUsernameOfUserList(String username) {
        Boolean removed = false;
        try {
            for (String s : usernameList) {
                if (s.equals(username)) {
                    usernameList.remove(s);
                    removed = true;
                    break;
                }
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("removeUsername()" + e.getLocalizedMessage());
        }
        return removed;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="validateUsernameWithSession">  

    /**
     * Garantiza que no existan username en userlist y que no esten en
     * sessionList
     *
     * @return
     */
    public static Boolean validateUsernameWithSession() {
        System.out.println("------------------------------------");
        System.out.println("run: validateUsernameWithSession()");
        Boolean analized = false;
        List<String> usernameToDelete = new ArrayList<>();
        try {
//            Boolean found = false;
            for (String u : usernameList) {
                System.out.println("run1 : " + u);
                //        found = false;
//                for (HttpSession s : sessionList) {
//                       System.out.println("run2 : "+s);
//                    if (s != null) {
//                           System.out.println("run3: ");
//                        if (s.getAttribute("username") == null) {
//   System.out.println("run4 : ");
//                        } else {
//                            if (s.getAttribute("usermame").equals(u)) {
//                                found = true;
//                                System.out.println("run5 : ");
//                                break;
//                            }
//                        }
//                    }
//
//                }

//                if (!found) {
//                    usernameToDelete.add(u);
//                }
                if (!isUsernameHaveSession(u)) {
                    System.out.println("run2: agregar a usernameToDelete");
                    usernameToDelete.add(u);
                } else {
                    System.out.println("run3: " + u + " Fue encontrado en la lista de sesiones");
                }
            }
            //elimino los username que no existen
            for (String u : usernameToDelete) {
                System.out.println("run4: -----------> Username a quitar: " + u);
                usernameList.remove(u);
                System.out.println("Run5: quitado");
            }
            analized = true;
        } catch (Exception e) {
            JsfUtil.errorMessage("validateUsernameWithSession()" + e.getLocalizedMessage());
        }
        return analized;
    }
}// </editor-fold>
