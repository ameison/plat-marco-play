package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.DbJsonB;
import com.fasterxml.jackson.annotation.JacksonInject;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "seccion_seq")
@Table(name = "secciones")
public class Seccion extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public static String CUERPO = "CU";

    public Seccion(){}

    public Seccion(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "estado", columnDefinition = "varchar(1) default 'A'")
    public String estado;

    @Column(name = "tipo", columnDefinition = "varchar(2)")
    public String tipo;

    @DbJsonB
    @Column(name = "contenido")
    @JacksonInject
    public Map<String, Object> contenido;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "imagen_ayuda")
    public byte[] imagenAyuda;

    @Column(name = "reporte_temperatura", columnDefinition = "boolean default false")
    public boolean reporteTemperatura;

    @ManyToOne
    @Column(name = "modelo")
    public Modelo modelo;

    public static Model.Finder<Long, Seccion> find = new Model.Finder<>(Seccion.class);

    public static List<Seccion> getSecciones(){
        return Seccion.find.where().eq("eliminado", false).orderBy("modelo.nombre asc").findList();
    }

    public static List<Seccion> getSeccionesxModelo(Long modeloId){
        return Seccion.find.where()
                .eq("eliminado", false)
                .eq("modelo_id", modeloId).findList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Map<String, Object> getContenido() {
        return contenido;
    }

    public void setContenido(Map<String, Object> contenido) {
        this.contenido = contenido;
    }

    public byte[] getImagenAyuda() {
        return imagenAyuda;
    }

    public void setImagenAyuda(byte[] imagenAyuda) {
        this.imagenAyuda = imagenAyuda;
    }

    public boolean isReporteTemperatura() {
        return reporteTemperatura;
    }

    public void setReporteTemperatura(boolean reporteTemperatura) {
        this.reporteTemperatura = reporteTemperatura;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Seccion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", contenido=" + contenido +
                ", imagenAyuda=" + Arrays.toString(imagenAyuda) +
                ", reporteTemperatura=" + reporteTemperatura +
                '}';
    }
}