package services;

import entities.ItemMenuBaseDTO;
import entities.MenuDTO;
import models.ItemMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuService {
    public static MenuDTO getMenuxTipoUsuario(String tipoUsaurio){
        List<ItemMenu> itemsCabeceras = ItemMenu.getCabecerasMenuxTipoUsuario(tipoUsaurio);
        List<ItemMenuBaseDTO> menuRetorno = new ArrayList<>();
        itemsCabeceras.stream().forEach(itemMenu -> {
            List<ItemMenu> items = ItemMenu.getItemsMenuxCabecera(itemMenu.id);
            menuRetorno.add(new ItemMenuBaseDTO(itemMenu, items));
        });
        return new MenuDTO(menuRetorno);
    }
}
