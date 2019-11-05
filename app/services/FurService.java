package services;

import entities.FurDTO;
import entities.SeccionFurDTO;
import models.*;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurService {

    public static FurDTO registrarFur(FurDTO furDTO){
        Fur furDB = new Fur();
        furDB.setNombre(furDTO.nombre);
        furDB.setEstado(Fur.ACTIVO);
        furDB.setModelo(new Modelo(furDTO.modelo.id));
        furDB.setMina(new Mina(furDTO.mina.id));
        furDB.setTipoInspeccion(furDTO.tipoInspeccion);
        furDB.save();

        for (int i = 0; i < furDTO.secciones.size(); i++) {
            SeccionFur sfDb = new SeccionFur();
            sfDb.setSeccion(new Seccion(furDTO.secciones.get(i).id));
            sfDb.setFur(furDB);
            sfDb.setOrden(furDTO.secciones.get(i).orden);
            sfDb.save();
        }

        Logger.debug("Se grabo la categoria correctamente  :::  " + furDB.toString());
        return new FurDTO(furDB);
    }

    public static void actualizarFur(FurDTO furDTO){
        Logger.info(furDTO.toString());
        Fur furDB = Fur.find.byId(furDTO.id);
        if(furDB != null){
            if (furDTO.nombre != null)
                furDB.setNombre(furDTO.nombre);
            if (furDTO.estado != null)
                furDB.setEstado(furDTO.estado);
            if (furDTO.modelo != null && furDTO.modelo.id != null)
                furDB.setModelo(new Modelo(furDTO.modelo.id));
            if (furDTO.mina != null && furDTO.mina.id != null)
                furDB.setMina(new Mina(furDTO.mina.id));
            if (furDTO.secciones != null){
                List<SeccionFur> seccionFurs = SeccionFur.getSeccionsFur(furDTO.id);
                seccionFurs.stream().forEach(seccionFur -> seccionFur.delete());
                for (int i = 0; i < furDTO.secciones.size(); i++) {
                    SeccionFur sfDb = new SeccionFur();
                    sfDb.setSeccion(new Seccion(furDTO.secciones.get(i).id));
                    sfDb.setFur(furDB);
                    sfDb.setOrden(furDTO.secciones.get(i).orden);
                    sfDb.save();
                }
            }
            if (furDTO.tipoInspeccion != null)
                furDB.setTipoInspeccion(furDTO.tipoInspeccion);
            furDB.update();
        }
    }

    public static void actualizarSeccionesFur(List<SeccionFurDTO> seccionFurDTOs, Long furId){
        Logger.debug("Eliminando secciones pasadas");
        List<SeccionFur> seccionFurs = SeccionFur.getSeccionsFur(furId);
        if (seccionFurs != null){
            for (SeccionFur sf: seccionFurs) {
                sf.delete();
            }
        }
        Logger.debug("Registrando nuevas secciones");
        if (seccionFurDTOs != null){
            seccionFurDTOs.stream().forEach(seccionFurDTO -> {
                SeccionFur seccionFurDb = new SeccionFur();
                seccionFurDb.setFur(new Fur(furId));
                seccionFurDb.setSeccion(new Seccion(seccionFurDTO.id));
                seccionFurDb.setOrden(seccionFurDTO.orden);
                seccionFurDb.save();
            });
        }
    }

    public static Map<String, Object> eliminarFur(Long id){
        Map<String, Object> map = new HashMap<>();
        Fur furDB = Fur.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(furDB != null){
            List<SeccionFur> seccionFurs = SeccionFur.getSeccionsFur(furDB.getId());
            seccionFurs.stream().forEach(seccionFur -> {
                seccionFur.setEliminado(true);
                seccionFur.update();
            });
            furDB.setEliminado(true);
            furDB.update();
            map.put("respuesta", "eliminacion-exitosa");
        }else{
            map.put("respuesta", "fur-no-existe");
        }
        return map;
    }

    public static List<FurDTO> getFurs(){
        List<FurDTO> furDTOs = new ArrayList<>();
        List<Fur> furs = Fur.getFurs();
        furs.stream().forEach(fur -> {
            List<SeccionFurDTO> seccionDTOs = SeccionService.getSeccionesFur(fur.getId());
            furDTOs.add(new FurDTO(fur, seccionDTOs));
        });
        return furDTOs;
    }

    public static FurDTO getFurxId(Long id){
        List<SeccionFurDTO> seccionDTOs = SeccionService.getSeccionesFur(id);
        return new FurDTO(Fur.find.byId(id), seccionDTOs);
    }


}
