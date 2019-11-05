package restfull;

import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CategoriaDTO;
import models.Categoria;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.CategoriaService;

public class CategoriaWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarCategoria() {
        try {
            JsonNode node = request().body().asJson();
            CategoriaDTO cDto = mapper.convertValue(node, CategoriaDTO.class);
            if (cDto.nombre != null){
                Categoria foundCat = Categoria.find.where().eq("nombre", cDto.nombre).eq("eliminado", false).findUnique();
                if(foundCat == null){
                    return ok(Json.toJson(CategoriaService.registrarCategoria(cDto)));
                }else{
                    return ok(categoriaExiste);
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
    public Result actualizarCategoria() {
        try {
            JsonNode node = request().body().asJson();
            CategoriaDTO cDto = mapper.convertValue(node, CategoriaDTO.class);
            CategoriaService.actualizarCategoria(cDto);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarCategoria(Long id) {
        try {
            return ok(Json.toJson(CategoriaService.eliminarCategoria(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getCategorias() {
        try {
            return ok(Json.toJson(CategoriaService.getCategorias()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getCategoria(Long id) {
        try {
            return ok(Json.toJson(CategoriaService.getCategoria(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
