package restfull;

import auth.Cliente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.FurDTO;
import entities.SeccionFurDTO;
import models.Fur;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.FurService;

import java.util.List;

public class FurWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarFur() {
        try {
            JsonNode node = request().body().asJson();
            FurDTO fDto = mapper.convertValue(node, FurDTO.class);
            if (fDto.nombre != null && fDto.modelo != null && fDto.modelo.id != null &&
                    fDto.mina != null && fDto.mina.id != null){
                Fur foundMod = Fur.find.where()
                        .eq("nombre", fDto.nombre)
                        .eq("modelo_id", fDto.modelo.id)
                        .eq("mina_id", fDto.mina.id)
                        .eq("eliminado", false).findUnique();
                if(foundMod == null){
                    return ok(Json.toJson(FurService.registrarFur(fDto)));
                }else{
                    return ok(furExiste);
                }
            }else {
                return ok(faltanDatos);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result actualizarFur() {
        try {
            JsonNode node = request().body().asJson();
            Logger.info(node.toString());
            FurDTO fDto = mapper.convertValue(node, FurDTO.class);
            FurService.actualizarFur(fDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result actualizarSeccionesFur(Long id) {
        try {
            JsonNode node = request().body().asJson();
            List<SeccionFurDTO> sfDto = mapper.convertValue(node, new TypeReference<List<SeccionFurDTO>>(){});
            FurService.actualizarSeccionesFur(sfDto, id);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarFur(Long id) {
        try {
            return ok(Json.toJson(FurService.eliminarFur(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getFurs() {
        try {
            return ok(Json.toJson(FurService.getFurs()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getFurxId(Long id) {
        try {
            return ok(Json.toJson(FurService.getFurxId(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }
}
