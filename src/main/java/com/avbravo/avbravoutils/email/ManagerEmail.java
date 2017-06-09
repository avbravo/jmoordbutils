/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils.email;

import com.avbravo.avbravoutils.JsfUtil;
import javax.inject.Named;
import java.util.Properties;
import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author avbravo
 */

public class ManagerEmail {

    /**
     * Creates a new instance of EnviarEmail
     */
    public ManagerEmail() {
    }
// <editor-fold defaultstate="collapsed" desc="nenviar"> 

    public String enviar() {
        try {

            final String username = "myemail@gmail.com";
            final String password = "azuero2015nasa";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("avbravo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("avbravo@gmail.com"));
            message.setSubject("Alert");
            message.setText("Existe una plaga,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (Exception ex) {
            JsfUtil.infoDialog("error", ex.getLocalizedMessage());
            System.out.println("error " + ex.getLocalizedMessage());
        }
        return null;
    }// </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="send"> 
/**
 * 
 * @param emaildestinatario
 * @param titulo
 * @param mensaje
 * @param emailremitente
 * @param passwordremitente
 * @return 
 */
    public Boolean send(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente) {
        Boolean sending=false;
        try {

//            final String username = "avbravo@gmail.com";
//            final String password = "javnet180denver$";
            final String username = emailremitente;
            final String password = passwordremitente;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");


            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emaildestinatario));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
            message.setText(mensaje);

            Transport.send(message);
sending=true;
        } catch (Exception ex) {
            JsfUtil.errorMessage("send() "+ ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="send"> 

    /**
     * 
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @param props
     * @return 
     */
    public Boolean send(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente,Properties props) {
        Boolean sending=false;
        try {

//            final String username = "avbravo@gmail.com";
//            final String password = "javnet180denver$";
            final String username = emailremitente;
            final String password = passwordremitente;
           

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emaildestinatario));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
            message.setText(mensaje);

            Transport.send(message);
sending=true;
        } catch (Exception ex) {
            JsfUtil.errorMessage("send() "+ ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="send"> 

    /**
     * 
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @param props
     * @return 
     */
    public Boolean send(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente,EmailSegurityProperties emailSegurityProperties) {
        Boolean sending=false;
        try {

//            final String username = "avbravo@gmail.com";
//            final String password = "javnet180denver$";
            final String username = emailremitente;
            final String password = passwordremitente;
           
  Properties props = new Properties();
            props.put("mail.smtp.auth", emailSegurityProperties.getMailSmtpAuth());
            props.put("mail.smtp.starttls.enable", emailSegurityProperties.getMailSmtpStarttlsEnable());
            props.put("mail.smtp.host", emailSegurityProperties.getMailSmtpHost());
            props.put("mail.smtp.port", emailSegurityProperties.getMailSmtpPort());
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emaildestinatario));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
            message.setText(mensaje);

            Transport.send(message);
sending=true;
        } catch (Exception ex) {
            JsfUtil.errorMessage("send() "+ ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>
   
}
