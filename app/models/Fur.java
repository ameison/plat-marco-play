package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "fur_seq")
@Table(name = "furs")
public class Fur extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Fur(){}

    public Fur(Long id){
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
    @Column(name = "modelo")
    public Modelo modelo;

    @ManyToOne
    @Column(name = "mina")
    public Mina mina;

    @Column(name = "tipo_inspeccion", columnDefinition = "varchar(2)")
    public String tipoInspeccion;

    public static Model.Finder<Long, Fur> find = new Model.Finder<>(Fur.class);

    public static List<Fur> getFurs(){
        return Fur.find.where().eq("eliminado", false).findList();
    }

    public static boolean modeloExiste(Long modeloId){
        return Fur.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("modelo_id", modeloId).findRowCount() > 0;
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

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Mina getMina() {
        return mina;
    }

    public void setMina(Mina mina) {
        this.mina = mina;
    }

    public String getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(String tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    @Override
    public String toString() {
        return "Fur{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", modelo=" + modelo +
                ", mina=" + mina +
                ", tipoInspeccion='" + tipoInspeccion + '\'' +
                '}';
    }
}