/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.fileencripter;

import com.avbravo.jmoordbutils.JsfUtil;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author avbravo Fuente:
 * https://javapapers.com/java/java-file-encryption-decryption-using-aes-password-based-encryption-pbe/
 */
public class FileEncryption {

    // <editor-fold defaultstate="collapsed" desc="Boolean cifrar(String filenamepath, String password)">
    /**
     * 
     * @param filenamepath
     * @param password
     * @return  archivos cifrados en el directorio actual
     */
    public static Boolean cifrar(String filenamepath, String password) {
        try {
           String filenameEnc = filenamepath.substring(0, filenamepath.lastIndexOf('.'))+".enc";
           String filenameIvEnc = filenamepath.substring(0, filenamepath.lastIndexOf('.'))+"iv.enc";
           String filenameDes = filenamepath.substring(0, filenamepath.lastIndexOf('.'))+".des";

            // password to encrypt the file
            //String password = "javapapers";
            // file to be encrypted
            FileInputStream inFile = new FileInputStream(filenamepath);

            // encrypted file
          //  FileOutputStream outFile = new FileOutputStream("encryptedfile.des");
            FileOutputStream outFile = new FileOutputStream(filenameDes);

            // password, iv and salt should be transferred to the other end
            // in a secure manner
            // salt is used for encoding
            // writing it to a file
            // salt should be transferred to the recipient securely
            // for decryption
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);
            FileOutputStream saltOutFile = new FileOutputStream(filenameEnc );
            saltOutFile.write(salt);
            saltOutFile.close();

            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
                    256);
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            //
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();

            // iv adds randomness to the text and just makes the mechanism more
            // secure
            // used while initializing the cipher
            // file to store the iv
            FileOutputStream ivOutFile = new FileOutputStream(filenameIvEnc);
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            ivOutFile.write(iv);
            ivOutFile.close();

            //file encryption
            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) {
                    outFile.write(output);
                }
            }

            byte[] output = cipher.doFinal();
            if (output != null) {
                outFile.write(output);
            }

            inFile.close();
            outFile.flush();
            outFile.close();

            System.out.println("File Encrypted.");
            return true;
        } catch (Exception ex) {
            System.out.println("cifrar() " + ex.getLocalizedMessage());
        }
        return false;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean cifrar(String filenamepath, String targetDirectory, String password) ">
    /**
     * 
     * @param filenamepath
     * @param targetDirectory
     * @param password
     * @return  archivos cifrados en el directorio destino especificado
     */
    public static Boolean cifrar(String filenamepath, String targetDirectory, String password) {
        try {
            String name = JsfUtil.nameOfFileInPath(filenamepath);
            String extension = JsfUtil.extensionOfFileInPath(filenamepath);
            if(!JsfUtil.existDirectory(targetDirectory)){
                if(JsfUtil.mkdir(targetDirectory)){
                    
                }else{
                    
                }
            }
            
           String filenameEnc =targetDirectory+name+".enc";
           String filenameIvEnc = targetDirectory+name+"iv.enc";
           String filenameDes = targetDirectory+name+".des";

            // password to encrypt the file
            //String password = "javapapers";
            // file to be encrypted
            FileInputStream inFile = new FileInputStream(filenamepath);

            // encrypted file
          //  FileOutputStream outFile = new FileOutputStream("encryptedfile.des");
            FileOutputStream outFile = new FileOutputStream(filenameDes);

            // password, iv and salt should be transferred to the other end
            // in a secure manner
            // salt is used for encoding
            // writing it to a file
            // salt should be transferred to the recipient securely
            // for decryption
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);
            FileOutputStream saltOutFile = new FileOutputStream(filenameEnc );
            saltOutFile.write(salt);
            saltOutFile.close();

            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,
                    256);
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            //
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();

            // iv adds randomness to the text and just makes the mechanism more
            // secure
            // used while initializing the cipher
            // file to store the iv
            FileOutputStream ivOutFile = new FileOutputStream(filenameIvEnc);
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            ivOutFile.write(iv);
            ivOutFile.close();

            //file encryption
            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = inFile.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null) {
                    outFile.write(output);
                }
            }

            byte[] output = cipher.doFinal();
            if (output != null) {
                outFile.write(output);
            }

            inFile.close();
            outFile.flush();
            outFile.close();

            System.out.println("File Encrypted.");
            return true;
        } catch (Exception ex) {
            System.out.println("cifrar() " + ex.getLocalizedMessage());
        }
        return false;
    }
    
    // </editor-fold>

}
