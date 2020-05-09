package me.suesslab.rogueblight.basegame;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.entity.ILivingComponent;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.world.IWorld;

import java.util.Optional;
import java.util.UUID;

public class ItemContainer extends EntityType {

    ItemContainer(JsonObject config) {
        super("itemContainer", config);
    }

    protected EntityInstance getBody(Entity t) {
        return new ItemContainerBehavior(t);
    }

    @Override
    public Entity create(JsonObject input, UUID uuid, IWorld world) {
        return new Entity(this, uuid,world );
    }

    public Entity create(Inventory i, UUID itemId, IWorld world, UUID uuid) {
        Entity result = create(null, uuid, world);
        Optional<Item> op = i.getItemByUUID(itemId);
        if (op.isPresent()) {
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
            i = new Inventory(t);
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
