package me.suesslab.rogueblight.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.literary.StringLog;
import me.suesslab.rogueblight.tile.Tile;
import me.suesslab.rogueblight.tile.TileMap;
import me.suesslab.rogueblight.tile.TileMapType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class World implements IWorld {

    private LevelManager levelManager;

    private final Object lock = new Object();

    public JsonObject getWorldData() {
        return worldData;
    }

    private JsonObject worldData;
    private Map<UUID, Entity> entities;
    private TileMap map;

    public StringLog getStringLog() {
        return stringLog;
    }

    private StringLog stringLog;

    @Override
    public long getTick() {
        return tick;
    }

    @Override
    public boolean removeEntity(UUID uuid) {
        if (getEntityWithUUID(uuid).isPresent()) {
            synchronized (lock) {
                getEntities().remove(uuid);
            }
            return true;
        }
        return false;
    }

    private long tick;

    private void init(LevelManager levelManager) {
        this.levelManager = levelManager;
        levelManager.setWorld(this);
        entities = new ConcurrentHashMap<>();
        stringLog = new StringLog(Integer.parseInt(ResourceBundle.getBundle("Game").getString("worldLogSize")));
    }
    public World(LevelManager levelManager, JsonObject worldData) {
        init(levelManager);
        this.worldData = worldData;
        //Load the entities.
        for (JsonElement entityDatum : worldData.get("entities").getAsJsonArray()) {
            loadEntityIntoWorld(entityDatum.getAsJsonObject());
        }
        //Load the map.
        //Get the map type
        String mapTypeStr = worldData.get("mapType").getAsString();
        Optional<TileMapType> mapType = levelManager.getTileMapByName(mapTypeStr);
        assert(mapType.isPresent());
        map = mapType.get().create(this, worldData.get("map").getAsJsonArray());
        assert(map != null);
        worldData.add("map", map.getMapData());
        map.loadMap(worldData.get("map").getAsJsonArray());
    }

    public World(LevelManager levelManager) {
        init(levelManager);
        this.worldData = new JsonObject();
        worldData.add("entities", new JsonArray());
        worldData.add("map", new JsonObject());
        worldData.addProperty("mapType", levelManager.getSelectedTileMapName());
        //Load the map.
        //Get the map type
        String mapTypeStr = levelManager.getSelectedTileMapName();
        Optional<TileMapType> mapType = levelManager.getTileMapByName(mapTypeStr);
        assert(mapType.isPresent());
        map = mapType.get().create(this);
        assert(map != null);
        worldData.add("map", map.getMapData());

    }
    public void save() {
        //Saves all entities on the map.
        JsonArray entityData = new JsonArray();
        synchronized (lock) {
            for (Entity entity : getEntities().values()) {
                entity.save();
                entityData.add(entity.getData());
            }
        }

        worldData.add("entities", entityData);
        worldData.add("map", map.getMapData());
        worldData.addProperty("mapType", map.getName());
    }


    @Override
    public void createItemInInventory(String typeName, Inventory inv) {
        levelManager.createItemInInventory(typeName, inv);
    }

    @Override
    public void createItemInInventory(ItemType type, Inventory inv) {
        type.create(inv);
    }

    @Override
    public void loadItemIntoInventory(JsonObject input, Inventory inv) {
        levelManager.loadItemIntoInventory(input, inv);
    }

    @Override
    public void loadEntityIntoWorld(JsonObject input) {
        levelManager.loadEntityIntoWorld(input);
    }

    @Override
    public void createEntityAtPosition(String typeName, Position pos) {
        levelManager.createEntityAtPosition(typeName, pos);
    }

    @Override
    public void createEntityInWorld(Entity entity) {
        getEntities().put(entity.getUUID(), entity);
    }


    public Optional<Tile> createTile(String tileName, Position pos) {
        return levelManager.createTile(tileName, pos);
    }

    public Optional<Tile> loadTile(JsonObject tileData, Position pos) {
        return levelManager.loadTile(tileData, pos);
    }

    @Override
    public Optional<Entity> getEntityWithUUID(UUID uuid) {
        synchronized (lock) {
            if (getEntities().containsKey(uuid)) {
                return Optional.of(getEntities().get(uuid));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Entity> getEntitiesAtPosition(Position pos) {
        synchronized (lock) {
            ArrayList<Entity> result = new ArrayList<>();
            for (Entity entity : getEntities().values()) {
                if (entity.getPos().equals(pos)) {
                    result.add(entity);
                }
            }
            return result;
        }
    }

    @Override
    public void registerInteraction(Interaction action) {
        stringLog.log(action);
    }

    @Override
    public Optional<Tile> getTileAtPosition(Position pos) {
        return map.getTileAtPosition(pos);
    }

    public void update() {
        try {
            TimeUnit.MILLISECONDS.sleep((long)(1.0/(double)levelManager.getTickRate() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            for (Entity e : getEntities().values()) {
                e.update();
            }
        }

        tick++;
    }

    public Map<UUID, Entity> getEntities() {
        synchronized (lock) {
            return entities;
        }
    }

}
