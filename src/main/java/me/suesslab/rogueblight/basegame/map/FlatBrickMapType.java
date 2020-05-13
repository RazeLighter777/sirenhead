package me.suesslab.rogueblight.basegame.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.tile.Tile;
import me.suesslab.rogueblight.tile.TileMap;
import me.suesslab.rogueblight.tile.TileMapType;
import me.suesslab.rogueblight.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FlatBrickMapType extends TileMapType {

    public FlatBrickMapType(String name) {
        super(name);
    }

    @Override
    public TileMap create(World world) {
        return new FlatBrickMap(world);
    }

    @Override
    public TileMap create(World world, JsonArray data) {
        TileMap newMap = new FlatBrickMap(world);
        newMap.loadMap(data);
        return newMap;
    }


    public static class FlatBrickMap extends TileMap {

        private HashMap<Position, Tile> tiles;

        public FlatBrickMap(World world) {
            super(world);
            tiles = new HashMap<>();
        }

        @Override
        public void loadMap(JsonArray json) {
            tiles.clear();
            for (JsonElement tileData : json) {
                Position position = new Position(tileData.getAsJsonArray().get(0).getAsJsonArray());
                Optional<Tile> tile = world.loadTile(tileData.getAsJsonArray().get(1).getAsJsonObject());
                if (tile.isPresent()) {
                    tiles.put(position, tile.get());
                }
            }
        }

        @Override
        public JsonArray getMapData() {
            JsonArray mapData = new JsonArray();
            for (Map.Entry<Position, Tile> entry : tiles.entrySet()) {
                entry.getValue().save();
                JsonArray tileEntry = new JsonArray();
                tileEntry.add(entry.getKey().getJSON());
                tileEntry.add(entry.getValue().getData());
                mapData.add(tileEntry);
            }
            return mapData;
        }

        @Override
        public Optional<Tile> getTileAtPosition(Position pos) {
            if (tiles.containsKey(pos)) {
                return Optional.of(tiles.get(pos));
            }
            Optional<Tile> newTile = world.createTile("brickTile", pos);
            if (newTile.isPresent()) {
                tiles.put(pos, newTile.get());
            }
            return newTile;
        }

        @Override
        public String getName() {
            return "flatBrickMap";
        }
    }
}
