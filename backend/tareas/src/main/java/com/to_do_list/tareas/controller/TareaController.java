package com.to_do_list.tareas.controller;

import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<TareaResponseDTO>> obtenerTodo(){
        List<TareaResponseDTO> tareas = tareaService.obtenerTodo();
        return ResponseEntity.status(HttpStatus.OK).body(tareas);
    }

    @PostMapping
    public ResponseEntity<TareaResponseDTO> agregarTarea(@Valid @RequestBody TareaRequestDTO dto){
        TareaResponseDTO tarea = tareaService.agregarTarea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> modificarTarea(@PathVariable Long id, @Valid @RequestBody TareaRequestDTO dto){
        TareaResponseDTO tarea = tareaService.modificarTarea(dto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable Long id){
        tareaService.eliminarTarea(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> cambiarEstado(@PathVariable Long id){
        TareaResponseDTO tarea = tareaService.cambiarEstado(id);
        return ResponseEntity.status(HttpStatus.OK).body(tarea);

    }

}
