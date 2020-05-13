package me.suesslab.rogueblight.tile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.world.World;

public abstract class TileMapType {

    public String getName() {
        return name;
    }

    String name;

    protected TileMapType(String name) {
        this.name = name;
    }

    public abstract TileMap create(World world);

    public abstract TileMap create(World world, JsonArray data);

}
