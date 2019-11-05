package entities;


import models.Mina;

import java.util.Base64;

public class MinaDTO {

    public Long id;
    public String nombre;
    public String estado;
    public String logo;

    public MinaDTO(){}

    public MinaDTO(Mina mina) {
        this.id = mina.getId();
        this.nombre = mina.getNombre();
        this.estado = mina.getEstado();
        if (mina.getLogo() != null)
            this.logo = Base64.getEncoder().encodeToString(mina.getLogo());
    }

}
