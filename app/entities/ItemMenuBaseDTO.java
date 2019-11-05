package entities;

import models.ItemMenu;

import java.util.ArrayList;
import java.util.List;

public class ItemMenuBaseDTO {

    public Long id;
    public String title;
    public String sref;
    public String icon;
    public List<ItemMenuBaseDTO> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSref() {
        return sref;
    }

    public void setSref(String sref) {
        this.sref = sref;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ItemMenuBaseDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemMenuBaseDTO> items) {
        this.items = items;
    }

    public ItemMenuBaseDTO(){}

    public ItemMenuBaseDTO(ItemMenu itemMenu) {
        this.setId(itemMenu.id);
        this.setTitle(itemMenu.titulo);
        this.setSref(itemMenu.sref);
        this.setIcon(itemMenu.icono);

    }

    public ItemMenuBaseDTO(ItemMenu itemMenu, List<ItemMenu> items) {
        this.setId(itemMenu.id);
        this.setTitle(itemMenu.titulo);
        this.setSref(itemMenu.sref);
        this.setIcon(itemMenu.icono);
        if (items != null){
            items.stream().forEach(im -> this.items.add(new ItemMenuBaseDTO(im)));
        }
    }

    @Override
    public String toString() {
        return "ItemMenuBaseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sref='" + sref + '\'' +
                ", icon='" + icon + '\'' +
                ", items=" + items +
                '}';
    }
}