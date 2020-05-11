package me.suesslab.rogueblight.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;

import java.lang.reflect.Array;
import java.util.*;

public class World implements IWorld {

    private LevelManager levelManager;
    private JsonObject worldData;
    private HashMap<UUID, Entity> entities;

    public World(LevelManager levelManager, JsonObject worldData) {
        this.worldData = worldData;
        this.levelManager = levelManager;
        entities = new HashMap<>();
    }
    public void save() {
        //Saves all entities on the map.
        JsonArray entityData = new JsonArray();
        for (Entity entity : entities.values()) {
            entity.save();
            entityData.add(entity.getData());
        }
        worldData.add("entities", entityData);
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
    public void createEntityInWorld(Entity entity) {
        entities.put(entity.getUUID(), entity);
    }

    @Override
    public Optional<Entity> getEntityWithUUID(UUID uuid) {
        if (entities.containsKey(uuid)) {
            return Optional.of(entities.get(uuid));
        }
        return Optional.empty();
    }

    @Override
    public List<Entity> getEntitiesAtPosition(Position pos) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity entity : entities.values()) {
            if (entity.getPos().equals(pos)) {
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public void registerInteraction(Interaction action) {

    }
}
