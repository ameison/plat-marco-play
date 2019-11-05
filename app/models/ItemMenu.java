package models;


import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name="generator", sequenceName="items_menu_seq")
@Table(name="items_menu")
public class ItemMenu extends BaseModel {

    public ItemMenu(){}

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "item_id")
    public Long itemId;

    @Column(name = "title")
    public String titulo;

    @Column(name = "sref")
    public String sref;

    @Column(name = "icon")
    public String icono;

    @Column(name = "indice_posicion")
    public String indicePosicion;

    @Column(name = "tipo_usuario")
    public String tipoUsuario;

    public static Finder<Long, ItemMenu> find = new Finder<>(ItemMenu.class);

    public static List<ItemMenu> getCabecerasMenuxTipoUsuario(String tipoUsuario){
        return ItemMenu.find.where().eq("eliminado", false).eq("tipo_usuario", tipoUsuario).eq("item_id", null).orderBy("indice_posicion asc").findList();
    }

    public static List<ItemMenu> getItemsMenuxCabecera(Long itemId){
        return ItemMenu.find.where().eq("eliminado", false).eq("item_id", itemId).orderBy("indice_posicion asc").findList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSref() {
        return sref;
    }

    public void setSref(String sref) {
        this.sref = sref;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getIndicePosicion() {
        return indicePosicion;
    }

    public void setIndicePosicion(String indicePosicion) {
        this.indicePosicion = indicePosicion;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "ItemMenu{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", title='" + titulo + '\'' +
                ", sref='" + sref + '\'' +
                ", icon='" + icono + '\'' +
                ", indicePosicion='" + indicePosicion + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }
}