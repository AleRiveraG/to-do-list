package com.to_do_list.tareas.service;

import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.exception.TareaNotFoundException;
import com.to_do_list.tareas.model.Tarea;
import com.to_do_list.tareas.repository.TareaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaResponseDTO mapToDTO(Tarea tarea){
        return new TareaResponseDTO(
                tarea.getId(),
                tarea.getNombre(),
                tarea.getHoraInicio(),
                tarea.getHoraTermino(),
                tarea.getEstado()
        );
    }

    public List<TareaResponseDTO> obtenerTodo(){
        return tareaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TareaResponseDTO agregarTarea(TareaRequestDTO dto){
        Tarea tarea = new Tarea(null, dto.getNombre(), dto.getHoraInicio(), dto.getHoraTermino(), "PENDIENTE");
        return mapToDTO(tareaRepository.save(tarea));
    }

    @Transactional
    public TareaResponseDTO modificarTarea(TareaRequestDTO dto, Long id){
        Optional<Tarea> tarea = tareaRepository.findById(id);
        if(tarea.isPresent()){
            return tarea.map(existente ->{
                existente.setNombre(dto.getNombre());
                existente.setHoraInicio(dto.getHoraInicio());
                existente.setHoraTermino(dto.getHoraTermino());

            return mapToDTO(tareaRepository.save(existente));
            }).orElseThrow();
        }
        throw new TareaNotFoundException("Tarea con id "+id+"no encontrado");
    }

    @Transactional
    public void eliminarTarea(Long id){
        Optional<Tarea> tarea = tareaRepository.findById(id);

        if(tarea.isPresent()){
            tareaRepository.deleteById(id);
            return;
        }
        throw new TareaNotFoundException("Tarea con id "+id+"no encontrado");
    }

    @Transactional
    public TareaResponseDTO cambiarEstado(Long id){
        Optional<Tarea> tarea = tareaRepository.findById(id);

        if(tarea.isPresent()){
            return tarea.map(existente ->{
                existente.setEstado("COMPLETADO");
                return mapToDTO(tareaRepository.save(existente));
            }).orElseThrow();
        }
        throw new TareaNotFoundException("Tarea con id "+id+"no encontrado");
    }

}
