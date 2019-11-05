package restfull;

import auth.AuthService;
import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import entities.EquipoDTO;
import entities.SeguimientoDTO;
import models.Usuario;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.EquipoService;

public class EquipoWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarEquipo() {
        try {
            JsonNode node = request().body().asJson();
            EquipoDTO eDto = mapper.convertValue(node, EquipoDTO.class);
            if (eDto.nombre != null && eDto.superintendencia != null && eDto.superintendencia.id != null &&
                    eDto.modelo != null && eDto.modelo.id != null){
                return ok(Json.toJson(EquipoService.registrarEquipo(eDto)));
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
    public Result actualizarEquipo() {
        try {
            JsonNode node = request().body().asJson();
            EquipoDTO eDto = mapper.convertValue(node, EquipoDTO.class);
            return ok(Json.toJson(EquipoService.actualizarEquipo(eDto)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarEquipo(Long id) {
        try {
            return ok(Json.toJson(EquipoService.eliminarEquipo(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEquipos() {
        try {
            return ok(Json.toJson(EquipoService.getEquipos()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEquiposXMinaId(Long id) {
        try {
            return ok(Json.toJson(EquipoService.getEquiposxMinaId(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEquiposXSuperintendenciaId(Long id) {
        try {
            return ok(Json.toJson(EquipoService.getEquiposXSuperintendenciaId(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEquipo(Long id) {
        try {
            return ok(Json.toJson(EquipoService.getEquipo(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getSeguimientoEquipos() {
        try {
            Logger.info("******************************************");
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);
            return ok(Json.toJson(EquipoService.getSeguimientoEquipos(sDto)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getMantenimientoCorrectivoEquipos() {
        try {
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);
            return ok(Json.toJson(EquipoService.getMantenimientoCorrectivoEquipos(sDto)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getMonitoreoTemperaturaEquipos() {
        try {
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);
            return ok(Json.toJson(EquipoService.getMonitoreoTemperaturaEquipos(sDto)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result descargarExcelSeguimientoPreventivo() {
        try {
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);
            return ok(EquipoService.generarExcelSeguimientoPreventivo(sDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result descargarMonitoreoTemperatura(Long equipoId){
        try {
            return ok(EquipoService.generarExcelMonitoreoTemperatura(equipoId));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result generarExcelHistorialCorrectivo(){
        try {
            return ok(EquipoService.generarExcelHistorialCorrectivo());
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEstadisticaSclDias(){
        try {
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);

            JWTClaimsSet jwt = AuthService.getPayload(request());
            String tipoUsuario = jwt.getStringClaim("tipo_usuario");

            if (tipoUsuario.equals(Usuario.SUPERVISOR_CLIENTE) || tipoUsuario.equals(Usuario.INSPECTOR)){
                sDto.minaId = jwt.getLongClaim("mina_id");
            }

            return ok(Json.toJson(EquipoService.getSclDiasUltimaInspeccion(sDto)));

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result descargarMantenimientoCorrectivo(){
        try {
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);
            return ok(EquipoService.generarExcelMantenimientoCorrectivo(sDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }
}
