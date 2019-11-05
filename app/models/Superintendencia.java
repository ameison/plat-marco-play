package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "superintendencia_seq")
@Table(name = "superintendencias")
public class Superintendencia extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Superintendencia(){}

    public Superintendencia(Long id){
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
    @Column(name = "mina")
    public Mina mina;

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

    public Mina getMina() {
        return mina;
    }

    public void setMina(Mina mina) {
        this.mina = mina;
    }

    @Override
    public String toString() {
        return "Superintendencia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", mina=" + mina +
                '}';
    }

    public static Model.Finder<Long, Superintendencia> find = new Model.Finder<>(Superintendencia.class);

    public static List<Superintendencia> getSuperintendencias(){
        return Superintendencia.find.where()
                .eq("eliminado", false).findList();
    }

    public static List<Superintendencia> getSuperintendenciasXMina(Long minaId){
        return Superintendencia.find.where()
                .eq("eliminado", false)
                .eq("mina_id", minaId)
                .eq("estado", ACTIVO).findList();
    }

    public static boolean minaExiste(Long minaId){
        return Superintendencia.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("mina_id", minaId).findRowCount() > 0;
    }
}