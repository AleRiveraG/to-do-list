package com.to_do_list.tareas.controller;

import com.to_do_list.tareas.model.Tarea;
import com.to_do_list.tareas.service.TareaService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> obtenerTodo(){
        return tareaService.obtenerTodo();
    }

    @PostMapping
    public Tarea agregarTarea(@RequestBody Tarea tarea){
        return tareaService.agregarTarea(tarea);
    }

    @PutMapping("/{id}")
    public Tarea modificarTarea(@PathVariable Long id, @RequestBody Tarea tarea){
        return tareaService.modificarTarea(tarea, id);
    }

    @DeleteMapping("/{id}")
    public void eliminarTarea(@PathVariable Long id){
        tareaService.eliminarTarea(id);
    }

    @PatchMapping("/{id}")
    public Tarea cambiarEstado(@PathVariable Long id){
        return tareaService.cambiarEstado(id);
    }

}
