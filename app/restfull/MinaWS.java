package restfull;

import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.MinaDTO;
import models.Mina;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.MinaService;

public class MinaWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarMina() {
        try {
            JsonNode node = request().body().asJson();
            MinaDTO mDto = mapper.convertValue(node, MinaDTO.class);
            if (mDto.nombre != null){
                Mina foundMod = Mina.find.where().eq("nombre", mDto.nombre).eq("eliminado", false).findUnique();
                if(foundMod == null){
                    return ok(Json.toJson(MinaService.registrarMina(mDto)));
                }else{
                    return ok(minaExiste);
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
    public Result actualizarMina() {
        try {
            JsonNode node = request().body().asJson();
            MinaDTO mDto = mapper.convertValue(node, MinaDTO.class);
            MinaService.actualizarMina(mDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarMina(Long id) {
        try {
            return ok(Json.toJson(MinaService.eliminarMina(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getMinas() {
        try {
            return ok(Json.toJson(MinaService.getMinas()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getMina(Long id) {
        try {
            return ok(Json.toJson(MinaService.getMina(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
