package services;

import entities.SuperintendenciaDTO;
import models.Equipo;
import models.Mina;
import models.Superintendencia;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperintendenciaService {

    public static SuperintendenciaDTO registrarSuperintendencia(SuperintendenciaDTO siDTO){
        Superintendencia siDB = new Superintendencia();
        siDB.setNombre(siDTO.nombre);
        siDB.setEstado(Superintendencia.ACTIVO);
        Mina mina = new Mina(siDTO.mina.id);
        siDB.setMina(mina);
        siDB.save();
        Logger.debug("Se grabo la superintendencia correctamente  :::  " + siDB.toString());
        return new SuperintendenciaDTO(siDB);
    }

    public static void actualizarSuperintendencia(SuperintendenciaDTO siDTO){
        Superintendencia siDB = Superintendencia.find.byId(siDTO.id);
        if(siDB != null){
            if (siDTO.nombre != null)
                siDB.setNombre(siDTO.nombre);
            if (siDTO.estado != null)
                siDB.setEstado(siDTO.estado);
            if (siDTO.mina != null && siDTO.mina.id != null){
                Mina mina = new Mina(siDTO.mina.id);
                siDB.setMina(mina);
            }
            siDB.update();
        }
    }

    public static Map<String, Object> eliminarSuperintendencia(Long id){
        Map<String, Object> map = new HashMap<>();
        Superintendencia siDB = Superintendencia.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(siDB != null){
            if (!Equipo.superintendenciaExiste(siDB.getId())){
                siDB.setEliminado(true);
                siDB.update();
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "superintendencia-en-uso");
            }
        }else{
            map.put("respuesta", "superintendencia-no-existe");
        }
        return map;
    }

    public static List<SuperintendenciaDTO> getSuperintendencias(){
        List<SuperintendenciaDTO> siDTOs = new ArrayList<>();
        List<Superintendencia> si = Superintendencia.getSuperintendencias();
        si.stream().forEach(superintendencia -> siDTOs.add(new SuperintendenciaDTO(superintendencia)));
        return siDTOs;
    }

    public static SuperintendenciaDTO getSuperintendencia(Long superintendenciaId){
        return new SuperintendenciaDTO(Superintendencia.find.byId(superintendenciaId));
    }

    public static List<SuperintendenciaDTO> getSuperintendenciasXMina(Long minaId){
        List<SuperintendenciaDTO> siDTOs = new ArrayList<>();
        List<Superintendencia> si = Superintendencia.getSuperintendenciasXMina(minaId);
        si.stream().forEach(superintendencia -> siDTOs.add(new SuperintendenciaDTO(superintendencia)));
        return siDTOs;
    }

}
