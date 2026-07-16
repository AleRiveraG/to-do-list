package com.to_do_list.tareas.controller;

import com.to_do_list.tareas.assembler.TareaModelAssembler;
import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.service.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tareas", description = "Operaciones relacionadas con tareas")
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    @Operation(summary = "Listar tareas", description = "Consulta de tareas disponibles")
    public ResponseEntity<?> obtenerTodo(){
        List<TareaResponseDTO> tareas = tareaService.obtenerTodo();

        CollectionModel<TareaResponseDTO> collectionModel = CollectionModel.of(tareas);
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Agregar tarea", description = "Registro de tarea validando sus datos de entrada")
    public ResponseEntity<?> agregarTarea(@Valid @RequestBody TareaRequestDTO dto){
        TareaResponseDTO tarea = tareaService.agregarTarea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar tarea", description = "Modificación de tarea por medio de su ID")
    public ResponseEntity<TareaResponseDTO> modificarTarea(@PathVariable Long id, @Valid @RequestBody TareaRequestDTO dto){
        TareaResponseDTO tarea = tareaService.modificarTarea(dto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Eliminar una tarea por medio de su ID")
    public ResponseEntity<?> eliminarTarea(@PathVariable Long id){
        tareaService.eliminarTarea(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Cambiar estado", description = "Cambiar estado de la tarea")
    public ResponseEntity<TareaResponseDTO> cambiarEstado(@PathVariable Long id){
        TareaResponseDTO tarea = tareaService.cambiarEstado(id);
        return ResponseEntity.status(HttpStatus.OK).body(tarea);

    }

}
