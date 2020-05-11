package me.suesslab.rogueblight.lib;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.world.World;

public class LevelManager implements Subsystem {

    private SubsystemManager manager;
    private Registry registry;
    private World world;

    public LevelManager(Registry registry) {
        this.registry = registry;
    }

    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
    }

    public void loadItemIntoInventory(JsonObject input, Inventory inv) {
        registry.createItemInInventory(inv, input);
    }

    public void loadEntityIntoWorld(JsonObject input) {
        registry.createEntityInWorld(input, world);
    }

    @Override
    public void stop() {

    }
}
