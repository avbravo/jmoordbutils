/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.viewanddonwload;

import com.avbravo.jmoordbutils.DownloadUtil;
import com.avbravo.jmoordbutils.JsfUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author avbravo
 */
@Named(value ="jMoordbGraphicImageDownload" )
@RequestScoped
public class JMoordbGraphicImageDownload implements Serializable {

    private StreamedContent fileDisk;

    InputStream is = null;

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    @PostConstruct
    public void init() {
        try {
            System.out.println("init() GraphImageView..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String create(String pathFile){
        try {
            System.out.println("-----------------------------------------");
            System.out.println("----->create "+pathFile);
            System.out.println("-----------------------------------------");
            String name = DownloadUtil.nameOfFileInPath(pathFile);
            String pathOfFile = DownloadUtil.pathOfFile(pathFile);
            String extensionOfFileInPath = DownloadUtil.extensionOfFileInPath(pathFile);

            File filet = new File(pathFile);
            is = new FileInputStream(filet);

            fileDisk = DefaultStreamedContent.builder()
                    .contentType(DownloadUtil.typeOfMimeTypeForDownload(extensionOfFileInPath))
                    .name(name + extensionOfFileInPath)
                    .stream(() -> is)
                    .build();
        } catch (Exception e) {
            System.out.println("generateImage() " + e.getLocalizedMessage());
            JsfUtil.errorMessage("generateImage()" + e.getLocalizedMessage());
        }
        return "";
    }

    public StreamedContent download() {
       

        return fileDisk;
    }

}
