package me.suesslab.rogueblight.basegame;

import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.ItemContainer;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.lib.IPlugin;

import java.util.Arrays;
import java.util.List;

public class BaseGamePlugin implements IPlugin {

    @Override
    public String getPluginName() {
        return null;
    }

    @Override
    public String getPluginConfigFile() {
        return null;
    }

    @Override
    public List<EntityType> getEntityTypes() {
        return Arrays.asList(new ItemContainer());
    }

    @Override
    public List<ItemType> getItemTypes() {
        return Arrays.asList(new Stone());
    }
}
