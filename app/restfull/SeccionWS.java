package restfull;

import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.SeccionDTO;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.SeccionService;

public class SeccionWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarSeccion() {
        try {
            JsonNode node = request().body().asJson();
            SeccionDTO sDto = mapper.convertValue(node, SeccionDTO.class);
            if (sDto.nombre != null && sDto.tipo != null && sDto.contenido != null){
                return ok(Json.toJson(SeccionService.registrarSeccion(sDto)));
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
    public Result actualizarSeccion() {
        try {
            JsonNode node = request().body().asJson();
            SeccionDTO mDto = mapper.convertValue(node, SeccionDTO.class);
            SeccionService.actualizarSeccion(mDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarSeccion(Long id) {
        try {
            return ok(Json.toJson(SeccionService.eliminarSeccion(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSecciones() {
        try {
            return ok(Json.toJson(SeccionService.getSecciones()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSeccionesxModelo(Long modeloId) {
        try {
            return ok(Json.toJson(SeccionService.getSeccionesxModelo(modeloId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSeccione(Long id) {
        try {
            return ok(Json.toJson(SeccionService.getSeccion(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
