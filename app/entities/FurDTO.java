package entities;


import models.Fur;

import java.util.ArrayList;
import java.util.List;

public class FurDTO {

    public Long id;
    public String nombre;
    public String estado;
    public ModeloDTO modelo;
    public MinaDTO mina;
    public List<SeccionFurDTO> secciones = new ArrayList<>();
    public String tipoInspeccion;

    public FurDTO(){}

    public FurDTO(Fur fur) {
        this.id = fur.getId();
        this.nombre = fur.getNombre();
        this.estado = fur.getEstado();
        if (fur.getModelo() != null)
            this.modelo = new ModeloDTO(fur.getModelo());
        if (fur.getMina() != null)
            this.mina = new MinaDTO(fur.getMina());
        this.tipoInspeccion = fur.getTipoInspeccion();
    }

    public FurDTO(Fur fur, List<SeccionFurDTO> seccionDTOs) {
        this.id = fur.getId();
        this.nombre = fur.getNombre();
        this.estado = fur.getEstado();
        if (fur.getModelo() != null)
            this.modelo = new ModeloDTO(fur.getModelo());
        if (fur.getMina() != null)
            this.mina = new MinaDTO(fur.getMina());
        this.tipoInspeccion = fur.getTipoInspeccion();
        this.secciones = seccionDTOs;
    }

    @Override
    public String toString() {
        return "FurDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", modelo=" + modelo +
                ", mina=" + mina+
                ", secciones=" + secciones +
                '}';
    }
}
