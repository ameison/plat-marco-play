package services;

import entities.ModeloDTO;
import models.Categoria;
import models.Equipo;
import models.Fur;
import models.Modelo;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloService {

    public static ModeloDTO registrarModelo(ModeloDTO modeloDTO){
        Modelo modeloDB = new Modelo();
        modeloDB.setNombre(modeloDTO.nombre);
        modeloDB.setEstado(Modelo.ACTIVO);
        modeloDB.setCategoria(new Categoria(modeloDTO.categoria.id));

        modeloDB.save();
        Logger.debug("Se grabo la categoria correctamente  :::  " + modeloDB.toString());
        return new ModeloDTO(modeloDB);
    }

    public static void actualizarModelo(ModeloDTO modeloDTO){
        Modelo modeloDB = Modelo.find.byId(modeloDTO.id);
        if(modeloDB != null){
            if (modeloDTO.nombre != null)
                modeloDB.setNombre(modeloDTO.nombre);
            if (modeloDTO.estado != null)
                modeloDB.setEstado(modeloDTO.estado);
            if (modeloDTO.categoria != null && modeloDTO.categoria.id != null)
                modeloDB.setCategoria(new Categoria(modeloDTO.categoria.id));
            modeloDB.update();
        }
    }

    public static Map<String, Object> eliminarModelo(Long id){
        Map<String, Object> map = new HashMap<>();
        Modelo modelo = Modelo.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(modelo != null){
            if (!Fur.modeloExiste(modelo.getId()) && !Equipo.modeloExiste(modelo.getId())){
                modelo.setEliminado(true);
                modelo.update();
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "modelo-en-uso");
            }
        }else{
            map.put("respuesta", "modelo-no-existe");
        }
        return map;
    }

    public static List<ModeloDTO> getModelos(){
        List<ModeloDTO> modeloDTOs = new ArrayList<>();
        List<Modelo> modelos = Modelo.getModelos();
        modelos.stream().forEach(modelo -> modeloDTOs.add(new ModeloDTO(modelo)));
        return modeloDTOs;
    }

    public static List<ModeloDTO> getModelosxCategoria(Long categoriaId){
        List<ModeloDTO> modeloDTOs = new ArrayList<>();
        List<Modelo> modelos = Modelo.getModelosxCategoria(categoriaId);
        modelos.stream().forEach(modelo -> modeloDTOs.add(new ModeloDTO(modelo)));
        return modeloDTOs;
    }

    public static ModeloDTO getModelo(Long modeloId){
        return new ModeloDTO(Modelo.find.byId(modeloId));
    }

}
