package services;

import entities.SeccionDTO;
import entities.SeccionFurDTO;
import models.Modelo;
import models.Seccion;
import models.SeccionFur;
import play.Logger;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeccionService {

    public static SeccionDTO registrarSeccion(SeccionDTO seccionDTO){
        Seccion seccionDB = new Seccion();
        seccionDB.setNombre(seccionDTO.nombre);
        seccionDB.setEstado(Seccion.ACTIVO);
        seccionDB.setTipo(seccionDTO.tipo);
        seccionDB.setContenido(seccionDTO.contenido);
        if (seccionDTO.imagenAyuda != null)
            seccionDB.setImagenAyuda(DatatypeConverter.parseBase64Binary(seccionDTO.imagenAyuda));
        seccionDB.setReporteTemperatura(seccionDTO.reporteTemperatura);
        seccionDB.setModelo(new Modelo(seccionDTO.modelo.id));
        seccionDB.save();
        Logger.debug("Se grabo la categoria correctamente  :::  " + seccionDB.toString());
        return new SeccionDTO(seccionDB);
    }

    public static void actualizarSeccion(SeccionDTO seccionDTO){
        Seccion seccionDB = Seccion.find.byId(seccionDTO.id);
        if(seccionDB != null){
            if (seccionDTO.nombre != null)
                seccionDB.setNombre(seccionDTO.nombre);
            if (seccionDTO.estado != null)
                seccionDB.setEstado(seccionDTO.estado);
            if (seccionDTO.tipo != null)
                seccionDB.setTipo(seccionDTO.tipo);
            if (seccionDTO.contenido != null)
                seccionDB.setContenido(seccionDTO.contenido);
            if (seccionDTO.imagenAyuda != null)
                seccionDB.setImagenAyuda(DatatypeConverter.parseBase64Binary(seccionDTO.imagenAyuda));
            if (seccionDTO.modelo != null && seccionDTO.modelo.id != null)
                seccionDB.setModelo(new Modelo(seccionDTO.modelo.id));
            seccionDB.setReporteTemperatura(seccionDTO.reporteTemperatura);
            seccionDB.update();
        }
    }

    public static Map<String, Object> eliminarSeccion(Long id){
        Map<String, Object> map = new HashMap<>();
        Seccion seccionDB = Seccion.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(seccionDB != null){
            if (!SeccionFur.enUso(seccionDB.getId())){
                seccionDB.setEliminado(true);
                seccionDB.update();
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "seccion-en-uso");
            }
        }else{
            map.put("respuesta", "seccion-no-existe");
        }
        return map;
    }

    public static List<SeccionDTO> getSecciones(){
        List<SeccionDTO> seccionDTOs = new ArrayList<>();
        List<Seccion> seccions = Seccion.getSecciones();
        seccions.stream().forEach(seccion -> seccionDTOs.add(new SeccionDTO(seccion)));
        return seccionDTOs;
    }

    public static List<SeccionDTO> getSeccionesxModelo(Long modeloId){
        List<SeccionDTO> seccionDTOs = new ArrayList<>();
        List<Seccion> seccions = Seccion.getSeccionesxModelo(modeloId);
        seccions.stream().forEach(seccion -> seccionDTOs.add(new SeccionDTO(seccion)));
        return seccionDTOs;
    }

    public static List<SeccionFurDTO> getSeccionesFur(Long furId){
        List<SeccionFurDTO> seccionFurDTOs = new ArrayList<>();
        List<SeccionFur> seccionFurs = SeccionFur.getSeccionsFur(furId);
        seccionFurs.stream().forEach(seccionFur -> seccionFurDTOs.add(new SeccionFurDTO(seccionFur.getSeccion(), seccionFur.getOrden())));
        return seccionFurDTOs;
    }

    public static SeccionDTO getSeccion(Long seccionId){
        return new SeccionDTO(Seccion.find.byId(seccionId));
    }

}
