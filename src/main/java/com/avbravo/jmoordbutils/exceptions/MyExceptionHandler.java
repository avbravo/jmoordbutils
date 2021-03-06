/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbutils.exceptions;

import java.util.Iterator;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;

/**
 *
 * @author avbravo
 */
public class MyExceptionHandler extends ExceptionHandlerWrapper {
 
    private ExceptionHandler wrapped;
 
    Logger log;
    FacesContext fc;
 
    public MyExceptionHandler(ExceptionHandler exceptionHandler) {
        wrapped = exceptionHandler;
        log = Logger.getLogger(this.getClass().getName());
        fc = FacesContext.getCurrentInstance();
    }
 
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }
 
    @Override
    public void handle() {
 
        Iterator<ExceptionQueuedEvent> i = super.getUnhandledExceptionQueuedEvents().iterator();
 
        while (i.hasNext()) {
 
            Throwable t = i.next().getContext().getException();
 
            if (t instanceof ViewExpiredException) {
                try {
                    // Handle the exception, for example:
                    log.severe("ViewExpiredException occurred!");
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An exception occurred",
                            "ViewExpiredException happened!"));
                    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, "viewExpiredException");
                }
                finally {
                    i.remove();
                }
            }
 
            /*
            else if (t instanceof SomeOtherException) {
                // handle SomeOtherException
            }
            */
 
        }
 
        // let the parent handle the rest
        getWrapped().handle();
    }
 
    
}
