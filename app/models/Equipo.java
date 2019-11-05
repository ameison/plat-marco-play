package models;

import com.avaje.ebean.Model;
import entities.EquipoDTO;
import entities.SeguimientoDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "equipo", sequenceName = "equipo_seq")
@Table(name = "equipos")
public class Equipo extends BaseModel {

    public static String ACTIVO = "A", INACTIVO = "I";

    public Equipo(){}

    public Equipo(Long id){
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
    @Column(name = "superintendencia")
    public Superintendencia superintendencia;

    @ManyToOne
    @Column(name = "modelo")
    public Modelo modelo;

    //campos de configuracion para el grafico de seguimiento
    @Column(name = "dias_optimo")
    public Integer diasOptimo;

    @Column(name = "dias_critico")
    public Integer diasCritico;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Superintendencia getSuperintendencia() {
        return superintendencia;
    }

    public void setSuperintendencia(Superintendencia superintendencia) {
        this.superintendencia = superintendencia;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Integer getDiasCritico() {
        return diasCritico;
    }

    public void setDiasCritico(Integer diasCritico) {
        this.diasCritico = diasCritico;
    }

    public Integer getDiasOptimo() {
        return diasOptimo;
    }

    public void setDiasOptimo(Integer diasOptimo) {
        this.diasOptimo = diasOptimo;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", superintendencia=" + superintendencia +
                ", modelo=" + modelo +
                ", diasOptimo=" + diasOptimo +
                ", diasCritico=" + diasCritico +
                '}';
    }

    public static Model.Finder<Long, Equipo> find = new Model.Finder<>(Equipo.class);

    public static List<Equipo> getEquipos(){
        return Equipo.find.where()
                .eq("eliminado", false)
                .orderBy("nombre").findList();
    }

    public static List<Equipo> getEquiposXMinaId(Long id){
        return Equipo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("superintendencia.mina.id", id)
                .orderBy("nombre").findList();
    }

    public static List<Equipo> getEquiposXSuperintendenciaId(Long id){
        return Equipo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("superintendencia_id", id)
                .orderBy("nombre").findList();
    }

    public static List<Equipo> getEquipoXCategoriaYMina(SeguimientoDTO sDto){
        return Equipo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .in("modelo.categoria.id", sDto.categorias)
                .eq("superintendencia.mina.id", sDto.minaId)
                .orderBy("nombre").findList();
    }

    public static boolean equipoExiste(EquipoDTO equipoDTO){
        return Equipo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("superintendencia_id", equipoDTO.superintendencia.id)
                .eq("modelo_id", equipoDTO.modelo.id)
                .eq("nombre", equipoDTO.nombre).findRowCount() > 0;
    }

    public static boolean modeloExiste(Long modeloId){
        return Equipo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("modelo_id", modeloId).findRowCount() > 0;
    }

    public static boolean superintendenciaExiste(Long sId){
        return Equipo.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO)
                .eq("superintendencia_id", sId).findRowCount() > 0;
    }

}