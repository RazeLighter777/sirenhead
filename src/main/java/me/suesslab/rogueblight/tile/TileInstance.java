package me.suesslab.rogueblight.tile;

import com.google.gson.JsonObject;

public abstract class TileInstance {

    protected Tile tile;

    public TileInstance(Tile tile) {
        this.tile = tile;
    }

    public void save() {

    }
}
