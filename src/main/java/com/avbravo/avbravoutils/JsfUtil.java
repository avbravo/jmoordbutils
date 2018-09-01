/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoutils;
// <editor-fold defaultstate="collapsed" desc="import">  

import com.avbravo.avbravoutils.crypto.CryptoConverter;
import com.avbravo.avbravoutils.dates.FechaDiaUtils;
import com.avbravo.avbravoutils.dates.MonthUtils;
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
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JasperCompileManager;
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

    // <editor-fold defaultstate="collapsed" desc="testMessage"> 
    public static void testMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        LOG.warning(msg);
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="successMessage"> 
    public static void successMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
                msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="warningMessage"> 

    public static void warningMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
        LOG.warning(msg);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="fatalMessage"> 

    public static void fatalMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, ""));
        LOG.warning(msg);
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getRequestParameter"> 
    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getObjectFromRequestParameter"> 
    public static Object getObjectFromRequestParameter(String requestParameterName,
            Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="infoDialog"> 
    public static void infoDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="warningDialog"> 
    public static void warningDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="fatalDialog"> 

    public static void fatalDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorDialog"> 

    public static void errorDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                titulo, texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="converterDate"> 

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
// <editor-fold defaultstate="collapsed" desc="isDummySelectItem"> 

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

// <editor-fold defaultstate="collapsed" desc="getComponentMessages"> 
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
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getRootCause"> 
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
    }
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="md5"> 

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

    // <editor-fold defaultstate="collapsed" desc="getUUID"> 
    /**
     * genera id
     *
     * @return returna un randomUUID automatico
     */
    public static String getUUID() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getUUIDMinusculas()"> 

    /**
     * genera id
     *
     * @return returna un randomUUID automatico
     */
    public static String getUUIDMinusculas() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString().toLowerCase();

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getUUIDMinusculas()"> 

    /**
     * genera id
     *
     * @return returna un randomUUID automatico
     */
    public static String getUUIDMayusculas() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getExtension"> 
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
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="copyFile">
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
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getPathFotos">
    public static String getPathFotos() {
        try {

            String path = getPath() + "resources/fotos/";
            return path;
        } catch (Exception e) {

            errorMessage("getPathFotosPlagas() " + e.getLocalizedMessage());
        }
        return null;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="getPath"> 

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

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getAddPathResources"> 
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

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="redondear">
    public static Double redondear(Double n, Integer decimales) {
        Double r = 0.0;
        try {
            switch (decimales) {
                case 0:
                     r =Math.floor(n);
                     break;
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
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="redondear">
    public static Double redondearEnteroSuperior(Double n) {
        Double r = 0.0;
        try {
           
           
            return Math.ceil(n);
        } catch (Exception e) {
            errorMessage("redondearEnteroSuperior() " + e.getLocalizedMessage());
        }
        return r;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="redondear">
    public static Double redondearEnteroInferior(Double n) {
        Double r = 0.0;
        try {
           
           
            return Math.floor(n);
        } catch (Exception e) {
            errorMessage("redondearEnteroInferior() " + e.getLocalizedMessage());
        }
        return r;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="redondear">
    public static Long redondearMasCercano(Double n) {
        Double r = 0.0;
        try {
           
           
            return Math.round(n);
        } catch (Exception e) {
            errorMessage("redondearEnteroInferior() " + e.getLocalizedMessage());
        }
        return r.longValue();
    }// </editor-fold>
    
    
    
    

    // <editor-fold defaultstate="collapsed" desc="generateUniqueID">
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
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="round"> 

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
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getFechaActual"> 

    public static java.util.Date getFechaActual() {
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        java.util.Date date = java.sql.Date.valueOf(currentDate);
        return date;
    }    // </editor-fold>

//  public static java.util.Date getFechaActual() {
//        java.util.Calendar ca = java.util.Calendar.getInstance();
//        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
//        return new java.sql.Date(mydate.getTime());
//
//    }
    // <editor-fold defaultstate="collapsed" desc="numberDayOfMonth"> 
    public static Integer numberDayOfMonth(Integer anio, String mes) {
        Integer dias = 0;
        try {
            MonthUtils m = new MonthUtils();

            dias = numberDayOfMonth(anio, m.numeroMes(mes));
        } catch (Exception e) {
            errorMessage("numberDayOfMonth() " + e.getLocalizedMessage());
        }
        return dias;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="numberDayOfMonth"> 

    public static Integer numberDayOfMonth(Integer anio, Integer mes) {
        Integer dias = 0;
        try {
            switch (mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dias = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dias = 30;
                    break;
                case 2:
                    if ((anio % 4 == 0 && dias % 100 != 0) || anio % 400 == 0) {
                        dias = 29;
                    } else {
                        dias = 28;
                    }
                    break;
                default:
                    System.out.println("\nEl mes " + mes + " es incorrecto.");
                    break;
            }

        } catch (Exception e) {
            errorMessage("numberDayOfMonth() " + e.getLocalizedMessage());
        }
        return dias;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getAnioActual"> 

    public static Integer getAnioActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.YEAR);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getMesActual"> 
    public static Integer getMesActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.MONTH);
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getFechaHoraActual()"> 
    public static Date getFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
        return date2;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="numberOfMonth"> 
    public static Integer convertMonthNameToNumber(String mes) {
        Integer number = 0;
        try {
            MonthUtils m = new MonthUtils();

            number = m.numeroMes(mes);
        } catch (Exception e) {
        }
        return number;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="dateFormatToString"> 

    public static String dateFormatToString(Date fecha, String format) {
        String dateformat = "";
        try {
            //     SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateformat = sdf.format(fecha);
        } catch (Exception e) {
        }
        return dateformat;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="metodo"> 
    /**
     * busca una fecha si esta entre fechas
     *
     * @param fechaToSearch
     * @param fechainicio
     * @param fechafin
     * @return
     */
    public static Boolean dateBetween(Date fechaToSearch, Date fechainicio, Date fechafin) {
        try {
//            Date fechainiciot = converterDate(fechainicio);
//                    Date fechafint = converterDate(fechafin);
            if (fechaToSearch.equals(fechainicio) || fechaToSearch.equals(fechafin) || (fechaToSearch.after(fechainicio) && fechaToSearch.before(fechafin))) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getNombreMes"> 
    public static String getNombreMes(Integer numeromes) {
        try {
            String nombre = "";
            List<String> listMeses = new ArrayList<>();
            listMeses.add("Enero");
            listMeses.add("Febrero");
            listMeses.add("Marzo");
            listMeses.add("Abril");
            listMeses.add("Mayo");
            listMeses.add("Junio");
            listMeses.add("Julio");
            listMeses.add("Agosto");
            listMeses.add("Septiembre");
            listMeses.add("Octubre");
            listMeses.add("Noviembre");
            listMeses.add("Diciembre");
            return listMeses.get(numeromes);

        } catch (Exception e) {
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mesToMonth"> 
    /**
     * Convierte un nombre de mes a un objeto Month Month month =
     * JsfUtil.mesToMonth("Febrero); Devuelve un month.FEBRARY;
     *
     * @param mes
     * @return
     */
    public static Month mesToMonth(String mes) {
        mes = mes.toLowerCase();
        Month month = Month.JANUARY;
        try {
            switch (mes) {
                case "enero":
                    month = Month.JANUARY;
                    break;
                case "febrero":
                    month = Month.FEBRUARY;
                    break;
                case "marzo":
                    month = Month.MARCH;
                    break;
                case "abril":
                    month = Month.APRIL;
                    break;
                case "mayo":
                    month = Month.MAY;
                    break;
                case "junio":
                    month = Month.JUNE;
                    break;
                case "julio":
                    month = Month.JULY;
                    break;
                case "agosto":
                    month = Month.AUGUST;
                    break;
                case "septiembre":
                    month = Month.SEPTEMBER;
                    break;
                case "octubre":
                    month = Month.OCTOBER;
                    break;
                case "noviembre":
                    month = Month.NOVEMBER;
                    break;
                case "diciembre":
                    month = Month.DECEMBER;
                    break;

            }

        } catch (Exception e) {
            errorMessage("mesToMonth() " + e.getLocalizedMessage());
        }
        return month;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getMesDeUnaFecha"> 
    public static Integer getMesDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return mes;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getAnioDeUnaFecha"> 

    public static Integer getAnioDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return anio;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getDiaDeUnaFecha"> 
    public static Integer getDiaDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return dia;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getDiaActual"> 
    public static Integer getDiaActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.DATE);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="nombreDia"> 
    public static String nameOfDay(LocalDate date) {
        String nombre = "DOMINGO";
        try {
            DayOfWeek dia = date.getDayOfWeek();
            dia.name();
            switch (dia.name()) {
                case "SATURDAY":
                    nombre = "SABADO";
                    break;
                case "SUNDAY":
                    nombre = "DOMINGO";
                    break;
                case "MONDAY":
                    nombre = "LUNES";
                    break;
                case "TUESDAY":
                    nombre = "MARTES";
                    break;
                case "WEDNESDAY":
                    nombre = "MIERCOLES";
                    break;
                case "THURSDAY":
                    nombre = "JUEVES";
                    break;
                case "FRIDAY":
                    nombre = "VIERNES";
                    break;

            }

        } catch (Exception e) {
            errorMessage("nameOfDay() " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="firstLeterOfDay"> 
    /**
     * devuelve la primera letra del dia
     *
     * @param date
     * @return
     */
    public static String firstLetterOfDay(LocalDate date) {
        String letra = "";
        try {
            letra = nameOfDay(date);
            if (letra.length() > 1) {
                letra = letra.substring(0, 1);
            }

        } catch (Exception e) {
            errorMessage("firsLetterOfDay() " + e.getLocalizedMessage());
        }
        return letra;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="letterDayOfMonth"> 
    /**
     * devuelve un List<String> correspondiente a las letras de cada dia del mes
     *
     * @param year
     * @param mes
     * @return
     */
    public static List<String> letterDayOfMonth(Integer year, String mes) {
        List<String> letters = new ArrayList<>();
        try {
            LocalDate date;

            Month month = mesToMonth(mes);

            for (int i = 1; i <= month.maxLength(); i++) {

                date = LocalDate.of(year, month, i);
                String letra = firstLetterOfDay(date);
                letters.add(letra);

            }
        } catch (Exception e) {
            errorMessage("letterDayOfMonth() " + e.getLocalizedMessage());
        }
        return letters;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameDayOfMonth"> 

    /**
     * devuelve un List<String> correspondiente a las letras de cada dia del mes
     *
     * @param year
     * @param mes
     * @return
     */
    public static List<String> nameDayOfMonth(Integer year, String mes) {
        List<String> names = new ArrayList<>();
        try {
            LocalDate date;

            Month month = mesToMonth(mes);

            for (int i = 1; i <= month.maxLength(); i++) {

                date = LocalDate.of(year, month, i);
                String name = nameOfDay(date);
                names.add(name);

            }
        } catch (Exception e) {
            errorMessage("nameDayOfMonth() " + e.getLocalizedMessage());
        }
        return names;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameDayOfMonth"> 

    /**
     * devuelve un List<String> correspondiente a las letras de cada dia del mes
     *
     * @param year
     * @param mes
     * @return
     */
    public static List<FechaDiaUtils> nameOfDayOfDateOfMonth(Integer year, String mes) {
        List<FechaDiaUtils> fechaDiaUtilsList = new ArrayList<>();
        try {
            LocalDate date;

            Month month = mesToMonth(mes);
            Integer numeroDias = numberDayOfMonth(year, mes);
            //for (int i = 1; i <= month.maxLength(); i++) {
            for (int i = 1; i <= numeroDias; i++) {

                date = LocalDate.of(year, month, i);
                String name = nameOfDay(date);
                String letter = firstLetterOfDay(date);
                FechaDiaUtils fechaDiaUtils = new FechaDiaUtils(date, letter, name);
                fechaDiaUtilsList.add(fechaDiaUtils);

            }
        } catch (Exception e) {
            errorMessage("nameOfDayOfDateOfMonth() " + e.getLocalizedMessage());
        }
        return fechaDiaUtilsList;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getPrimeraFechaAnio"> 
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
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getUltimaFechaAnio"> 
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
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getDateFirtsOfMonth"> 

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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="integerToDate"> 
    public static Date integerToDate(Integer year, Integer month, Integer day) {

        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getDateLastOfMonth"> 

    public static Date getDateLastOfMonth(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="getISODate"> 
    public static String getISODate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="getTiempo"> 
    public static LocalTime getTiempo() {
        LocalTime now = LocalTime.now();

        return now;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="printTiempo"> 
    public static String printTiempo() {
        LocalTime now = LocalTime.now();
        String tiempo = "";

        tiempo = "En este momento son las %d horas con %d minutos y %d segundos\n" + now.getHour()
                + now.getMinute() + now.getSecond();

        return tiempo;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="letterToUpper"> 
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
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="letterToLower"> 
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
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getHour"> 

    public static LocalTime getHour() {

        return LocalTime.now();
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="diasEntreFechas"> 

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
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="complete"> 
    public static Long fechaActualEnMilisegundos() {
        return ZonedDateTime.now().toInstant().toEpochMilli();
    }

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="sumarMesaFechaActual"> 
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
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sumarMesaFecha(date,mes)"> 
    /**
     * suma a la fecha el numero de mes
     *
     * @param date
     * @param mes
     */
    public static Date sumarMesaFecha(Date date, Integer mes) {
        java.util.Date dateresult = new Date();
        try {
            System.out.println("llego a changeFecha()");
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = date.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

            localDate = localDate.plusMonths(3);
            dateresult = java.sql.Date.valueOf(localDate);

        } catch (Exception e) {
            errorMessage("sumarMesaFecha() " + e.getLocalizedMessage());
        }
        return dateresult;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="segundosToHoraString"> 
    public static String segundosToHoraString(Integer segundos) {
        String resultado = "";
        try {
            int hours = segundos / 3600;
            int minutes = (segundos % 3600) / 60;
            segundos = segundos % 60;
            resultado = twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(segundos);
        } catch (Exception e) {
            errorMessage("segundosToHoraString() " + e.getLocalizedMessage());
        }
        return resultado;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="twoDigitString"> 
    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getMilisegundos"> 

    public static long getMilisegundos() {
        long milisegundos = 0;
        try {
            milisegundos = System.nanoTime();

        } catch (Exception e) {
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return milisegundos;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getMilisegundosTranscurridos"> 

    public static long getMilisegundosTranscurridos(long t0, long t1) {
        long milisegundos = 0;
        try {
            milisegundos = TimeUnit.NANOSECONDS.toMillis(t1 - t0);

        } catch (Exception e) {
            errorMessage("getMilisegundos() " + e.getLocalizedMessage());
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return milisegundos;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="milisegundosToSegundos"> 
    public static Integer milisegundosToSegundos(long milisegundos) {
        Integer seconds = 0;
        try {
            seconds = (int) (milisegundos / 1000) % 60;
        } catch (Exception e) {
            errorMessage("miliseguntosToSegundos() " + e.getLocalizedMessage());
        }
        return seconds;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="milisegundosToMinutos"> 
    public static Integer milisegundosToMinutos(long milisegundos) {
        Integer minutes = 0;
        try {
            minutes = (int) ((milisegundos / (1000 * 60)) % 60);
        } catch (Exception e) {
            errorMessage("miliseguntosToMinutos() " + e.getLocalizedMessage());
        }
        return minutes;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="milisegundosToHoras"> 
    public static Integer milisegundosToHoras(long milisegundos) {
        Integer hours = 0;
        try {
            hours = (int) ((milisegundos / (1000 * 60 * 60)) % 24);
        } catch (Exception e) {
            errorMessage("miliseguntosToMinutos() " + e.getLocalizedMessage());
        }
        return hours;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="milisegundosToTiempoString"> 
    /**
     * devuelve el tiempo de los milisegundos en el formato hh:mm:ss
     * milisegundos 1222 devuelve; 1:2:23
     *
     * @param milisegundos
     * @return
     */
    public static String milisegundosToTiempoString(long milisegundos) {
        String tiempoString = "";

        try {

            tiempoString = milisegundosToHoras(milisegundos) + " : "
                    + milisegundosToMinutos(milisegundos) + " : " + milisegundosToSegundos(milisegundos);

        } catch (Exception e) {
            errorMessage("milisegundosToTiempoString() " + e.getLocalizedMessage());
        }
        return tiempoString;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="esImpar"> 
    public static Boolean esImpar(int iNumero) {
        if (iNumero % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }// </editor-fold>
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
    public static String encriptar(String texto) {

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
    public static String desencriptar(String textoencriptado) {
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
     *
     * @return
     */
    public static HttpSession getSession() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return session;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getSession">  
    /**
     * devuelve Un objeto Session correspondiente a la sesion local
     *
     * @return
     */
//    public static Boolean addParametersUserNameToSession(String username) {
//        Boolean add = false;
//        try {
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            HttpSession session = request.getSession();
//            session.setAttribute("username", username);
//            add = true;
//        } catch (Exception e) {
//            errorMessage("addParametersUserNameToSession() " + e.getLocalizedMessage());
//        }
//        
//        return add;
//    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getBrowserName()">  
    public static String getBrowserName() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");

        if (userAgent.contains("MSIE")) {
            return "Internet Explorer";
        }
        if (userAgent.contains("Firefox")) {
            return "Firefox";
        }
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        }
        if (userAgent.contains("Opera")) {
            return "Opera";
        }
        if (userAgent.contains("Safari")) {
            return "Safari";
        }
        return "Unknown";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getListaNombresPaises"> 
    public static List<String> getListaNombresPaises() {
        List<String> listPaises = new ArrayList<>();
        try {

            listPaises.add("Afganistán");
            listPaises.add("Albania");
            listPaises.add("Alemania");
            listPaises.add("Algeria");
            listPaises.add("Andorra");
            listPaises.add("Angola");
            listPaises.add("Anguila");
            listPaises.add("Antártida");
            listPaises.add("Antigua y Barbuda");
            listPaises.add("Antillas Neerlandesas");
            listPaises.add("Arabia Saudita");
            listPaises.add("Argentina");
            listPaises.add("Armenia");
            listPaises.add("Aruba");
            listPaises.add("Australia");
            listPaises.add("Austria");
            listPaises.add("Azerbayán");
            listPaises.add("Bélgica");
            listPaises.add("Bahamas");
            listPaises.add("Bahrein");
            listPaises.add("Bangladesh");
            listPaises.add("Barbados");
            listPaises.add("Belice");
            listPaises.add("Benín");
            listPaises.add("Bhután");
            listPaises.add("Bielorrusia");
            listPaises.add("Birmania");
            listPaises.add("Bolivia");
            listPaises.add("Bosnia y Herzegovina");
            listPaises.add("Botsuana");
            listPaises.add("Brasil");
            listPaises.add("Brunéi");
            listPaises.add("Bulgaria");
            listPaises.add("Burkina Faso");
            listPaises.add("Burundi");
            listPaises.add("Cabo Verde");
            listPaises.add("Camboya");
            listPaises.add("Camerún");
            listPaises.add("Canadá");
            listPaises.add("Chad");
            listPaises.add("Chile");
            listPaises.add("China");
            listPaises.add("Chipre");
            listPaises.add("Ciudad del Vaticano");
            listPaises.add("Colombia");
            listPaises.add("Comoras");
            listPaises.add("Congo");
            listPaises.add("Corea del Norte");
            listPaises.add("Corea del Sur");
            listPaises.add("Costa de Marfil");
            listPaises.add("Costa Rica");
            listPaises.add("Croacia");
            listPaises.add("Cuba");
            listPaises.add("Dinamarca");
            listPaises.add("Dominica");
            listPaises.add("Ecuador");
            listPaises.add("Egipto");
            listPaises.add("El Salvador");
            listPaises.add("Emiratos Árabes Unidos");
            listPaises.add("Eritrea");
            listPaises.add("Eslovaquia");
            listPaises.add("Eslovenia");
            listPaises.add("España");
            listPaises.add("Estados Unidos de América");
            listPaises.add("Estonia");
            listPaises.add("Etiopía");
            listPaises.add("Filipinas");
            listPaises.add("Finlandia");
            listPaises.add("Fiyi");
            listPaises.add("Francia");
            listPaises.add("Gabón");
            listPaises.add("Gambia");
            listPaises.add("Georgia");
            listPaises.add("Ghana");
            listPaises.add("Gibraltar");
            listPaises.add("Granada");
            listPaises.add("Grecia");
            listPaises.add("Groenlandia");
            listPaises.add("Guadalupe");
            listPaises.add("Guam");
            listPaises.add("Guatemala");
            listPaises.add("Guayana Francesa");
            listPaises.add("Guernsey");
            listPaises.add("Guinea");
            listPaises.add("Guinea Ecuatorial");
            listPaises.add("Guinea-Bissau");
            listPaises.add("Guyana");
            listPaises.add("Haití");
            listPaises.add("Honduras");
            listPaises.add("Hong kong");
            listPaises.add("Hungría");
            listPaises.add("India");
            listPaises.add("Indonesia");
            listPaises.add("Irán");
            listPaises.add("Irak");
            listPaises.add("Irlanda");
            listPaises.add("Isla Bouvet");
            listPaises.add("Isla de Man");
            listPaises.add("Isla de Navidad");
            listPaises.add("Isla Norfolk");
            listPaises.add("Islandia");
            listPaises.add("Islas Bermudas");
            listPaises.add("Islas Caimán");
            listPaises.add("Islas Cocos (Keeling)");
            listPaises.add("Islas Cook");
            listPaises.add("Islas de Åland");
            listPaises.add("Islas Feroe");
            listPaises.add("Islas Georgias del Sur y Sandwich del Sur");
            listPaises.add("Islas Heard y McDonald");
            listPaises.add("Islas Maldivas");
            listPaises.add("Islas Malvinas");
            listPaises.add("Islas Marianas del Norte");
            listPaises.add("Islas Marshall");
            listPaises.add("Islas Pitcairn");
            listPaises.add("Islas Salomón");
            listPaises.add("Islas Turcas y Caicos");
            listPaises.add("Islas Ultramarinas Menores de Estados Unidos");
            listPaises.add("Islas Vírgenes Británicas");
            listPaises.add("Islas Vírgenes de los Estados Unidos");
            listPaises.add("Israel");
            listPaises.add("Italia");
            listPaises.add("Jamaica");
            listPaises.add("Japón");
            listPaises.add("Jersey");
            listPaises.add("Jordania");
            listPaises.add("Kazajistán");
            listPaises.add("Kenia");
            listPaises.add("Kirgizstán");
            listPaises.add("Kiribati");
            listPaises.add("Kuwait");
            listPaises.add("Líbano");
            listPaises.add("Laos");
            listPaises.add("Lesoto");
            listPaises.add("Letonia");
            listPaises.add("Liberia");
            listPaises.add("Libia");
            listPaises.add("Liechtenstein");
            listPaises.add("Lituania");
            listPaises.add("Luxemburgo");
            listPaises.add("México");
            listPaises.add("Mónaco");
            listPaises.add("Macao");
            listPaises.add("Macedônia");
            listPaises.add("Madagascar");
            listPaises.add("Malasia");
            listPaises.add("Malawi");
            listPaises.add("Mali");
            listPaises.add("Malta");
            listPaises.add("Marruecos");
            listPaises.add("Martinica");
            listPaises.add("Mauricio");
            listPaises.add("Mauritania");
            listPaises.add("Mayotte");
            listPaises.add("Micronesia");
            listPaises.add("Moldavia");
            listPaises.add("Mongolia");
            listPaises.add("Montenegro");
            listPaises.add("Montserrat");
            listPaises.add("Mozambique");
            listPaises.add("Namibia");
            listPaises.add("Nauru");
            listPaises.add("Nepal");
            listPaises.add("Nicaragua");
            listPaises.add("Niger");
            listPaises.add("Nigeria");
            listPaises.add("Niue");
            listPaises.add("Noruega");
            listPaises.add("Nueva Caledonia");
            listPaises.add("Nueva Zelanda");
            listPaises.add("Omán");
            listPaises.add("Países Bajos");
            listPaises.add("Pakistán");
            listPaises.add("Palau");
            listPaises.add("Palestina");
            listPaises.add("Panamá");
            listPaises.add("Papúa Nueva Guinea");
            listPaises.add("Paraguay");
            listPaises.add("Perú");
            listPaises.add("Polinesia Francesa");
            listPaises.add("Polonia");
            listPaises.add("Portugal");
            listPaises.add("Puerto Rico");
            listPaises.add("Qatar");
            listPaises.add("Reino Unido");
            listPaises.add("República Centroafricana");
            listPaises.add("República Checa");
            listPaises.add("República Dominicana");
            listPaises.add("Reunión");
            listPaises.add("Ruanda");
            listPaises.add("Rumanía");
            listPaises.add("Rusia");
            listPaises.add("Sahara Occidental");
            listPaises.add("Samoa");
            listPaises.add("Samoa Americana");
            listPaises.add("San Bartolomé");
            listPaises.add("San Cristóbal y Nieves");
            listPaises.add("San Marino");
            listPaises.add("San Martín (Francia)");
            listPaises.add("San Pedro y Miquelón");
            listPaises.add("San Vicente y las Granadinas");
            listPaises.add("Santa Elena");
            listPaises.add("Santa Lucía");
            listPaises.add("Santo Tomé y Príncipe");
            listPaises.add("Senegal");
            listPaises.add("Serbia");
            listPaises.add("Seychelles");
            listPaises.add("Sierra Leona");
            listPaises.add("Singapur");
            listPaises.add("Siria");
            listPaises.add("Somalia");
            listPaises.add("Sri lanka");
            listPaises.add("Sudáfrica");
            listPaises.add("Sudán");
            listPaises.add("Suecia");
            listPaises.add("Suiza");
            listPaises.add("Surinám");
            listPaises.add("Svalbard y Jan Mayen");
            listPaises.add("Swazilandia");
            listPaises.add("Tadjikistán");
            listPaises.add("Tailandia");
            listPaises.add("Taiwán");
            listPaises.add("Tanzania");
            listPaises.add("Territorio Británico del Océano Índico");
            listPaises.add("Territorios Australes y Antárticas Franceses");
            listPaises.add("Timor Oriental");
            listPaises.add("Togo");
            listPaises.add("Tokelau");
            listPaises.add("Tonga");
            listPaises.add("Trinidad y Tobago");
            listPaises.add("Tunez");
            listPaises.add("Turkmenistán");
            listPaises.add("Turquía");
            listPaises.add("Tuvalu");
            listPaises.add("Ucrania");
            listPaises.add("Uganda");
            listPaises.add("Uruguay");
            listPaises.add("Uzbekistán");
            listPaises.add("Vanuatu");
            listPaises.add("Venezuela");
            listPaises.add("Vietnam");
            listPaises.add("Wallis y Futuna");
            listPaises.add("Yemen");
            listPaises.add("Yibuti");
            listPaises.add("Zambia");
            listPaises.add("Zimbabue");
        } catch (Exception e) {
        }
        return listPaises;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="generarCodigoDigitos">
    /**
     *
     * @param n
     * @param digitos
     * @return
     */
    public static String generarCodigoDigitos(Integer n, Integer digitos) {
        String t = String.valueOf(n);
        try {
            if (t.length() >= digitos) {
                return t;
            } else {
                Integer dif = digitos - t.length();
                String c = "";
                for (int i = 0; i < dif; i++) {
                    c += "0";
                }
                t = c.trim() + t.trim();
            }

        } catch (Exception e) {
            errorMessage("generarCodigoDigitos() " + e.getLocalizedMessage());
        }

        return t.trim();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="generarCodigoDigitos"> 
    public static Integer getEntero(Double n) {
        Integer value = 0;
        try {
            String str = String.valueOf(n);
            value = Integer.parseInt(str.substring(0, str.indexOf('.')));

        } catch (Exception e) {
            errorMessage("getEntero() " + e.getLocalizedMessage());
        }

        return value;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getDecimal"> 
    public static Integer getDecimal(Double n) {
        Integer value = 0;
        try {
            String str = String.valueOf(n);
            value = Integer.parseInt(str.substring(str.indexOf('.') + 1));

        } catch (Exception e) {
            errorMessage("getDecimal() " + e.getLocalizedMessage());
        }

        return value;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getRandomNumber"> 
    public static Integer getRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    // </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc="mesAnterior()"> 
     /**
     * devuelve el nombre del mes anterior
     *
     * @param mes
     * @return
     */
    public static  String getMesAnterior(String mes) {
        String mesanterior = "";
        try {
            switch (mes.toLowerCase()) {
                case "enero":
                    mesanterior = "diciembre";
                    break;

                case "febrero":
                    mesanterior = "enero";
                    break;
                case "marzo":
                    mesanterior = "febrero";
                    break;
                case "abril":
                    mesanterior = "marzo";
                    break;
                case "mayo":
                    mesanterior = "abril";
                    break;
                case "junio":
                    mesanterior = "mayo";
                    break;
                case "julio":
                    mesanterior = "junio";
                    break;
                case "agosto":
                    mesanterior = "julio";
                    break;
                case "septiembre":
                    mesanterior = "agosto";
                    break;
                case "octubre":
                    mesanterior = "septiembre";
                    break;
                case "noviembre":
                    mesanterior = "octubre";
                    break;
                case "diciembre":
                    mesanterior = "noviembre";
                    break;
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("mesAnterior() " + e.getLocalizedMessage());
        }
        return mesanterior;
    }    // </editor-fold>
    
    
    
    
    // <editor-fold defaultstate="collapsed" desc="isVacio(String texto)()"> 
    /**
     * return true si es null empty equals("")
     * @param texto
     * @return 
     */
    public static Boolean isVacio(String texto){
        texto= texto.trim();
        if(texto == null || texto.equals("") || texto.isEmpty()){
           return true;
        }else{
            return false;
        }
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isVacio(Integer texto)"> 
    /**
     * return true si es null empty equals("")
     * @param texto
     * @return 
     */
    public static Boolean isVacio(Integer texto){
        if(texto == null || texto.equals("") ){
           return true;
        }else{
            return false;
        }
    }// </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="createJasper">  
    private Boolean createJasper(String reportSource, String pathJasper) {
        try {

            JasperCompileManager.compileReportToFile(reportSource, pathJasper);

            return true;
        } catch (Exception e) {

           errorMessage("createJasper() " + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold> 
    
    
    // <editor-fold defaultstate="collapsed" desc="primercaracter()">
    public static  String primerCaracter(String texto) {
        try {
            if (texto.length() > 0) {
                return texto.trim().substring(0, 1).toUpperCase();
            }

        } catch (Exception e) {
            JsfUtil.errorMessage("primerCaracter() " + e.getLocalizedMessage());
        }
        return texto;
    }

    // </editor-fold>
}
