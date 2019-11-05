package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antares on 12/10/16.
 */
public class SeguimientoDTO {

    public Long minaId;
    public List<Long> categorias = new ArrayList<>();
    public String tipoSeguimiento;

    public SeguimientoDTO(){}

    @Override
    public String toString() {
        return "SeguimientoDTO{" +
                "minaId=" + minaId +
                ", categorias=" + categorias +
                ", tipoSeguimiento=" + tipoSeguimiento +
                '}';
    }
}
