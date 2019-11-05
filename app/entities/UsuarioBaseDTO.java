package entities;

import models.Usuario;

public class UsuarioBaseDTO {

    public Long id;
    public String nombres;
    public String apellidos;
    public String tipoUsuario;
    public String correo;
    public String usuario;
    public String telefono;
    public String estado;
    public MinaDTO mina;

    public UsuarioBaseDTO(){}

    public UsuarioBaseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.tipoUsuario = usuario.getTipoUsuario();

        this.correo = usuario.getCorreo();
        this.usuario = usuario.getUsuario();
        this.telefono = usuario.getTelefono();
        this.estado = usuario.getEstado();

        if (usuario.getMina() != null)
            this.mina = new MinaDTO(usuario.getMina());
    }

}
