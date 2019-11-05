package entities;


import models.Seccion;

import java.util.Base64;
import java.util.Map;

public class SeccionDTO {

    public Long id;
    public String nombre;
    public String estado;
    public String tipo;
    public Map<String, Object> contenido;
    public String imagenAyuda;
    public boolean reporteTemperatura;
    public ModeloDTO modelo;

    public SeccionDTO(){}

    public SeccionDTO(Seccion seccion) {
        this.id = seccion.getId();
        this.nombre = seccion.getNombre();
        this.estado = seccion.getEstado();
        this.tipo = seccion.getTipo();
        this.contenido = seccion.getContenido();
        if (seccion.getImagenAyuda() != null)
            this.imagenAyuda = Base64.getEncoder().encodeToString(seccion.getImagenAyuda());
        this.reporteTemperatura = seccion.isReporteTemperatura();
        if (seccion.getModelo() != null)
            this.modelo = new ModeloDTO(seccion.getModelo());
    }

}
