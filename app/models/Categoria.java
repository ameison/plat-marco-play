package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "categoria_seq")
@Table(name = "categorias")
public class Categoria extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Categoria(Long id){
        this.id = id;
    }

    public Categoria(){}

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "estado", columnDefinition = "varchar(1) default 'A'")
    public String estado;

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

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    public static Model.Finder<Long, Categoria> find = new Model.Finder<>(Categoria.class);

    public static List<Categoria> getCategorias(){
        return Categoria.find.where().eq("eliminado", false).eq("estado", Categoria.ACTIVO).findList();
    }
}