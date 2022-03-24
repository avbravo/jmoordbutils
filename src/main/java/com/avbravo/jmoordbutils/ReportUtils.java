/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;


import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPCell;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author avbravo
 */
public class ReportUtils implements Serializable {

// <editor-fold defaultstate="collapsed" desc="Paragraph paragraph(String texto, Font fonFactory,Integer ALIGN)">
    /**
     * Paragraph p = new Paragraph("MI TEXTO", FontFactory.getFont("arial", //
     * fuente 12, // tamaño Font.BOLD)); p.setAlignment(Element.ALIGN_CENTER);
     *
     * @param texto "Mi Texto"
     * @param fonFactory FontFactory.getFont("arial",12, Font.BOLD);
     * @param ALIGN: Element.ALIGN_CENTER
     * @return
     */
    public static Paragraph paragraph(String texto, Font fonFactory, Integer ALIGN) {
        Paragraph p = new Paragraph(texto, fonFactory);
        p.setAlignment(ALIGN);
        return p;
    }

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="PdfPCell PdfCell(String texto, Font fontFactory, Integer ALIGN)">
    public static PdfPCell PdfCell(String texto, Font fontFactory, Integer ALIGN) {

        PdfPCell cell = new PdfPCell(new Paragraph(texto, fontFactory));
        cell.setHorizontalAlignment(ALIGN);
        return cell;
    }
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="PdfPCell PdfCell(String texto, Font fontFactory, Integer ALIGN)">
    public static PdfPCell PdfCell(String texto, Font fontFactory) {

        PdfPCell cell = new PdfPCell(new Paragraph(texto, fontFactory));

        return cell;
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="printPDF(ByteArrayOutputStream baos)">
    public static Boolean printPDF(ByteArrayOutputStream baos) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Object response = context.getExternalContext().getResponse();
            if (response instanceof HttpServletResponse) {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Contentdisposition", "attachment;filename=report.pdf");
                //        hsr.setHeader("Content-disposition", "attachment");
                hsr.setContentLength(baos.size());
                try {
                    ServletOutputStream out = hsr.getOutputStream();
                    baos.writeTo(out);
                    out.flush();
                } catch (IOException ex) {
                    System.out.println("Error:  " + ex.getMessage());
                    return false;
                }
                context.responseComplete();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc=" ByteArrayOutputStream initializate()">
    public static  ByteArrayOutputStream initializate(){
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         return baos;
    }
    // </editor-fold>  
    
  
            
}
