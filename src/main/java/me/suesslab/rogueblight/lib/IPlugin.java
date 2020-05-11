package me.suesslab.rogueblight.lib;

import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.ItemType;

import java.util.List;

public interface IPlugin {

    public abstract String getPluginName();

    public abstract String getPluginConfigFile();

    public abstract List<EntityType> getEntityTypes();

    public abstract List<ItemType> getItemTypes();

}
