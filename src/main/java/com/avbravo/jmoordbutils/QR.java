/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author avbravo
 */
public class QR {
public static Exception exception= new Exception();

    /**
     * Genera codigo qr
     *
     * @param text: Testo
     * @param width: Alto de la imagen
     * @param height: Ancho de la imagen
     * @param filePath: Ruta
     * @throws WriterException
     * @throws IOException generateQRCodeImage("Aristides villlarreal bravo",
     * 350, 350, "/home/avbravo/Descargas");
     */
    public static Boolean generarImagenQRCode(String texto, int alto, int ancho, String rutaImagen)
            throws WriterException, IOException {
          try{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, alto, ancho);

        Path path = FileSystems.getDefault().getPath(rutaImagen);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
         }catch(Exception ex){
            System.out.println("Error "+ex.getLocalizedMessage());
            exception=ex;
        }
        return false;
    }

//    public static Boolean generarImagenQRCode350x350(String texto, String rutaImagen)
//            throws WriterException, IOException {
//        try{
    public static Boolean generarImagenQRCode350x350(String texto, String rutaImagen)
 {
        try{
            
      
        int width = 350;
        int height = 350;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(rutaImagen);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return true;
          }catch(Exception ex){
            System.out.println("Error "+ex.getLocalizedMessage());
            exception=ex;
        }
        return false;
    }

    /**
     *
     * @param qrCodeimage
     * @return
     * @throws IOException
     */
    private static String _internalDecodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            exception=e;
            return null;
            
        }
    }

    /**
     *
     * @param imagePath
     * @return decodificarCodeQR("/home/avbravo/Descargas/b.png")
     */
    public static String decodificarQRCode(String rutaImagen, Boolean... showDialog) {
        String texto = "";
        Boolean dialog = false;
         

        try {
               if (showDialog.length != 0) {
                dialog = showDialog[0];

            }
            File file = new File(rutaImagen);
            String decodedText = _internalDecodeQRCode(file);
            
            if (decodedText == null) {
                if (dialog) {
                    JsfUtil.warningDialog("Advertencia", "No hay codigo QR en la imagen");
                } else {
                    JsfUtil.warningMessage("No hay codigo QR en la imagen");
                }

    
            } else {
                texto = decodedText;
    
            }
        } catch (IOException e) {
            if(dialog){
                JsfUtil.errorDialog("Error", "No puede decodficar la imagen: " + e.getMessage());
            }else{
                JsfUtil.errorMessage("No puede decodficar la imagen: " + e.getMessage());
            }
            
//            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
        return texto;
    }

}
