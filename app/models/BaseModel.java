package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseModel extends Model{

    @Column(name = "eliminado", columnDefinition = "boolean default false")
    public boolean eliminado;

    @CreatedTimestamp
    @Column(name = "fecha_creacion")
    public Date fechaCreacion;

    @UpdatedTimestamp
    @Column(name = "fecha_actualizacion")
    public Date fechaActualizacion;

    @Column(name = "usuario_creador")
    public Usuario usuarioCreador;

    @Column(name = "usuario_modificador")
    public Usuario usuarioModificador;

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Usuario getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(Usuario usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }
}
