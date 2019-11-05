package services;

import entities.CategoriaDTO;
import models.Categoria;
import models.Modelo;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaService {

    public static CategoriaDTO registrarCategoria(CategoriaDTO categoria){
        Categoria categoriaDb = new Categoria();
        categoriaDb.setNombre(categoria.nombre);
        categoriaDb.setEstado(Categoria.ACTIVO);
        categoriaDb.save();
        Logger.debug("Se grabo la categoria correctamente  :::  " + categoriaDb.toString());
        return new CategoriaDTO(categoriaDb);
    }

    public static void actualizarCategoria(CategoriaDTO categoriaDTO){
        Categoria categoriaDb = Categoria.find.byId(categoriaDTO.id);
        if(categoriaDb != null){
            if (categoriaDTO.nombre != null)
                categoriaDb.setNombre(categoriaDTO.nombre);
            if (categoriaDTO.estado != null)
                categoriaDb.setEstado(categoriaDTO.estado);
            categoriaDb.update();
        }
    }

    public static Map<String, Object> eliminarCategoria(Long id){
        Map<String, Object> map = new HashMap<>();
        Categoria categoriaDb = Categoria.find.where().eq("eliminado", false).eq("id", id).findUnique();
        if(categoriaDb != null){
            if (!Modelo.categoriaExiste(categoriaDb.getId())){
                categoriaDb.setEliminado(true);
                categoriaDb.update();
                map.put("respuesta", "eliminacion-exitosa");
            }else{
                map.put("respuesta", "categoria-en-uso");
            }
        }else{
            map.put("respuesta", "categoria-no-existe");
        }
        return map;
    }

    public static List<CategoriaDTO> getCategorias(){
        List<CategoriaDTO> categoriaDTOs = new ArrayList<>();
        List<Categoria> categorias = Categoria.getCategorias();
        categorias.stream().forEach(categoria -> categoriaDTOs.add(new CategoriaDTO(categoria)));
        return categoriaDTOs;
    }

    public static CategoriaDTO getCategoria(Long categoriaId){
        return new CategoriaDTO(Categoria.find.byId(categoriaId));
    }

}
