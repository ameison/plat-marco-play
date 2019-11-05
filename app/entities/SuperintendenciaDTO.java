package entities;


import models.Superintendencia;

public class SuperintendenciaDTO {

    public Long id;
    public String nombre;
    public String estado;
    public MinaDTO mina;

    public SuperintendenciaDTO(){}

    public SuperintendenciaDTO(Superintendencia superin) {
        this.id = superin.getId();
        this.nombre = superin.getNombre();
        this.estado = superin.getEstado();
        if (superin.getMina() != null){
            mina = new MinaDTO(superin.getMina());
        }
    }

}
