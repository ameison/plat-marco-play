package entities;


import models.Equipo;

public class EquipoDTO {

    public Long id;
    public String nombre;
    public String estado;
    public ModeloDTO modelo;
    public SuperintendenciaDTO superintendencia;
    public Integer diasOptimo;
    public Integer diasCritico;

    public EquipoDTO(){}

    public EquipoDTO(Equipo equipo) {
        this.id = equipo.getId();
        this.nombre = equipo.getNombre();
        this.estado = equipo.getEstado();
        if (equipo.getModelo() != null){
            this.modelo = new ModeloDTO(equipo.getModelo());
        }
        if (equipo.getSuperintendencia() != null){
            this.superintendencia = new SuperintendenciaDTO(equipo.getSuperintendencia());
        }
        this.diasOptimo = equipo.getDiasOptimo();
        this.diasCritico = equipo.getDiasCritico();
    }

}
