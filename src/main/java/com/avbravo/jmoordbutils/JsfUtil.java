/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;
// <editor-fold defaultstate="collapsed" desc="import">  

import com.avbravo.jmoordbutils.crypto.CryptoConverter;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    // static Pattern object, since pattern is fixed
    private static Pattern pattern;

    // non-static Matcher object because it's created from the input String
    private static Matcher matcher;

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
                    r = Math.floor(n);
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
    // <editor-fold defaultstate="collapsed" desc="redondearEnteroSuperior(Double n)">

    public static Double redondearEnteroSuperior(Double n) {
        Double r = 0.0;
        try {

            return Math.ceil(n);
        } catch (Exception e) {
            errorMessage("redondearEnteroSuperior() " + e.getLocalizedMessage());
        }
        return r;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="redondearEnteroInferior(Double n)">
    public static Double redondearEnteroInferior(Double n) {
        Double r = 0.0;
        try {

            return Math.floor(n);
        } catch (Exception e) {
            errorMessage("redondearEnteroInferior() " + e.getLocalizedMessage());
        }
        return r;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="redondearMasCercano(Double n)">

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
// <editor-fold defaultstate="collapsed" desc="round(Double value)"> 

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



    // <editor-fold defaultstate="collapsed" desc="isVacio(String texto)()"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isVacio(String texto) {
        texto = texto.trim();
        return texto == null || texto.equals("") || texto.isEmpty();
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isVacio(Integer texto)"> 

    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isVacio(Integer texto) {
        return texto == null || texto.equals("");
    }// </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="isVacio(Integer texto)"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isVacio(Double texto) {
        return texto == null || texto.equals("");
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean isNegativo(Double texto)"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isNegativo(Double numero) {
        return numero == null || numero.equals("") || numero <0;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean isNegativo(Double texto)"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isNegativo(Integer numero) {
        return numero == null || numero.equals("") || numero <0;
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
    public static String primerCaracter(String texto) {
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
    
    // <editor-fold defaultstate="collapsed" desc="emailValidate(String email)">
    
    public static Boolean emailValidate(String email) {
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }// </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="nameOfClassAndMethod()">
 
      public static String nameOfClassAndMethod() {
    final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
    final String s = e.getClassName();
    return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
}// </editor-fold>
      // <editor-fold defaultstate="collapsed" desc="nameOfClass()">

   public static String nameOfClass() {
    final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
    final String s = e.getClassName();
    return s.substring(s.lastIndexOf('.') + 1, s.length()) ;
}    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="nameOfMethod(">

   public static String nameOfMethod() {
    final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
    final String s = e.getClassName();
    return  e.getMethodName();
}
   // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc=" String textoDespuesUltimoPunto(String texto)">
   
   /**
    * obtiene el texto despues del ultimo puento
    * @param texto (com.avbravo.entity.Rol)
    * @return Rol
    */
   public static String textoDespuesUltimoPunto(String texto){
       String result="";
          // TODO code application logic here
          try {
       
        Integer pos =texto.lastIndexOf(".");

       result = texto.substring(pos+1, texto.length());

       } catch (Exception e) {
       }
        return result;
        
   }
    // </editor-fold>
}
