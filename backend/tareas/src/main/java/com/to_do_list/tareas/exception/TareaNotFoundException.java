package com.to_do_list.tareas.exception;

public class TareaNotFoundException extends RuntimeException{
    public TareaNotFoundException(String mensaje){
        super(mensaje);
    }
}
