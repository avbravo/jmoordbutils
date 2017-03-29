/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.avbravoutils.menu;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @authoravbravo
 */
@Named
@SessionScoped
public class MenuElement implements Serializable{

    private static final long serialVersionUID = 1L;
    Boolean menu;
    Boolean create;
    Boolean query;
    Boolean edit;
    Boolean delete;
    Boolean list;

    /**
     * Creates a new instance of MenuPojo
     */
    public MenuElement() {
        
    }

    public MenuElement(Boolean menu, Boolean create, Boolean query, Boolean edit, Boolean delete, Boolean list) {
        this.menu = menu;
        this.create = create;
        this.query = query;
        this.edit = edit;
        this.delete = delete;
        this.list = list;
    }
    
    
    public void habilitar(Boolean t){
        menu=t;
        create=t;
        query=t;
        edit=t;
        delete=t;
        list=t;
    }

    public Boolean getMenu() {
        return menu;
    }

    public void setMenu(Boolean menu) {
        this.menu = menu;
    }

    public Boolean getCrear() {
        return create;
    }

    public void setCrear(Boolean create) {
        this.create = create;
    }

    public Boolean getConsultar() {
        return query;
    }
    public void setConsultar(Boolean query) {
        this.query = query;
    }

    public Boolean getEditar() {
        return edit;
    }

    public void setEditar(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getEliminar() {
        return delete;
    }

    public void setEliminar(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getListar() {
        return list;
    }

    public void setListar(Boolean list) {
        this.list = list;
    }




}
