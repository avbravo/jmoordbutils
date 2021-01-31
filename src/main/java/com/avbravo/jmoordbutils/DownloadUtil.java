/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;

import static com.avbravo.jmoordbutils.JsfUtil.errorDialog;

/**
 *
 * @author avbravo
 */
public class DownloadUtil {
    // <editor-fold defaultstate="collapsed" desc="methodt() ">
    public static String typeOfMimeTypeForDownload(String extension){
        String text ="";
        try {
    
 
  switch(extension){
            case "jpg":
          text="image/jpg";
                break;
            case "png":
               text="image/png";
                break;
            case "pdf":
               text="application/pdf";
                break;
            case "plain":
                 text ="text/plain";
            case "cvs":
                 text ="text/csv";
           
                break;
                
            case "txt":
                 text="application/txt";
           
                break;
                
            default:
               System.out.println("Esta extesnsion"+extension + " no es soportada...");
        }
        } catch (Exception e) {
            System.out.println("typeOfMimeTypeForDownload() "+e.getLocalizedMessage());
        }
        return text;
    }
    // </editor-fold>
    
    /**
     *
     * @param filenamePath
     * @return el nombre del archivo que esta en un path
     */
    public static String nameOfFileInPath(String filenamePath) {
        String name = "";
        try {
            name = filenamePath.substring(filenamePath.lastIndexOf(System.getProperty("file.separator")) + 1,
                    filenamePath.lastIndexOf('.'));
        } catch (Exception e) {
            errorDialog("nameOfFileInPath()", e.getLocalizedMessage());
        }
        return name;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String pathOfFile(String filenamePath)" >
    /**
     *
     * @param filenamePath
     * @return el path de un archivo
     */
    public static String pathOfFile(String filenamePath) {
        String path = "";
        try {
            path = filenamePath.substring(0, filenamePath.lastIndexOf(System.getProperty("file.separator")));
        } catch (Exception e) {
            errorDialog("pathOfFile()", e.getLocalizedMessage());
        }
        return path;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String extensionOfFileInPath(String filenamePath)">
    /**
     *
     * @param filenamePath
     * @return devuelve la extension de un archivo en un path
     */
    public static String extensionOfFileInPath(String filenamePath) {
        String extension = "";
        try {
            extension = filenamePath.substring(filenamePath.lastIndexOf('.') + 1, filenamePath.length());
        } catch (Exception e) {
        }
        return extension;
    }
    // </editor-fold>
}
