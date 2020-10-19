package me.suesslab.rogueblight.lib;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.tile.Tile;
import me.suesslab.rogueblight.tile.TileMapType;
import me.suesslab.rogueblight.tile.TileType;
import me.suesslab.rogueblight.world.World;

import java.util.Optional;
import java.util.ResourceBundle;

public class LevelManager implements ISubsystem {

    private SubsystemManager manager;
    private Registry registry;

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private GameController gameController = null;

    //TODO: Allow proper level world management outide getters / setters.
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private World world;

    public LevelManager(Registry registry) {
        this.registry = registry;
    }


    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
    }

    public void loadItemIntoInventory(JsonObject input, Inventory inv) {
        registry.loadItemInInventory(inv, input);
    }

    public void createItemInInventory(String itemType, Inventory inv) {
        Optional<ItemType> type = registry.getItemTypeByName("name");
        if (type.isPresent()) {
            type.get().create(inv);
            return;
        }
        manager.getLogger().warning("Entity with type " + itemType + " not registered, could not create");
    }

    public void loadEntityIntoWorld(JsonObject input) {
        Optional<Entity> entity = registry.loadEntityInWorld(input, world);
        if (entity.isPresent()) {
            world.createEntityInWorld(entity.get());
            //If there is a gameController, construct the appropriate entity controller.
            if (gameController != null) {
                System.out.println(entity.get().getQualifiedName());
                if (entity.get().getData().get("declaredController") != null) {
                    gameController.getSupportedController(entity.get().getData().get("declaredController").getAsString(), entity.get());
                }
            }
        }
    }

    public void createEntityAtPosition(String typeName, Position pos) {
        Optional<EntityType> type = registry.getEntityTypeByName("name");
        if (type.isPresent()) {
            type.get().create(world, pos);
        }
        manager.getLogger().warning("Entity with type " + typeName + " not registered, could not create");
    }

    public Optional<Tile> createTile(String tileName, Position pos) {
        Optional<TileType> type = registry.getTileTypeByName(tileName);
        if (type.isPresent()) {
            return Optional.of(type.get().create(world, pos));
        }
        manager.getLogger().warning("Tile with type " + tileName + " not registered, could not create");
        return Optional.empty();
    }

    public Optional<Tile> loadTile(JsonObject tileData, Position pos) {
        if (!tileData.has("type")) {
            manager.getLogger().warning("Cannot create tile with missing type data: " + tileData.toString());
            return Optional.empty();
        }
        String tileName = tileData.get("type").getAsString();
        Optional<TileType> type = registry.getTileTypeByName(tileName);
        if (type.isPresent()) {
            return Optional.of(type.get().create(world, pos));
        }
        manager.getLogger().warning("Tile with type " + tileName + " not registered, could not create");
        return Optional.empty();
    }
    public String getSelectedTileMapName() {
        return "flatBrickMap";
    }

    public Optional<TileMapType> getTileMapByName(String name) {
        return registry.getMapType(name);
    }

    public int getTickRate() {
        return Integer.parseInt(ResourceBundle.getBundle("Game").getString("tickRate"));
    }
    @Override
    public void stop() {

    }
}
