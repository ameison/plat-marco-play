package entities;


import models.Componente;

import java.util.Map;

public class ComponenteDTO {

    public Long id;
    public String nombre;
    public String estado;
    public Map<String, Object> contenido;

    public ComponenteDTO(){}

    public ComponenteDTO(Componente conponente) {
        this.id = conponente.getId();
        this.nombre = conponente.getNombre();
        this.estado = conponente.getEstado();
        this.contenido = conponente.getContenido();
    }

}
