package me.suesslab.rogueblight.tile;

import com.google.gson.JsonObject;

public abstract class TileInstance {

    protected Tile tile;

    public TileInstance(Tile tile) {
        this.tile = tile;
    }

    public void save() {

    }

    public abstract double getOpacity();

    public abstract boolean isSolid();

    public abstract boolean isAir();

    public abstract double getFriction();

}
