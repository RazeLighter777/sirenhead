package me.suesslab.rogueblight.lib;

import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.tile.TileMapType;
import me.suesslab.rogueblight.tile.TileType;

import java.util.List;

public interface IPlugin {

    public abstract String getPluginName();

    public abstract String getPluginConfigFile();

    public abstract List<EntityType> getEntityTypes();

    public abstract List<ItemType> getItemTypes();

    public abstract List<TileType> getTileTypes();

    public abstract List<TileMapType> getTileMapTypes();


}
