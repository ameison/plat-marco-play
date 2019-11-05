package restfull;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;

class BaseController extends Controller {

    ObjectNode registroExitoso = Json.newObject();
    ObjectNode actualizacionExitosa = Json.newObject();
    ObjectNode eliminacionExitosa = Json.newObject();
    ObjectNode falseResponse = Json.newObject();
    ObjectNode correoExiste = Json.newObject();
    ObjectNode categoriaExiste = Json.newObject();
    ObjectNode modeloExiste = Json.newObject();
    ObjectNode minaExiste = Json.newObject();
    ObjectNode furExiste = Json.newObject();
    ObjectNode superintendenciaExiste = Json.newObject();
    ObjectNode faltanDatos = Json.newObject();

    BaseController(){
        registroExitoso.put("respuesta", "registro-exitoso");
        actualizacionExitosa.put("respuesta", "actualizacion-exitosa");
        eliminacionExitosa.put("respuesta", "eliminacion-exitosa");
        falseResponse.put("respuesta", "registro-fallido");
        correoExiste.put("respuesta", "correo-existe");
        categoriaExiste.put("respuesta", "categoria-existe");
        modeloExiste.put("respuesta", "equipo-existe");
        minaExiste.put("respuesta", "mina-existe");
        furExiste.put("respuesta", "fur-existe");
        superintendenciaExiste.put("respuesta", "superintendencia-existe");
        faltanDatos.put("respuesta", "faltan-datos");
    }

}
