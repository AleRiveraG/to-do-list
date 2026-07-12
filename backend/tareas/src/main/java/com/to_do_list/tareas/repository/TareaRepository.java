package com.to_do_list.tareas.repository;

import com.to_do_list.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TareaRepository {

    private List<Tarea> listaTareas = new ArrayList<>();

    public List<Tarea> obtenerTodo(){
        return listaTareas;
    }

    public Tarea agregarTarea(Tarea tarea){
        tarea.setEstado("PENDIENTE");
        listaTareas.add(tarea);
        return tarea;
    }

    public Tarea modificarTarea(Tarea tarea, Long id){

        for(Tarea tarea1: listaTareas){
            if(tarea1.getId().equals(tarea.getId())){
                tarea1.setId(id);
                tarea1.setNombre(tarea.getNombre());
                tarea1.setHora_inicio(tarea.getHora_inicio());
                tarea1.setHora_termino(tarea.getHora_termino());
                tarea1.setEstado(tarea.getEstado());

            }
            return tarea1;
        }
        return null;
    }

    public void eliminarTarea(Long id){
        for(Tarea tarea : listaTareas){
            if(tarea.getId().equals(id)){
                listaTareas.remove(tarea);
            }
        }
    }

    public Tarea cambiarEstado(Long id){

        for(Tarea tarea : listaTareas){
            if(tarea.getId().equals(id) && tarea.getEstado().equals("PENDIENTE")){
                tarea.setEstado("COMPLETADO");
                return tarea;
            }
        }
        return null;
    }

}
