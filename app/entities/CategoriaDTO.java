package entities;


import models.Categoria;

public class CategoriaDTO {

    public Long id;
    public String nombre;
    public String estado;

    public CategoriaDTO(){}

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
        this.estado = categoria.getEstado();
    }

}
