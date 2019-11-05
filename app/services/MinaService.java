package services;

import entities.MinaDTO;
import models.Mina;
import models.Superintendencia;
import play.Logger;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinaService {

    public static MinaDTO registrarMina(MinaDTO minaDTO){
        Mina minaDB = new Mina();
        minaDB.setNombre(minaDTO.nombre);
        minaDB.setEstado(Mina.ACTIVO);
        if (minaDTO.logo != null)
            minaDB.setLogo(DatatypeConverter.parseBase64Binary(minaDTO.logo));
        minaDB.save();
        Logger.debug("Se grabo la categoria correctamente  :::  " + minaDB.toString());
        return new MinaDTO(minaDB);
    }

    public static void actualizarMina(MinaDTO minaDTO){
        Mina minaDB = Mina.find.byId(minaDTO.id);
        if(minaDB != null){
            if (minaDTO.nombre != null)
                minaDB.setNombre(minaDTO.nombre);
            if (minaDTO.estado != null)
                minaDB.setEstado(minaDTO.estado);
            if (minaDTO.logo != null)
                minaDB.setLogo(DatatypeConverter.parseBase64Binary(minaDTO.logo));
            minaDB.update();
        }
    }

    public static Map<String, Object> eliminarMina(Long id){
        Map<String, Object> map = new HashMap<>();
        Mina mina = Mina.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(mina != null){
            if (!Superintendencia.minaExiste(mina.getId())){
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "mina-en-uso");
            }
            mina.setEliminado(true);
            mina.update();
        }else{
            map.put("respuesta", "mina-no-existe");
        }
        return map;
    }

    public static List<MinaDTO> getMinas(){
        List<MinaDTO> minaDTOs = new ArrayList<>();
        List<Mina> minas = Mina.getMinas();
        minas.stream().forEach(mina -> minaDTOs.add(new MinaDTO(mina)));
        return minaDTOs;
    }

    public static MinaDTO getMina(Long minaId){
        return new MinaDTO(Mina.find.byId(minaId));
    }

}
