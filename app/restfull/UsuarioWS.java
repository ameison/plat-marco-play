package restfull;

import auth.AuthService;
import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import entities.UsuarioDTO;
import models.Usuario;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.UsuarioService;

public class UsuarioWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarUsuario() {
        try {
            JsonNode node = request().body().asJson();
            UsuarioDTO uDto = mapper.convertValue(node, UsuarioDTO.class);
            if (uDto.tipoUsuario != null && uDto.nombres != null && uDto.apellidos != null
                    && uDto.correo != null && uDto.usuario != null && uDto.clave != null && uDto.telefono != null){
                Usuario foundUser = Usuario.find.where().eq("correo", uDto.correo).findUnique();
                if(foundUser == null){
                    return ok(Json.toJson(UsuarioService.registrarUsuario(uDto)));
                }else{
                    return ok(correoExiste);
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
    public Result actualizarDatosUsuario() {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            Long usuarioId = jwt.getLongClaim("id");
            JsonNode node = request().body().asJson();
            UsuarioDTO u = mapper.convertValue(node, UsuarioDTO.class);
            UsuarioService.actualizarUsuario(u, usuarioId);
            return ok(actualizacionExitosa);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarUsuario(Long id) {
        try {
            return ok(Json.toJson(UsuarioService.eliminarUsuario(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getUsuarios() {
        try {
            return ok(Json.toJson(UsuarioService.getUsuarios()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getUsuario(Long id) {
        try {
            return ok(Json.toJson(UsuarioService.getUsuario(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result descargarExcelUsuarios() {
        try {
            return ok(UsuarioService.generarExcelUsuarios());
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
