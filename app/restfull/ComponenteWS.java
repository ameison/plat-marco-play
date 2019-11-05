package restfull;

import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.ComponenteDTO;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.ComponenteService;

public class ComponenteWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarComponente() {
        try {
            JsonNode node = request().body().asJson();
            ComponenteDTO cDto = mapper.convertValue(node, ComponenteDTO.class);
            if (cDto.nombre != null && cDto.contenido != null){
                return ok(Json.toJson(ComponenteService.registrarComponente(cDto)));
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
    public Result actualizarComponente() {
        try {
            JsonNode node = request().body().asJson();
            ComponenteDTO cDto = mapper.convertValue(node, ComponenteDTO.class);
            ComponenteService.actualizarComponente(cDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarComponente(Long id) {
        try {
            ComponenteService.eliminarComponente(id);
            return ok(eliminacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getComponentes() {
        try {
            return ok(Json.toJson(ComponenteService.getComponentes()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getComponente(Long id) {
        try {
            return ok(Json.toJson(ComponenteService.getComponente(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
