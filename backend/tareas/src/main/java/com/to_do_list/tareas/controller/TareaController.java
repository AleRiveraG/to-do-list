package com.to_do_list.tareas.controller;

import com.to_do_list.tareas.assembler.TareaModelAssembler;
import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;
    private final TareaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<?> obtenerTodo(){
        List<TareaResponseDTO> tareas = tareaService.obtenerTodo();

        CollectionModel<TareaResponseDTO> collectionModel = CollectionModel.of(tareas);
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @PostMapping
    public ResponseEntity<?> agregarTarea(@Valid @RequestBody TareaRequestDTO dto){
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
