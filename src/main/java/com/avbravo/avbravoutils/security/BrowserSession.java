/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils.security;

import java.time.LocalTime;
import javax.servlet.http.HttpSession;

/**
 *
 * @author avbravo
 */
public class BrowserSession {
    private String id;
        private LocalTime time; 
        private String ipcliente;
        private String browser;
        private String username;
        HttpSession session;

    public BrowserSession() {
    }

    public BrowserSession(String id, LocalTime time, String ipcliente, String browser, String username, HttpSession session) {
        this.id = id;
        this.time = time;
        this.ipcliente = ipcliente;
        this.browser = browser;
        this.username = username;
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getIpcliente() {
        return ipcliente;
    }

    public void setIpcliente(String ipcliente) {
        this.ipcliente = ipcliente;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
        
        
}
