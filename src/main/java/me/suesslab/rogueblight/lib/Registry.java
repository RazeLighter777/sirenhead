package me.suesslab.rogueblight.lib;

import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.ItemType;

import java.util.ArrayList;

public final class Registry implements Subsystem {

    private ArrayList<EntityType> entityTypes;
    private ArrayList<ItemType> itemTypes;
    private SubsystemManager manager;

    public void registerEntityType(EntityType type) {
        entityTypes.add(type);
    }

    public void registerItemType(ItemType type) {
        itemTypes.add(type);
    }

    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
        String path = manager.getDataPath();

    }

    @Override
    public void stop() {

    }
}
