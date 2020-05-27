package me.suesslab.rogueblight.basegame.tile;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.tile.Tile;
import me.suesslab.rogueblight.tile.TileInstance;
import me.suesslab.rogueblight.tile.TileType;
import me.suesslab.rogueblight.world.IWorld;

public class BrickTile extends TileType {


    @Override
    protected TileInstance getBody(Tile t) {
        return new BrickTileInstance(t);
    }

    @Override
    public Tile create(JsonObject input, IWorld world, Position pos) {
        return new Tile(this, world, input, pos);
    }

    @Override
    public Tile create(IWorld world, Position pos) {
        return new Tile(this, world, new JsonObject(), pos);
    }

    @Override
    public String getName() {
        return "brickTile";
    }

    public static class BrickTileInstance extends TileInstance {

        public BrickTileInstance(Tile tile) {
            super(tile);
        }

        @Override
        public double getOpacity() {
            return 0;
        }

        @Override
        public boolean isSolid() {
            return false;
        }

        @Override
        public boolean isAir() {
            return false;
        }

        /**
         * Gets the friction of the tile
         * @return a number between 0 and 1 where 0 is perfectly sticky and 1 is perfectly smooth.
         */
        @Override
        public double getFriction() {
            return 0.7;
        }

    }

}
