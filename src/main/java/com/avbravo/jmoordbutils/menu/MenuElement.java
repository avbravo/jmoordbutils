/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordbutils.menu;

import java.io.Serializable;
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Named;

/**
 *
 * @authoravbravo
 */
//@Named
//@SessionScoped
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
    
    /**
     * inicializa
     * @param t 
     */
    public void initialize(Boolean t){
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

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getQuery() {
        return query;
    }

    public void setQuery(Boolean query) {
        this.query = query;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getList() {
        return list;
    }

    public void setList(Boolean list) {
        this.list = list;
    }

  



}
