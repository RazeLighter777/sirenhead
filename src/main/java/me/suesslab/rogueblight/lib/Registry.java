package me.suesslab.rogueblight.lib;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.tile.TileMapType;
import me.suesslab.rogueblight.tile.TileType;
import me.suesslab.rogueblight.world.IWorld;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public final class Registry implements ISubsystem {

    private ArrayList<EntityType> entityTypes;
    private ArrayList<ItemType> itemTypes;
    private ArrayList<TileType> tileTypes;
    private ArrayList<TileMapType> mapTypes;
    private SubsystemManager manager;
    private JsonObject allRegistryConfigs;

    public Registry() {
        entityTypes = new ArrayList<>();
        itemTypes = new ArrayList<>();
        tileTypes = new ArrayList<>();
        mapTypes = new ArrayList<>();
    }


    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
        try {
            System.out.println(manager.getDataPath() + "/" + manager.getTypeConfigFileName());
            FileReader reader = new FileReader(manager.getDataPath() + "/" + manager.getTypeConfigFileName());
            allRegistryConfigs = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (FileNotFoundException e) {
            manager.getLogger().severe("Could not open types.json at path " + manager.getDataPath());
        }
    }

    @Override
    public void stop() {

    }

    public boolean checkIfEntityTypeExists(String typeName) {
        for (EntityType type : entityTypes) {
            if (type.getName().equals(typeName)) {
                return true;
            }
        }
        return false;
    }



    public void registerType(EntityType type) {
        manager.getLogger().info("Registering new entity type " + type.getName());
        Optional<JsonObject> config = attemptToFindConfig(type.getName());
        if (config.isPresent()) {
            type.setConfig(config.get());
        } else {
            manager.getLogger().warning("Unable to find existing configuration for " + type.getName() + ", using empty configuration");
            type.setConfig(new JsonObject());
        }
        entityTypes.add(type);
    }

    public void registerType(ItemType type) {
        manager.getLogger().info("Registering new entity type " + type.getName());
        Optional<JsonObject> config = attemptToFindConfig(type.getName());
        if (config.isPresent()) {
            type.setConfig(config.get());
        } else {
            manager.getLogger().warning("Unable to find existing configuration for " + type.getName() + ", using empty configuration");
            type.setConfig(new JsonObject());
        }
        itemTypes.add(type);
    }

    public void registerType(TileType type) {
        manager.getLogger().info("Registering new tile type " + type.getName());
        tileTypes.add(type);
    }

    public void registerType(TileMapType type)  {
        manager.getLogger().info("Registering new map type " + type.getName());
        for (TileMapType mapType : mapTypes) {
            if (mapType.getName().equals(type.getName())) {
                manager.getLogger().info("Could not register duplicate map type " + type.getName());
                return;
            }
        }
        mapTypes.add(type);
    }

    public List<String> getListOfMapTypes() {
        ArrayList<String> result = new ArrayList<>();
        for (TileMapType mapType : mapTypes) {
            result.add(mapType.getName());
        }
        return result;
    }

    public Optional<TileMapType> getMapType(String name) {
        for (TileMapType mapType : mapTypes) {
            if (mapType.getName().equals(name)) {
                return Optional.of(mapType);
            }
        }
        return Optional.empty();
    }

    public Optional<Entity> loadEntityInWorld(JsonObject config, IWorld world) {
        if (!config.has("type")) {
            manager.getLogger().warning("Malformed JsonObject, type not found, cannot instantiate entity");
            return Optional.empty();
        }
        Optional<EntityType> type = getEntityTypeByName(config.get("type").getAsString());
        if (type.isPresent()) {
            Entity e = type.get().create(config, world);
            //TODO: Replace this to allow more remote connection types.

            return Optional.of(e);
        }
        manager.getLogger().warning("Unable to find matching type " + config.get("type").getAsString());
        return Optional.empty();
    }

    public boolean loadItemInInventory(Inventory inv, JsonObject config) {
        if (!config.has("type")) {
            manager.getLogger().warning("Malformed JsonObject, type not found, cannot instantiate item");
            return false;
        }
        Optional<ItemType> type = getItemTypeByName(config.get("type").getAsString());

        if (type.isPresent()) {
            type.get().create(config, inv);
            return true;
        }
        manager.getLogger().warning("Unable to find matching type " + config.get("type").getAsString());
        return false;
    }

    public void addPlugin(IPlugin plugin) {
        plugin.getEntityTypes().forEach(this::registerType);
        plugin.getItemTypes().forEach(this::registerType);
        plugin.getTileTypes().forEach(this::registerType);
        plugin.getTileMapTypes().forEach(this::registerType);
    }

    public void resetRegistry() {
        itemTypes.clear();
        entityTypes.clear();
    }

    public Optional<EntityType> getEntityTypeByName(String typeName) {
        for (EntityType type : entityTypes) {
            if (type.getName().equals(typeName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    public Optional<ItemType> getItemTypeByName(String typeName) {
        for (ItemType type : itemTypes) {
            if (type.getName().equals(typeName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    public Optional<TileType> getTileTypeByName(String typeName) {
        for (TileType type : tileTypes) {
            if (type.getName().equals(typeName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }


    private Optional<JsonObject> attemptToFindConfig(String typeName) {
        Set<Map.Entry<String, JsonElement>> entrySet = allRegistryConfigs.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            if (entry.getKey().equals(typeName)) {
                return Optional.of(entry.getValue().getAsJsonObject());
            }
        }
        return Optional.empty();
    }
}
