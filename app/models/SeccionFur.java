package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "seccion_fur_seq")
@Table(name = "seccion_fur")
public class SeccionFur extends BaseModel {

    public SeccionFur(){}

    public SeccionFur(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @Column(name = "fur")
    public Fur fur;

    @ManyToOne
    @Column(name = "seccion")
    public Seccion seccion;

    @Column(name = "orden")
    public Integer orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fur getFur() {
        return fur;
    }

    public void setFur(Fur fur) {
        this.fur = fur;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public static Model.Finder<Long, SeccionFur> find = new Model.Finder<>(SeccionFur.class);

    public static List<SeccionFur> getSeccionsFur(Long furId){
        return SeccionFur.find.where().eq("eliminado", false).eq("fur_id", furId).orderBy("orden").findList();
    }

    public static boolean enUso(Long seccionId){
        return SeccionFur.find.where().eq("eliminado", false).eq("seccion_id", seccionId).findList().size() > 0;
    }

}