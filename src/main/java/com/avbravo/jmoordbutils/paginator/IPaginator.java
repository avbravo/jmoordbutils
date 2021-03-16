/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.paginator;

import com.avbravo.jmoordbutils.JsfUtil;
import static com.avbravo.jmoordbutils.JsfUtil.nameOfMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author avbravo
 */
public interface IPaginator {

    // <editor-fold defaultstate="collapsed" desc="String last(Paginator paginator)">
    public default String last(Paginator paginator) {
        try {

            paginator.setPage(paginator.getNumberOfPage());

            move(paginator);
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String first(Paginator paginator)">
    public default String first(Paginator paginator) {
        try {

            paginator.setPage(1);

            move(paginator);
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String next(Paginator paginator)">
    public default String next(Paginator paginator) {
        try {

            if (paginator.getPage() < paginator.getNumberOfPage()) {
                paginator.setPage(paginator.getPage() + 1);
            }

            move(paginator);
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String back(Paginator paginator)">
    public default String back(Paginator paginator) {
        try {

            if (paginator.getPage() > 1) {

                paginator.setPage(paginator.getPage() - 1);

            }

            move(paginator);
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String skip(Paginator paginator)">
    /**
     * componentes <jmoordbjsf:paginator> toma el numero de pagina y lo mueve
     *
     * @param field
     * @param value
     * @return
     */
    public default String skip(Paginator paginator) {
        try {

            paginator.setPage(paginator.getPageforskip());

            move(paginator);

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="move(Paginator paginator)">
    public default void move(Paginator paginator) {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Integer numberOfPages(Integer rows,Integer rowForPage)">
    default Integer numberOfPages(Integer rows, Integer rowForPage) {
        Integer numberOfPage = 1;
        try {

            if (rows > 0) {
                numberOfPage = rows / rowForPage;
                if ((rows % rowForPage) > 0) {
                    numberOfPage++;
                }
            }
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return numberOfPage;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<Integer> arrayListOfPage(Integer numberOfPage) ">
    /**
     * Devuele un array list en base al numero de paginas pasadaas
     *
     * @param rowsForPage
     * @param doc
     * @return
     */
    default public List<Integer> arrayListOfNumber(Integer numberOfPage) {
        List<Integer> pages = new ArrayList<>();
        try {

            pages = IntStream.range(1, numberOfPage + 1)
                    .boxed()
                    .collect(Collectors.toList());

            return pages;

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JsfUtil.nameOfClass() + " Metodo:" + JsfUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JsfUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return pages;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean sizeExceeds(String text,Integer largo) ">
    default public Boolean sizeExceeds(String text, Integer largo) {

        try {
            if (text.length() > largo) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("amanoExcede(() " + e.getLocalizedMessage());
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String shortTextObservacio(Boletas boletas,Integer largo)">
    default public String shortTextObservacion(String text, Integer largo) {

        try {

            if (text.length() > largo) {
                text = text.substring(0, largo);
            }

        } catch (Exception e) {
            System.out.println("shortTextObservacion() " + e.getLocalizedMessage());

        }
        return text;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String showDialogoObservacion()">
    default public String showDialogoObservacion() {
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Paginator loadPaginator(Paginator paginator)">
    default public Paginator loadPaginator(Paginator paginator) {
        return paginator;
    }
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="move(Paginator paginator)">
    public default void move(Paginator paginator, Object s) {
        
    }

    // </editor-fold>
}
