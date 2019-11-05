package entities;

import models.Modelo;

public class ModeloDTO {

    public Long id;
    public String nombre;
    public String estado;
    public CategoriaDTO categoria;


    public ModeloDTO(){}

    public ModeloDTO(Modelo modelo) {
        this.id = modelo.getId();
        this.nombre = modelo.getNombre();
        this.estado = modelo.getEstado();
        if (modelo.categoria != null){
            this.categoria = new CategoriaDTO(modelo.categoria);
        }

    }

}
