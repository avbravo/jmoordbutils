/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.downloadfile;

import com.avbravo.jmoordbutils.DateUtil;
import com.avbravo.jmoordbutils.DownloadUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author avbravo
 */
@Named
//@RequestScoped
@SessionScoped

public class JmoordbViewImageServices implements Serializable{
    //    private StreamedContent file;
    private StreamedContent fileDisk;
    
      String name= "";
            String pathOfFile="";
            String extensionOfFileInPath= "";
    /**
     * Creates a new instance of FileDownloadController
     */
     String textplain ="text/plain";
   String pdf="application/pdf";
  String imagepng ="image/png";
 String imagejpg ="image/jpg";
   
   private String selectedOption=pdf;
private String rutaArchivo="/home/avbravo/Documentos/im.jpg";
   
    InputStream is = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathOfFile() {
        return pathOfFile;
    }

    public void setPathOfFile(String pathOfFile) {
        this.pathOfFile = pathOfFile;
    }

    public String getExtensionOfFileInPath() {
        return extensionOfFileInPath;
    }

    public void setExtensionOfFileInPath(String extensionOfFileInPath) {
        this.extensionOfFileInPath = extensionOfFileInPath;
    }

    public String getTextplain() {
        return textplain;
    }

    public void setTextplain(String textplain) {
        this.textplain = textplain;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getImagepng() {
        return imagepng;
    }

    public void setImagepng(String imagepng) {
        this.imagepng = imagepng;
    }

    public String getImagejpg() {
        return imagejpg;
    }

    public void setImagejpg(String imagejpg) {
        this.imagejpg = imagejpg;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    
    
    
    
    
    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

   
    

public JmoordbViewImageServices() {
  System.out.println(" constructor JmoordbViewImageServices() "+DateUtil.horaMinutoAMPMDeUnaFecha(DateUtil.fechaHoraActual()));        
       
    }

  @PostConstruct
    public void init() {
        //Usuario logeado
        // User user = (User) JmoordbContext.get("jmoordb_user");
      System.out.println(" init  JmoordbViewImageServices() "+DateUtil.horaMinutoAMPMDeUnaFecha(DateUtil.fechaHoraActual()));        
       
    }



    public String fromDisk(String pathFile) {
        try { 
            /**
             * 
             */
            rutaArchivo=pathFile;
       name= DownloadUtil.nameOfFileInPath(rutaArchivo);
     pathOfFile=DownloadUtil.pathOfFile(rutaArchivo);
          extensionOfFileInPath= DownloadUtil.extensionOfFileInPath(rutaArchivo);
            System.out.println("---------------------------------");
            System.out.println("name "+name);
            System.out.println("pathOfFile "+pathOfFile);
            System.out.println("extensionOfFileInPath "+extensionOfFileInPath);
            System.out.println("---------------------------------");
            
            File filet = new File(rutaArchivo);
		is = new FileInputStream(filet);
		

                
    fileDisk =      DefaultStreamedContent.builder()
            .contentType(DownloadUtil.typeOfMimeTypeForDownload(extensionOfFileInPath))
            .name(name+extensionOfFileInPath)
                .stream(() -> is)
            .build();

          
        } catch (Exception e) {
            System.out.println("fromDisk() "+e.getLocalizedMessage());
            showError(e.getLocalizedMessage());
        }
        
       return "";
    }
  
  
  


    public StreamedContent getFileDisk() {
   
        return fileDisk;
    }
    
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
     public void showError(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error Message", message);
    }
}
