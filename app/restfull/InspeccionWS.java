package restfull;

import auth.AuthService;
import auth.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import entities.ConsultaLineaDTO;
import entities.InspeccionDTO;
import entities.SeguimientoDTO;
import entities.UsuarioDTO;
import models.Usuario;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.InspeccionService;

public class InspeccionWS extends BaseController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Security.Authenticated(Cliente.class)
    public Result registrarInspeccion() {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            JsonNode node = request().body().asJson();

            Logger.info("Json recibido : ", node.toString());

            InspeccionDTO iDto = mapper.convertValue(node, InspeccionDTO.class);
            UsuarioDTO uDto = new UsuarioDTO();
            uDto.id = jwt.getLongClaim("id");
            iDto.responsable = uDto;

            if (iDto.tipo != null && iDto.equipo.id != null){
                return ok(Json.toJson(InspeccionService.registrarInspeccion(iDto)));
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
    public Result actualizarInspeccion() {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            JsonNode node = request().body().asJson();

            InspeccionDTO iDto = mapper.convertValue(node, InspeccionDTO.class);
            UsuarioDTO uDto = new UsuarioDTO();
            uDto.id = jwt.getLongClaim("id");
            iDto.responsable = uDto;

            String tipoUsuario = jwt.getStringClaim("tipo_usuario");

            if (iDto.hi.size() > 0){
                return ok(Json.toJson(InspeccionService.actualizarInspeccion(iDto, tipoUsuario)));
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
    public Result descargarInspecciones() {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesPendientes()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result descargarInspeccionesSync() {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            Long responsbleId = jwt.getLongClaim("id");
            Long minaId = jwt.getLongClaim("mina_id");
            return ok(Json.toJson(InspeccionService.descargarInspeccionesSync(responsbleId, minaId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result descargarInspeccionesFinalizadasResponsableId() {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            Long responsbleId = jwt.getLongClaim("id");
            return ok(Json.toJson(InspeccionService.descargarInspeccionesFinalizadasResponsableId(responsbleId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspecciones() {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            Logger.info("Id de Usuario" + jwt.getClaim("id").toString());
            return ok(Json.toJson(InspeccionService.getInspecciones()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getConfiguracionInicial() {
        try {
            return ok(Json.toJson(InspeccionService.getConfiguracionInicial()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionxId(Long id) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionxId(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionesxEstado(String estado) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesxEstado(estado)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionesxTipo(String tipo) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesxTipo(tipo)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionesxMina(Long id) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesxMina(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionesxModelo(Long id) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesxModelo(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result listaInspeccionesConsultaLinea() {
        try {
            JsonNode node = request().body().asJson();
            ConsultaLineaDTO cDto = mapper.convertValue(node, ConsultaLineaDTO.class);
            JWTClaimsSet jwt = AuthService.getPayload(request());
            Long usuarioId = jwt.getLongClaim("id");
            String tipoUsuario = jwt.getStringClaim("tipo_usuario");
            Long minaId = jwt.getLongClaim("mina_id");

            return ok(Json.toJson(InspeccionService.getInspeccionesxConsultaLinea(cDto, usuarioId, tipoUsuario, minaId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionesxCategoriaEquipo(Long id) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesxCategoriaEquipo(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionPdf(Long inspeccionId) {
        try {
            return ok(InspeccionService.generatePdfInspeccion(inspeccionId));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result eliminarInspeccion(Long inspeccionId) {
        try {
            JWTClaimsSet jwt = AuthService.getPayload(request());
            String tipoUsuario = jwt.getStringClaim("tipo_usuario");
            return ok(Json.toJson(InspeccionService.eliminarInspeccion(inspeccionId, tipoUsuario)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getInspeccionxEquipo(Long equipoId) {
        try {
            return ok(Json.toJson(InspeccionService.getInspeccionesxEquipo(equipoId)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEstadisticaInspeccionesScl() {
        try {

            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);

            JWTClaimsSet jwt = AuthService.getPayload(request());
            String tipoUsuario = jwt.getStringClaim("tipo_usuario");

            if (tipoUsuario.equals(Usuario.SUPERVISOR_CLIENTE) || tipoUsuario.equals(Usuario.INSPECTOR)){
                sDto.minaId = jwt.getLongClaim("mina_id");
            }

            return ok(Json.toJson(InspeccionService.getSCLUltimosDoceMeses(sDto)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

    @Security.Authenticated(Cliente.class)
    public Result getEstadisticaInspeccionCorrectiva() {
        try {
            JsonNode node = request().body().asJson();
            SeguimientoDTO sDto = mapper.convertValue(node, SeguimientoDTO.class);

            JWTClaimsSet jwt = AuthService.getPayload(request());
            String tipoUsuario = jwt.getStringClaim("tipo_usuario");

            if (tipoUsuario.equals(Usuario.SUPERVISOR_CLIENTE) || tipoUsuario.equals(Usuario.INSPECTOR)){
                sDto.minaId = jwt.getLongClaim("mina_id");
            }

            return ok(Json.toJson(InspeccionService.getCorrectivoUltimosDoceMeses(sDto)));
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug("ex:  "+ex);
            return notFound();
        }
    }

}
