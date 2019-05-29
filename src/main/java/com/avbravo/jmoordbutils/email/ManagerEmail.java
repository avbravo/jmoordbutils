/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.email;

import com.avbravo.jmoordbutils.JsfUtil;
import java.util.Date;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
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
// <editor-fold defaultstate="collapsed" desc="enviar"> 

    public String enviar() {
        try {
//username: info@centraldemotores.com
//password:CM2017chitre@
//smtp: smtpout.secureserver.net
//port:587
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
    
    
    // <editor-fold defaultstate="collapsed" desc=" send(String emaildestinatario, String titulo, String mensaje,    String emailremitente, String passwordremitente)"> 
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
            message.setFrom(new InternetAddress(emailremitente));
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
    
    // <editor-fold defaultstate="collapsed" desc="Boolean sendOutlook(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente)"> 
/**
 * 
 * @param emaildestinatario
 * @param titulo
 * @param mensaje
 * @param emailremitente
 * @param passwordremitente
 * @return 
 */
    public Boolean sendOutlook(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente) {
        Boolean sending=false;
        try {

//            final String username = "avbravo@gmail.com";
//            final String password = "javnet180denver$";
            final String username = emailremitente;
            final String password = passwordremitente;

//        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");


            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
            message.setText(mensaje);
             message.setSentDate(new Date());

            Transport.send(message);
sending=true;
        } catch (Exception ex) {
            JsfUtil.errorMessage("send() "+ ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Boolean send(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente,Properties props) "> 

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


            final String username = emailremitente;
            final String password = passwordremitente;
           

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
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
   
    // <editor-fold defaultstate="collapsed" desc=" Boolean send(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente,EmailSegurityProperties emailSegurityProperties)"> 

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
            message.setFrom(new InternetAddress(emailremitente));
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
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Boolean getOutlook( String mtemail, String mypassword)"> 
    public Boolean getOutlook( String myemail, String mypassword) {
        Boolean sending=false;
        try {

//            final String username = "avbravo@gmail.com";
//            final String password = "javnet180denver$";
            final String username = myemail;
            final String password = mypassword;

//        
      
         Properties props = new Properties();
        props.put("mail.host", "outlook.office365.com");
        props.put("mail.store.protocol", "pop3s");
        props.put("mail.pop3s.auth", "true");
        props.put("mail.pop3s.port", "995");


            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Store store = session.getStore("pop3s");
        store.connect();
        Folder emailFolder = store.getFolder("INBOX");

        emailFolder.open(Folder.READ_ONLY);

        // retrieve the messages from the folder in an array and print it
        Message[] messages = emailFolder.getMessages();
        System.out.println("messages.length---" + messages.length);

        for (int i = 0, n = messages.length; i < n; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Fecha: " +message.getSentDate());
            System.out.println("Content: " +message.getContent());
        }

        //close the store and folder objects
        emailFolder.close(false);
        store.close();
sending=true;
        } catch (Exception ex) {
            JsfUtil.errorMessage("send() "+ ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }
   // </editor-fold>
}
