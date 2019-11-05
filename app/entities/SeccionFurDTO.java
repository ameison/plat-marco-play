package entities;


import models.Seccion;

import java.util.Base64;
import java.util.Map;

public class SeccionFurDTO {

    public Long id;
    public String nombre;
    public String estado;
    public String tipo;
    public int orden;
    public Map<String, Object> contenido;
    public String imagenAyuda;

    public SeccionFurDTO(){}

    public SeccionFurDTO(Seccion seccion, int orden) {
        this.id = seccion.getId();
        this.nombre = seccion.getNombre();
        this.estado = seccion.getEstado();
        this.tipo = seccion.getTipo();
        this.contenido = seccion.getContenido();
        this.orden = orden;
        if (seccion.getImagenAyuda() != null)
            this.imagenAyuda = Base64.getEncoder().encodeToString(seccion.getImagenAyuda());
    }

    @Override
    public String toString() {
        return "SeccionFurDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", orden=" + orden +
                ", contenido=" + contenido +
                ", imagenAyuda='" + imagenAyuda + '\'' +
                '}';
    }
}
