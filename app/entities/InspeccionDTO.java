package entities;


import models.Inspeccion;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InspeccionDTO {

    public Long id;
    public String estado;
    public String tipo;
    public EquipoDTO equipo;
    public String ordenTrabajo;
    public String prioridad;
    public UsuarioBaseDTO responsable;
    public String resumenActividad;
    public boolean aprobadoSoporte;
    public boolean compartido;
    public Long horometro;
    public List<HistorialInspeccionDTO> hi = new ArrayList<>();
    public Long furId;
    public String fechaCreacion;
    public InspeccionDTO inspeccionCerrada;
    public String observaciones;
    public List<Map<String, Object>> usuariosInspeccion = new ArrayList<>();

    public InspeccionDTO(){}

    public InspeccionDTO(Inspeccion inspeccion) {
        this.id = inspeccion.getId();
        this.estado = inspeccion.getEstado();
        this.tipo = inspeccion.getTipo();
        if (inspeccion.getEquipo() != null)
            this.equipo = new EquipoDTO(inspeccion.getEquipo());
        this.ordenTrabajo = inspeccion.getOrdenTrabajo();
        this.prioridad = inspeccion.getPrioridad();
        if (inspeccion.getResponsable() != null)
            this.responsable = new UsuarioBaseDTO(inspeccion.getResponsable());
        this.resumenActividad = inspeccion.getResumenActividad();
        this.aprobadoSoporte = inspeccion.isAprobadoSoporte();
        this.compartido = inspeccion.isCompartido();
        this.horometro = inspeccion.getHorometro();
        if (inspeccion.getFur() != null)
            this.furId = inspeccion.getFur().id;
        if (inspeccion.getFechaCreacion() != null){
            this.fechaCreacion = Util.parseDateTime(inspeccion.getFechaCreacion());
        }
        if (inspeccion.getInspeccionCerrada() != null){
            this.inspeccionCerrada = new InspeccionDTO(inspeccion.getInspeccionCerrada());
        }
        this.observaciones = inspeccion.observaciones;
    }

    public InspeccionDTO(Inspeccion inspeccion, List<HistorialInspeccionDTO> uiDTOs) {
        this.id = inspeccion.getId();
        this.estado = inspeccion.getEstado();
        this.tipo = inspeccion.getTipo();
        if (inspeccion.getEquipo() != null)
            this.equipo = new EquipoDTO(inspeccion.getEquipo());
        this.ordenTrabajo = inspeccion.getOrdenTrabajo();
        this.prioridad = inspeccion.getPrioridad();
        if (inspeccion.getResponsable() != null)
            this.responsable = new UsuarioBaseDTO(inspeccion.getResponsable());
        this.resumenActividad = inspeccion.getResumenActividad();
        this.aprobadoSoporte = inspeccion.isAprobadoSoporte();
        this.compartido = inspeccion.isCompartido();
        this.horometro = inspeccion.getHorometro();
        if (uiDTOs != null)
            this.hi = uiDTOs;
        if (inspeccion.getFur() != null)
            this.furId = inspeccion.getFur().id;
        if (inspeccion.getFechaCreacion() != null){
            this.fechaCreacion = Util.parseDateTime(inspeccion.getFechaCreacion());
        }
        if (inspeccion.getInspeccionCerrada() != null){
            this.inspeccionCerrada = new InspeccionDTO(inspeccion.getInspeccionCerrada());
        }
        this.observaciones = inspeccion.observaciones;
    }

    public InspeccionDTO(Inspeccion inspeccion, List<HistorialInspeccionDTO> uiDTOs, List<Map<String, Object>> maps) {
        this.id = inspeccion.getId();
        this.estado = inspeccion.getEstado();
        this.tipo = inspeccion.getTipo();
        if (inspeccion.getEquipo() != null)
            this.equipo = new EquipoDTO(inspeccion.getEquipo());
        this.ordenTrabajo = inspeccion.getOrdenTrabajo();
        this.prioridad = inspeccion.getPrioridad();
        if (inspeccion.getResponsable() != null)
            this.responsable = new UsuarioBaseDTO(inspeccion.getResponsable());
        this.resumenActividad = inspeccion.getResumenActividad();
        this.aprobadoSoporte = inspeccion.isAprobadoSoporte();
        this.compartido = inspeccion.isCompartido();
        this.horometro = inspeccion.getHorometro();
        if (uiDTOs != null)
            this.hi = uiDTOs;
        if (inspeccion.getFur() != null)
            this.furId = inspeccion.getFur().id;
        if (inspeccion.getFechaCreacion() != null){
            this.fechaCreacion = Util.parseDateTime(inspeccion.getFechaCreacion());
        }
        if (inspeccion.getInspeccionCerrada() != null){
            this.inspeccionCerrada = new InspeccionDTO(inspeccion.getInspeccionCerrada());
        }
        this.observaciones = inspeccion.observaciones;
        this.usuariosInspeccion = maps;
    }

    @Override
    public String toString() {
        return "InspeccionDTO{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", equipo=" + equipo +
                ", ordenTrabajo='" + ordenTrabajo + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", responsable=" + responsable +
                ", resumenActividad='" + resumenActividad + '\'' +
                ", aprobadoSoporte=" + aprobadoSoporte +
                ", compartido=" + compartido +
                ", horometro=" + horometro +
                ", hi=" + hi +
                ", furId=" + furId +
                ", fechaCreacion=" + fechaCreacion +
                ", inspeccionCerrada=" + inspeccionCerrada +
                '}';
    }

}
