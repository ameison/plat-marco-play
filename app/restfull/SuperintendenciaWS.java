package restfull;

import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.SuperintendenciaDTO;
import models.Superintendencia;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.SuperintendenciaService;

public class SuperintendenciaWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarSuperintendencia() {
        try {
            JsonNode node = request().body().asJson();
            SuperintendenciaDTO siDto = mapper.convertValue(node, SuperintendenciaDTO.class);
            if (siDto.nombre != null && siDto.mina.id != null){
                Superintendencia foundMod = Superintendencia.find.where()
                        .eq("nombre", siDto.nombre)
                        .eq("mina_id", siDto.mina.id)
                        .eq("eliminado", false).findUnique();
                if(foundMod == null){
                    return ok(Json.toJson(SuperintendenciaService.registrarSuperintendencia(siDto)));
                }else{
                    return ok(superintendenciaExiste);
                }
            }else{
                return ok(faltanDatos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result actualizarSuperintendencia() {
        try {
            JsonNode node = request().body().asJson();
            SuperintendenciaDTO siDto = mapper.convertValue(node, SuperintendenciaDTO.class);
            SuperintendenciaService.actualizarSuperintendencia(siDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarSuperintendencia(Long id) {
        try {
            return ok(Json.toJson(SuperintendenciaService.eliminarSuperintendencia(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSuperintendencias() {
        try {
            return ok(Json.toJson(SuperintendenciaService.getSuperintendencias()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSuperintendencia(Long id) {
        try {
            return ok(Json.toJson(SuperintendenciaService.getSuperintendencia(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSuperintendenciasXMina(Long minaId) {
        try {
            return ok(Json.toJson(SuperintendenciaService.getSuperintendenciasXMina(minaId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
