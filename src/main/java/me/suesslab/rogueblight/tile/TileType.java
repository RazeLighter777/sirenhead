package me.suesslab.rogueblight.tile;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;

public abstract class TileType {

    protected abstract TileInstance getBody(Tile t);

    /**
     * Loads a tile with existing data
     * @param input
     * @param world
     * @return
     */
    public abstract Tile create(JsonObject input, IWorld world, Position pos);

    /**
     * Creates a tile without existing data
     * @param world
     * @return
     */
    public abstract Tile create(IWorld world, Position pos);

    /**
     *
     * @return The name of the type this TileType creates.
     */
    public abstract String getName();

}
