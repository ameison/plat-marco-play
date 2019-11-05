package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.DbJsonB;
import com.fasterxml.jackson.annotation.JacksonInject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "historial_inspeccion_seq")
@Table(name = "historial_inspeccion")
public class HistorialInspeccion extends BaseModel {

    public static String
            INSPECCION = "INS",
            REVISION = "REV",
            APROBADO = "APR";

    public HistorialInspeccion(){}

    public HistorialInspeccion(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "estado", columnDefinition = "varchar(3)")
    public String estado;

    @ManyToOne
    @Column(name = "inspeccion")
    public Inspeccion inspeccion;

    @ManyToOne
    @Column(name = "usuario")
    public Usuario usuario;

    @DbJsonB
    @Column(name = "contenido")
    @JacksonInject
    public Map<String, Object> contenido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Inspeccion getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Map<String, Object> getContenido() {
        return contenido;
    }

    public void setContenido(Map<String, Object> contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "UsuarioInspeccion{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", inspeccion=" + inspeccion +
                ", usuario=" + usuario +
                ", contenido=" + contenido +
                '}';
    }

    public static Model.Finder<Long, HistorialInspeccion> find = new Model.Finder<>(HistorialInspeccion.class);

    public static List<HistorialInspeccion> getHistorialInspeccion(Long inspeccionId){
        return HistorialInspeccion.find.where()
                .eq("eliminado", false)
                .eq("inspeccion_id", inspeccionId)
                .orderBy("fecha_creacion desc").findList();
    }

    public static HistorialInspeccion getHistorialTipoAprobado(Long inspeccionId){
        return HistorialInspeccion.find.where()
                .eq("eliminado", false)
                .eq("inspeccion_id", inspeccionId)
                .eq("estado", APROBADO)
                .orderBy("fecha_creacion desc").setMaxRows(1).findUnique();

    }

    public static HistorialInspeccion getHistorialTipoInspeccion(Long inspeccionId){
        return HistorialInspeccion.find.where()
                .eq("eliminado", false)
                .eq("inspeccion_id", inspeccionId)
                .eq("estado", INSPECCION).setMaxRows(1).findUnique();
    }

    public static HistorialInspeccion getUltimoHistorialInspeccion(Long inspeccionId){
        List<String> expr = new ArrayList<>();
        expr.add(INSPECCION);
        expr.add(REVISION);
        expr.add(APROBADO);
        return HistorialInspeccion.find.where()
                .eq("eliminado", false)
                .eq("inspeccion_id", inspeccionId)
                .in("estado", expr)
                .orderBy("fecha_creacion desc").setMaxRows(1).findUnique();
    }

}