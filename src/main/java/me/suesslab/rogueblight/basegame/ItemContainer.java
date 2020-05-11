package me.suesslab.rogueblight.basegame;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.entity.ILivingComponent;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;

import java.util.Optional;
import java.util.UUID;

public class ItemContainer extends EntityType {

    public ItemContainer() {
        super("itemContainer");
    }

    protected EntityInstance getBody(Entity t) {
        return new ItemContainerBehavior(t);
    }

    @Override
    public Entity create(JsonObject input, IWorld world) {
        return new Entity(this ,world, input );
    }


    public Entity create(Inventory i, UUID itemId, IWorld world, UUID uuid, Position pos) {
        JsonObject defaultJson = new JsonObject();
        defaultJson.addProperty("name", i.getItemByUUID(uuid).get().getQualifiedName());
        defaultJson.addProperty("uuid", itemId.toString());
        defaultJson.add("position", pos.getJSON());
        defaultJson.add("inventory", new JsonArray());
        Entity result = null;
        Optional<Item> op = i.getItemByUUID(itemId);
        if (op.isPresent()) {
            result = create(defaultJson, world);
            Inventory.transferItem(i, result.body.getInventoryComponent().get(), op.get());
            return result;
        } else {
            System.out.println("Could not find item in inventory.");
            return null;
        }
    }


    public static class ItemContainerBehavior extends EntityInstance {

        private Inventory i;

        public ItemContainerBehavior(Entity t) {
            super(t);
            i = new Inventory(t, t.getData().get("inventory").getAsJsonArray());
        }

        @Override
        public void update() {

        }

        @Override
        public void save() {

        }

        @Override
        public void touch(Interaction action, String message) {

        }

        @Override
        public double queryMass() {
            Item item = i.getItemByUUID(i.getItemUUIDs().get(0)).get();
            return item.body.queryMass();
        }

        @Override
        public Optional<UUID> getPresentedItem() {
            return Optional.of(i.getItemUUIDs().get(0));
        }

        @Override
        public Optional<Inventory> getInventoryComponent() {
            return Optional.of(i);
        }

        @Override
        public Optional<ILivingComponent> getLivingComponent() {
            return Optional.empty();
        }
    }
}
