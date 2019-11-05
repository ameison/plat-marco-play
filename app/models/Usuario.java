package models;


import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "generator", sequenceName = "usuario_seq")
@Table(name = "usuarios")
public class Usuario extends BaseModel {

    public static final String
            INSPECTOR = "INS",
            SOPORTE = "SOP",
            SUPERVISOR = "SUP",
            ADMINISTRADOR = "ADM",
            SUPERVISOR_CLIENTE = "CLI";

    public static final String
            ACTIVO = "A",
            INACTIVO = "I";

    public Usuario(){}

    public Usuario(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "nombres")
    public String nombres;

    @Column(name = "apellidos")
    public String apellidos;

    @Column(name = "correo", unique = true)
    public String correo;

    @Column(name = "usuario", unique = true)
    public String usuario;

    @Column(name = "clave")
    public String clave;

    @Column(name = "tipo_usuario")
    public String tipoUsuario;

    @Column(name = "telefono")
    public String telefono;

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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Mina getMina() {
        return mina;
    }

    public void setMina(Mina mina) {
        this.mina = mina;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", clave='" + clave + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", telefono='" + telefono + '\'' +
                ", estado='" + estado + '\'' +
                ", mina='" + mina + '\'' +
                '}';
    }

    public static Model.Finder<Long, Usuario> find = new Model.Finder<>(Usuario.class);

    public static List<Usuario> getUsuarios(){
        return Usuario.find.where()
                .eq("eliminado", false)
                .eq("estado", ACTIVO).orderBy("fecha_creacion desc").findList();
    }

}