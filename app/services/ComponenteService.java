package services;

import entities.ComponenteDTO;
import models.Componente;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class ComponenteService {

    public static ComponenteDTO registrarComponente(ComponenteDTO componenteDTO){
        Componente componenteDb = new Componente();
        componenteDb.setNombre(componenteDTO.nombre);
        componenteDb.setEstado(Componente.ACTIVO);
        componenteDb.setContenido(componenteDTO.contenido);
        componenteDb.save();
        Logger.debug("Se grabo el componente correctamente  :::  " + componenteDb.toString());
        return new ComponenteDTO(componenteDb);
    }

    public static void actualizarComponente(ComponenteDTO componenteDTO){
        Componente componenteDb = Componente.find.byId(componenteDTO.id);
        if(componenteDb != null){
            if (componenteDTO.nombre != null)
                componenteDb.setNombre(componenteDTO.nombre);
            if (componenteDTO.estado != null)
                componenteDb.setEstado(componenteDTO.estado);
            if (componenteDTO.contenido != null)
                componenteDb.setContenido(componenteDTO.contenido);
            componenteDb.update();
        }
    }

    public static void eliminarComponente(Long id){
        Componente componenteDb = Componente.find.byId(id);
        if(componenteDb != null){
            componenteDb.setEliminado(true);
            componenteDb.update();
        }
    }

    public static List<ComponenteDTO> getComponentes(){
        List<ComponenteDTO> componenteDTOs = new ArrayList<>();
        List<Componente> componentes = Componente.getComponentes();
        componentes.stream().forEach(componente -> componenteDTOs.add(new ComponenteDTO(componente)));
        return componenteDTOs;
    }

    public static ComponenteDTO getComponente(Long componenteId){
        return new ComponenteDTO(Componente.find.byId(componenteId));
    }

}
