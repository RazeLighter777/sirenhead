package me.suesslab.rogueblight.basegame;

import me.suesslab.rogueblight.basegame.entity.Human;
import me.suesslab.rogueblight.basegame.item.Stone;
import me.suesslab.rogueblight.basegame.map.FlatBrickMapType;
import me.suesslab.rogueblight.basegame.tile.BrickTile;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.itemcontainer.ItemContainer;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.lib.IPlugin;
import me.suesslab.rogueblight.tile.TileMapType;
import me.suesslab.rogueblight.tile.TileType;

import java.util.Arrays;
import java.util.List;

public class BaseGamePlugin implements IPlugin {

    @Override
    public String getPluginName() {
        return "basePlugin";
    }

    @Override
    public String getPluginConfigFile() {
        return null;
    }

    @Override
    public List<EntityType> getEntityTypes() {
        return Arrays.asList(new ItemContainer(), new Human("human"));
    }

    @Override
    public List<ItemType> getItemTypes() {
        return Arrays.asList(new Stone());
    }

    @Override
    public List<TileType> getTileTypes() {
        return Arrays.asList(new BrickTile());
    }

    public List<TileMapType> getTileMapTypes() {
        return Arrays.asList(new FlatBrickMapType("flatBrickMap"));
    }


}
