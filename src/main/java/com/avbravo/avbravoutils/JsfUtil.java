/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils;
// <editor-fold defaultstate="collapsed" desc="import">  

import com.avbravo.avbravoutils.crypto.CryptoConverter;
import com.avbravo.avbravoutils.crypto.Encriptador;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
    // </editor-fold>
/**
 *
 * @authoravbravo
 */
public class JsfUtil implements Serializable {

    private static final Logger LOG = Logger.getLogger(JsfUtil.class.getName());
// <editor-fold defaultstate="collapsed" desc="getSelectItems"> 
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "­­­");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="logout"> 
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/index";
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorMessage"> 
    public static void errorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
           errorMessage(msg);
        } else {
            errorMessage(defaultMsg);
        }
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorMessages"> 
    public static void errorMessages(List<String> messages) {
        for (String message : messages) {
            errorMessage(message);

        }
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorMessage(String msg)"> 
    public static void errorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        LOG.warning(msg);
    }    // </editor-fold>

    public static void testMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        LOG.warning(msg);
    }    // </editor-fold>

    public static void successMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
                msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }    // </editor-fold>

    public static void warningMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
        LOG.warning(msg);
    }    // </editor-fold>

    public static void fatalMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, ""));
        LOG.warning(msg);
    }    // </editor-fold>

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }    // </editor-fold>

    public static Object getObjectFromRequestParameter(String requestParameterName,
            Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }    // </editor-fold>

    public static void infoDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }    // </editor-fold>

    public static void warningDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }    // </editor-fold>

    public static void fatalDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }    // </editor-fold>

    public static void errorDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                titulo, texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }    // </editor-fold>

    public static java.sql.Date converterDate(java.util.Date fecha) {
        try {
            long lfecha = fecha.getTime();
            java.sql.Date dtfecha = new java.sql.Date(lfecha);
            return dtfecha;
        } catch (Exception e) {
            errorMessage("converterDate() " + e.getLocalizedMessage());
        }
        return null;
    }    // </editor-fold>

    public static boolean isDummySelectItem(UIComponent component, String value) {
        for (UIComponent children : component.getChildren()) {
            if (children instanceof UISelectItem) {
                UISelectItem item = (UISelectItem) children;
                if (item.getItemValue() == null && item.getItemLabel().equals(value)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }    // </editor-fold>

    public static String getComponentMessages(String clientComponent, String defaultMessage) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(fc).findComponent(clientComponent);
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            if (inputComponent.isValid()) {
                return defaultMessage;
            } else {
                Iterator<FacesMessage> iter = fc.getMessages(inputComponent.getClientId());
                if (iter.hasNext()) {
                    return iter.next().getDetail();
                }
            }
        }
        return "";
    }    // </editor-fold>

    public static Throwable getRootCause(Throwable cause) {
        if (cause != null) {
            Throwable source = cause.getCause();
            if (source != null) {
                return getRootCause(source);
            } else {
                return cause;
            }
        }
        return null;
    }    // </editor-fold>

    public static String md5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());
        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        //algoritmo y arreglo md5
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        //clave encriptada
        return h.toString();
    }    // </editor-fold>

    /**
     * genera id
     *
     * @return returna un randomUUID automatico
     */
    public static String getUUID() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString().toLowerCase();

    }

    /**
     * getExtension()
     *
     * @param texto
     * @return la extension de un nombre de archivo
     */
    public static String getExtension(String texto) {
        try {
            return texto.substring(texto.indexOf("."), texto.length());
        } catch (Exception e) {
            JsfUtil.errorMessage("getExtension() " + e.getLocalizedMessage());
        }
        return "";
    }

    /*
/*
     copia un archivo generalmente cuando se usa el fileupload
     fileName: nombre del archivo a copiar
     in: Es el InputStream
     destination: ruta donde se guardara el archivo

     */
    public static Boolean copyFile(String fileName, InputStream in, String destination) {
        try {

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            return true;
        } catch (IOException e) {
            JsfUtil.errorMessage("copyFile() " + e.getLocalizedMessage());
        }
        return false;
    }

    public static String getPathFotos() {
        try {

            String path = getPath() + "resources/fotos/";
            return path;
        } catch (Exception e) {

            errorMessage("getPathFotosPlagas() " + e.getLocalizedMessage());
        }
        return null;

    }

    /*
     devuelve el path 
     */
    public static String getPath() {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            return ctx.getRealPath("/");

        } catch (Exception e) {

            errorMessage("getPath() " + e.getLocalizedMessage());
        }
        return null;

    }

    /**
     *
     * @param folder forExample: getPathResources("/fotos/paises/");
     * @return getPath() + "resources"+folder
     *
     */
    public static String getAddPathResources(String folder) {

        try {

            String path = getPath() + "resources" + folder;
            return path;
        } catch (Exception e) {

            errorMessage("getAddPathResources() " + e.getLocalizedMessage());
        }
        return null;

    }

    public static Double redondear(Double n, Integer decimales) {
        Double r = 0.0;
        try {
            switch (decimales) {
                case 1:
                    r = (double) Math.round(n * 10) / 10;

                    break;

                case 2:
                    r = (double) Math.round(n * 100) / 100;
                    break;
                case 3:
                    r = (double) Math.round(n * 1000) / 1000;
                    break;
                case 4:
                    r = (double) Math.round(n * 10000) / 10000;
                    break;
            }

            return r;
        } catch (Exception e) {
            errorMessage("redondear() " + e.getLocalizedMessage());
        }
        return r;
    }

    /**
     * Genera una serie de caracteres aleatorios
     *
     * @return
     */
    public static String generateUniqueID() {
        String strValue = "";
        UUID idUnique = UUID.randomUUID();
        strValue = idUnique.toString();
        return strValue.toUpperCase();
    }

    public static Double round(Double value) {

        if (value != null) {
            System.out.println("value: " + value);
            System.out.println("value to String: " + value.toString());
            String[] splitter = value.toString().split("\\.");
            System.out.println("decimals: " + splitter[1].length());
            if (splitter[1].length() <= 2) {
                return value;
            } else {
                return (long) (value * 1e2) / 1e2;
            }

        } else {
            return 1d;
        }
    }

    public static java.util.Date getFechaActual() {
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        java.util.Date date = java.sql.Date.valueOf(currentDate);
        return date;
    }

//  public static java.util.Date getFechaActual() {
//        java.util.Calendar ca = java.util.Calendar.getInstance();
//        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
//        return new java.sql.Date(mydate.getTime());
//
//    }
    public static Integer getAnioActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.YEAR);
    }

    public static Integer getMesActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.MONTH);
    }

    public static Integer getMesDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return mes;
    }

    public static Integer getAnioDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return anio;
    }

    public static Integer getDiaDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return dia;
    }

    public static Integer getDiaActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.DATE);
    }

    /**
     * devuelve la primera fecha del año
     *
     * @return
     */
    public static Date getPrimeraFechaAnio() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer year = now.getYear();
        Integer month = 1;
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);

        Date date = java.sql.Date.valueOf(firstDay);
        return date;

    }

    /**
     * devuelve la ultima fecha del año
     *
     * @return
     */
    public static Date getUltimaFechaAnio() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer year = now.getYear();
        Integer month = 12;
        Integer day = 31;
        LocalDate firstDay = LocalDate.of(year, month, day);

        Date date = java.sql.Date.valueOf(firstDay);
        return date;

    }

    /**
     *
     * @param month
     * @return devuelve una fecha correspondiente al primer dia de ese mes
     */
    public static Date getDateFirtsOfMonth(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }

    public static Date getDateLastOfMonth(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }

    public static String getISODate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static LocalTime getTiempo() {
        LocalTime now = LocalTime.now();

        return now;

    }

    public static String printTiempo() {
        LocalTime now = LocalTime.now();
        String tiempo = "";

        tiempo = "En este momento son las %d horas con %d minutos y %d segundos\n" + now.getHour()
                + now.getMinute() + now.getSecond();

        return tiempo;

    }

    public String letterToUpper(String texto) {
        try {

            texto = texto.trim();
            int largo = texto.length();
            if (largo <= 0) {
                return texto;
            }
            String letra = texto.substring(0, 1);

            texto = letra.toUpperCase() + texto.substring(1);
        } catch (Exception ex) {
            System.out.println("letterToUpper() " + ex.getLocalizedMessage());
        }
        return texto;
    }

    /**
     * ConvertirLetraMinuscula
     *
     * @param s_cadena
     * @param caracter
     * @return
     */
    public String letterToLower(String texto) {

        try {

            texto = texto.trim();
            int largo = texto.length();
            if (largo <= 0) {
                return texto;
            }
            String letra = texto.substring(0, 1);

            texto = letra.toLowerCase() + texto.substring(1);
        } catch (Exception ex) {
            System.out.println("letterToLower() " + ex.getLocalizedMessage());
        }
        return texto;
    }

    public static LocalTime getHour() {

        return LocalTime.now();
    }

    public static Integer diasEntreFechas(Date fechaMayor, Date fechaMenor) {
        int d = 0;
        try {
            long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();

            long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
            d = (int) dias;
        } catch (Exception e) {
            System.out.println("diasEntreFechas() " + e.getLocalizedMessage());
        }

        return d;
    }

    /*
    
     */
    public static Date sumarMesaFechaActual(Integer mes) {
        java.util.Date date = new Date();
        try {
            LocalDate localDate = LocalDate.now().plusMonths(mes);
            date = java.sql.Date.valueOf(localDate);

        } catch (Exception e) {
            System.out.println("diasEntreFechas() " + e.getLocalizedMessage());
        }

        return date;
    }

    public static long getMilisegundos() {
        long milisegundos = 0;
        try {
            milisegundos = System.nanoTime();

        } catch (Exception e) {
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return milisegundos;
    }

    public static long getMilisegundosTranscurridos(long t0, long t1) {
        long milisegundos = 0;
        try {
            milisegundos = TimeUnit.NANOSECONDS.toMillis(t1 - t0);

        } catch (Exception e) {
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return milisegundos;
    }

    public static Boolean esImpar(int iNumero) {
        if (iNumero % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="getIp">  

    /**
     * Devuelve el IP
     *
     * @return
     */
    public static String getIp() {
        String myip = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();

            }
            myip = ipAddress;
        } catch (Exception e) {
            errorMessage("getIp() " + e.getLocalizedMessage());
        }
        return myip;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="encriptar">  
    /**
     * Encripta un texto en base a una llave
     *
     * @param texto: myclavepersonal
     * @param key : mykey
     * @return
     */
    public static String encritpar(String texto) {

        try {
            CryptoConverter cryptoConverter = new CryptoConverter();

           return cryptoConverter.convertToDatabaseColumn(texto);
        } catch (Exception e) {
            errorMessage("encriptar() " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="desencritpar">  

    /**
     * Encripta un texto en base a una llave
     *
     * @param textoencriptado
     * @param texto: myclavepersonal
     * @param key : mykey
     * @return
     */
    public static String desencritpar(String textoencriptado) {
        try {
           CryptoConverter cryptoConverter = new CryptoConverter();

          return cryptoConverter.convertToEntityAttribute(textoencriptado);
        } catch (Exception e) {
            errorMessage("desencriptar() " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>
 
     // <editor-fold defaultstate="collapsed" desc="getSession">  
    /**
     * devuelve Un objeto Session correspondiente a la sesion local
     * @return 
     */
    public static HttpSession getSession(){
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession();
            return session;
    }// </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="getSession">  
    /**
     * devuelve Un objeto Session correspondiente a la sesion local
     * @return 
     */
    public static Boolean addParametersUserNameToSession(String username){
        Boolean add=false;
        try {
           HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession();
             session.setAttribute("username", username);
             add=true;
        } catch (Exception e) {
                 errorMessage("addParametersUserNameToSession() " + e.getLocalizedMessage());
        }
            
            return add;
    }// </editor-fold>
    
    
    
    
}
