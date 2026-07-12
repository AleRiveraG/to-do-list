package com.to_do_list.tareas.service;

import com.to_do_list.tareas.model.Tarea;
import com.to_do_list.tareas.repository.TareaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;


    public List<Tarea> obtenerTodo(){
        return tareaRepository.obtenerTodo();
    }

    public Tarea agregarTarea(Tarea tarea){
        return tareaRepository.agregarTarea(tarea);
    }

    public Tarea modificarTarea(Tarea tarea, Long id){
        return tareaRepository.modificarTarea(tarea, id);
    }

    public void eliminarTarea(Long id){
        tareaRepository.eliminarTarea(id);
    }

    public Tarea cambiarEstado(Long id){
        return tareaRepository.cambiarEstado(id);
    }

}
