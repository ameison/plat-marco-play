package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "modelo_seq")
@Table(name = "modelos")
public class Modelo extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Modelo(){}

    public Modelo(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "estado", columnDefinition = "varchar(1) default 'A'")
    public String estado;

    @ManyToOne
    @Column(name = "categoria")
    public Categoria categoria;

    public static Model.Finder<Long, Modelo> find = new Model.Finder<>(Modelo.class);

    public static List<Modelo> getModelos(){
        return Modelo.find.where().eq("eliminado", false).findList();
    }

    public static List<Modelo> getModelosxCategoria(Long categoriaId){
        return Modelo.find.where().eq("eliminado", false).eq("categoria_id", categoriaId).findList();
    }

    public static boolean categoriaExiste(Long categoriaId){
        return Modelo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("categoria_id", categoriaId).findRowCount() > 0;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}