package me.suesslab.rogueblight.tile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;
import me.suesslab.rogueblight.world.World;

import java.util.Optional;

public abstract class TileMap {

    protected IWorld getWorld() {
        return world;
    }

    protected World world;

    public TileMap(World world) {
        this.world = world;
    }


    public abstract void loadMap(JsonArray json);

    public abstract JsonArray getMapData();

    public abstract Optional<Tile> getTileAtPosition(Position pos);

    /**
     *
     * @return The name of the map type
     */
    public abstract String getName();

}
