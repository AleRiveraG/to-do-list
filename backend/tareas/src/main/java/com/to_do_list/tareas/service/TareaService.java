package com.to_do_list.tareas.service;

import com.to_do_list.tareas.assembler.TareaModelAssembler;
import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.exception.TareaNotFoundException;
import com.to_do_list.tareas.model.Tarea;
import com.to_do_list.tareas.repository.TareaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TareaService {

    private final TareaRepository tareaRepository;
    private final TareaModelAssembler tareaModelAssembler;

    public List<TareaResponseDTO> obtenerTodo(){
        return tareaRepository.findAll()
                .stream()
                .map(tareaModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public TareaResponseDTO agregarTarea(TareaRequestDTO dto){
        Tarea tarea = new Tarea(null, dto.getNombre(), dto.getHoraInicio(), dto.getHoraTermino(), "PENDIENTE");
        log.info("Tarea creada con exito");
        return tareaModelAssembler.toModel(tareaRepository.save(tarea));
    }

    @Transactional
    public TareaResponseDTO modificarTarea(TareaRequestDTO dto, Long id){
        Optional<Tarea> tarea = tareaRepository.findById(id);
        if(tarea.isPresent()){
            log.info("Tarea encontrada con id {} encontrada con exito!",id);
            return tarea.map(existente ->{
                existente.setNombre(dto.getNombre());
                existente.setHoraInicio(dto.getHoraInicio());
                existente.setHoraTermino(dto.getHoraTermino());
            log.info("Tarea modificada con exito!");
            return tareaModelAssembler.toModel(tareaRepository.save(existente));
            }).orElseThrow();
        }
        log.warn("Tarea con id {} no encontrado",id);
        throw new TareaNotFoundException("Tarea con id "+id+"no encontrado");
    }

    @Transactional
    public void eliminarTarea(Long id){
        Optional<Tarea> tarea = tareaRepository.findById(id);

        if(tarea.isPresent()){
            log.info("Tarea encontrada con id {} encontrada con exito!",id);
            tareaRepository.deleteById(id);
            log.info("Tarea eliminada con exito!");
            return;
        }
        log.warn("Tarea con id {} no encontrado",id);
        throw new TareaNotFoundException("Tarea con id "+id+"no encontrado");
    }

    @Transactional
    public TareaResponseDTO cambiarEstado(Long id){
        Optional<Tarea> tarea = tareaRepository.findById(id);

        if(tarea.isPresent()){
            log.info("Tarea encontrada con id {} encontrada con exito",id);
            return tarea.map(existente ->{
                existente.setEstado("COMPLETADO");
                log.info("Estado cambiado con exito!");
                return tareaModelAssembler.toModel(tareaRepository.save(existente));
            }).orElseThrow();
        }
        log.warn("Tarea con id {} no encontrado",id);
        throw new TareaNotFoundException("Tarea con id "+id+"no encontrado");
    }

}
