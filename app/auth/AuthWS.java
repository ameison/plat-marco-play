package auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import entities.UsuarioDTO;
import models.Usuario;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.MenuService;
import views.html.main;

import java.util.HashMap;
import java.util.Map;

//https://github.com/sahat/satellizer
public class AuthWS extends Controller {

    private static ObjectMapper mapper = new ObjectMapper();

    public Result login() {
        Map<String, Object> mapToken = new HashMap<>();
        JsonNode node = request().body().asJson();
        UsuarioDTO uLogin = mapper.convertValue(node, UsuarioDTO.class);
        Logger.info(uLogin.toString());
        Usuario foundUser = Usuario.find.where().eq("usuario", uLogin.usuario).findUnique();
        if (foundUser != null && (foundUser.getTipoUsuario().equals(Usuario.ADMINISTRADOR) ||
                foundUser.getTipoUsuario().equals(Usuario.SUPERVISOR) || foundUser.getTipoUsuario().equals(Usuario.SUPERVISOR_CLIENTE) ||
                foundUser.getTipoUsuario().equals(Usuario.SOPORTE) || foundUser.getTipoUsuario().equals(Usuario.INSPECTOR) )) {
            if (uLogin.usuario != null && !uLogin.usuario.trim().isEmpty()) {
                if (uLogin.clave != null && !uLogin.clave.trim().isEmpty()) {
                    if (AuthService.checkPassword(uLogin.clave, foundUser.clave)) {
                        Logger.debug("Se ha autenticado!");
                        try {
                            AuthToken token = AuthService.createToken(request().remoteAddress(), foundUser);
                            mapToken.put("token", token.getToken());
                            mapToken.put("usuario_nombres", foundUser.getNombres());
                            mapToken.put("usuario_apellidos", foundUser.getApellidos());
                            mapToken.put("usuario_tipo", foundUser.getTipoUsuario());
                            mapToken.put("mina_id", foundUser.getMina().getId());
                            mapToken.put("menu", Json.toJson(MenuService.getMenuxTipoUsuario(foundUser.getTipoUsuario())).toString());
                            mapToken.put("respuesta", "login-exitoso");
                        } catch (JOSEException je) {
                            Logger.error("Ex: " + je);
                            mapToken.put("respuesta", "usuario-no-existe");
                        }
                    }else{
                        mapToken.put("respuesta", "pass-no-coincide");
                    }
                }else{
                    mapToken.put("respuesta", "pass-nulo");
                }
            }else{
                mapToken.put("respuesta", "usuario-nulo");
            }
        } else {
            Logger.warn("No se encontro el usuario");
            mapToken.put("respuesta", "usuario-no-existe");
        }
        return ok(Json.toJson(mapToken));
    }

    public Result loginMovil() {
        Logger.debug("loginMovil");
        Map<String, Object> mapToken = new HashMap<>();
        JsonNode node = request().body().asJson();
        UsuarioDTO uLogin = mapper.convertValue(node, UsuarioDTO.class);
        Logger.info(uLogin.toString());
        Usuario foundUser = Usuario.find.where().eq("usuario", uLogin.usuario).findUnique();

        if (foundUser != null && (foundUser.getTipoUsuario().equals(Usuario.ADMINISTRADOR) ||
                foundUser.getTipoUsuario().equals(Usuario.SUPERVISOR) || foundUser.getTipoUsuario().equals(Usuario.INSPECTOR) ||
                foundUser.getTipoUsuario().equals(Usuario.SOPORTE))) {

            if (uLogin.usuario != null && !uLogin.usuario.trim().isEmpty()) {

                if (uLogin.clave != null && !uLogin.clave.trim().isEmpty()) {

                    if (AuthService.checkPassword(uLogin.clave, foundUser.clave)) {
                        Logger.debug("Se ha autenticado!");
                        try {
                            AuthToken token = AuthService.createToken(request().remoteAddress(), foundUser);
                            mapToken.put("token", token.getToken());
                            mapToken.put("usuario_nombres", foundUser.getNombres());
                            mapToken.put("usuario_apellidos", foundUser.getApellidos());
                            mapToken.put("usuario_telefono", foundUser.getTelefono());
                            mapToken.put("usuario_correo", foundUser.getCorreo());
                            mapToken.put("usuario_tipo", foundUser.getTipoUsuario());
                            mapToken.put("mina_id", foundUser.getMina().getId());
                            mapToken.put("respuesta", "login-exitoso");
                        } catch (JOSEException je) {
                            Logger.error("Ex: " + je);
                        }
                    }else{
                        mapToken.put("respuesta", "pass-no-coincide");
                    }
                }else{
                    mapToken.put("respuesta", "pass-nulo");
                }
            }else{
                mapToken.put("respuesta", "usuario-nulo");
            }
        } else {
            Logger.warn("No se encontro el usuario");
            mapToken.put("respuesta", "usuario-no-existe");
        }
        return ok(Json.toJson(mapToken));
    }

    public Result logout() {
        Logger.warn("Sesion cerrada");
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", null);
        return ok(Json.toJson(mapToken));
    }

    public Result index() {
        return ok(main.render());
    }

}

