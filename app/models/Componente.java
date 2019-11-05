package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.DbJsonB;
import com.fasterxml.jackson.annotation.JacksonInject;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "componente_seq")
@Table(name = "componentes")
public class Componente extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Componente(){}

    public Componente(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "estado", columnDefinition = "varchar(1) default 'A'")
    public String estado;

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

    public Map<String, Object> getContenido() {
        return contenido;
    }

    public void setContenido(Map<String, Object> contenido) {
        this.contenido = contenido;
    }

    public static Model.Finder<Long, Componente> find = new Model.Finder<>(Componente.class);

    public static List<Componente> getComponentes(){
        return Componente.find.where().eq("eliminado", false).findList();
    }

}