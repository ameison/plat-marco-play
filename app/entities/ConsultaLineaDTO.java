package entities;

/**
 * Created by antares on 08/11/16.
 */
public class ConsultaLineaDTO {

    public Long superintendenciaId;
    //public Long modeloId;
    public String tipo;

    public String fechaInicial;
    public String fechaFinal;

    public ConsultaLineaDTO(){}

    @Override
    public String toString() {
        return "ConsultaLineaDTO{" +
                ", superintendenciaId=" + superintendenciaId +
                //", modeloId=" + modeloId +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
