package entities;

import models.Usuario;

public class UsuarioDTO extends UsuarioBaseDTO {

    public String clave;

    public UsuarioDTO(){}

    public UsuarioDTO(Usuario usuario) {
        super(usuario);
        this.clave = usuario.getClave();
    }
}
