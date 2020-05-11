package me.suesslab.rogueblight.world;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.lib.Position;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWorld {

    public abstract void loadItemIntoInventory(JsonObject input, Inventory inv);

    public abstract void loadEntityIntoWorld(JsonObject input);

    public abstract void createEntityInWorld(Entity entity);

    public abstract Optional<Entity> getEntityWithUUID(UUID uuid);

    public abstract List<Entity> getEntitiesAtPosition(Position pos);

    public abstract void registerInteraction(Interaction action);
}
