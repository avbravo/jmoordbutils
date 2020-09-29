/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils;

/**
 *
 * @author avbravo
 */
public class PasswordInfo {
    private Integer numberOfUpper;
    private Integer numberOfLower;
    private Integer numberOfNumber;

    public PasswordInfo() {
    }

    public PasswordInfo(Integer numberOfUpper, Integer numberOfLower, Integer numberOfNumber) {
        this.numberOfUpper = numberOfUpper;
        this.numberOfLower = numberOfLower;
        this.numberOfNumber = numberOfNumber;
    }

    
    
    public Integer getNumberOfUpper() {
        return numberOfUpper;
    }

    public void setNumberOfUpper(Integer numberOfUpper) {
        this.numberOfUpper = numberOfUpper;
    }

    public Integer getNumberOfLower() {
        return numberOfLower;
    }

    public void setNumberOfLower(Integer numberOfLower) {
        this.numberOfLower = numberOfLower;
    }

    public Integer getNumberOfNumber() {
        return numberOfNumber;
    }

    public void setNumberOfNumber(Integer numberOfNumber) {
        this.numberOfNumber = numberOfNumber;
    }
 
    
    
    
    
}
