package entities;

import java.util.List;

public class MenuDTO {

    public List<ItemMenuBaseDTO> items;

    public MenuDTO(){}

    public MenuDTO(List<ItemMenuBaseDTO> items) {
        this.items = items;
    }

    public List<ItemMenuBaseDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemMenuBaseDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "items=" + items +
                '}';
    }
}