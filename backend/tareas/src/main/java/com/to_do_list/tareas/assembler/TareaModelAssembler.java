package com.to_do_list.tareas.assembler;

import com.to_do_list.tareas.controller.TareaController;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.model.Tarea;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TareaModelAssembler implements RepresentationModelAssembler<Tarea, TareaResponseDTO> {

    @Override
    public TareaResponseDTO toModel(Tarea tarea){

       TareaResponseDTO dto = new TareaResponseDTO();
       dto.setId(tarea.getId());
       dto.setNombre(tarea.getNombre());
       dto.setHoraInicio(tarea.getHoraInicio());
       dto.setHoraFinal(tarea.getHoraTermino());
       dto.setEstado(tarea.getEstado());

       dto.add(linkTo(methodOn(TareaController.class)
               .obtenerTodo()).withRel("tareas"));

       dto.add(linkTo(methodOn(TareaController.class)
               .cambiarEstado(tarea.getId())).withSelfRel());

       return dto;

    }


}
