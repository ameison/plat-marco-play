package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "mina_seq")
@Table(name = "minas")
public class Mina extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Mina(){}

    public Mina(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "estado", columnDefinition = "varchar(1) default 'A'")
    public String estado;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    public byte[] logo;

    public static Model.Finder<Long, Mina> find = new Model.Finder<>(Mina.class);

    public static List<Mina> getMinas(){
        return Mina.find.where().eq("eliminado", false).eq("estado", Mina.ACTIVO).findList();
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Mina{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", logo=" + Arrays.toString(logo) +
                '}';
    }
}