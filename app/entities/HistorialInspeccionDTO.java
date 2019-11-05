package entities;

import models.HistorialInspeccion;

import java.util.Map;

public class HistorialInspeccionDTO {
    public Long id;
    public String estado;
    public UsuarioBaseDTO usuario;
    public Map<String, Object> contenido;

    public HistorialInspeccionDTO(){};

    public HistorialInspeccionDTO(HistorialInspeccion hi){
        this.id = hi.getId();
        this.estado = hi.getEstado();
        if (hi.getUsuario() != null)
            this.usuario = new UsuarioBaseDTO(hi.getUsuario());
        this.contenido = hi.getContenido();
    }

    @Override
    public String toString() {
        return "HistorialInspeccionDTO{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", usuario=" + usuario +
                ", contenido=" + contenido +
                '}';
    }
}
