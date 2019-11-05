package restfull;

import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.ModeloDTO;
import models.Modelo;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.ModeloService;

public class ModeloWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarModelo() {
        try {
            JsonNode node = request().body().asJson();
            ModeloDTO mDto = mapper.convertValue(node, ModeloDTO.class);
            if (mDto.nombre != null && mDto.categoria != null){
                Modelo foundMod = Modelo.find.where()
                        .eq("nombre", mDto.nombre)
                        .eq("categoria_id", mDto.categoria.id)
                        .eq("eliminado", false).findUnique();
                if(foundMod == null){
                    return ok(Json.toJson(ModeloService.registrarModelo(mDto)));
                }else{
                    return ok(modeloExiste);
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
    public Result actualizarModelo() {
        try {
            JsonNode node = request().body().asJson();
            ModeloDTO mDto = mapper.convertValue(node, ModeloDTO.class);
            ModeloService.actualizarModelo(mDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarModelo(Long id) {
        try {
            return ok(Json.toJson(ModeloService.eliminarModelo(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getModelos() {
        try {
            return ok(Json.toJson(ModeloService.getModelos()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getModelo(Long id) {
        try {
            return ok(Json.toJson(ModeloService.getModelo(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getModelosxCategoria(Long categoriaId) {
        try {
            return ok(Json.toJson(ModeloService.getModelosxCategoria(categoriaId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
