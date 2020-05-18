package me.suesslab.rogueblight.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.IHumanoidComponent;
import me.suesslab.rogueblight.aspect.IPhysicalComponent;
import me.suesslab.rogueblight.basegame.item.Stone;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.aspect.ILivingComponent;
import me.suesslab.rogueblight.interact.Interaction;
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

    @Override
    public Entity create(IWorld world, Position pos) {
        return create(new Stone(), world, pos);
    }


    public Entity create(Inventory i, UUID itemId, IWorld world, Position pos) {
        JsonObject defaultJson = new JsonObject();
        defaultJson.addProperty("name", i.getItemByUUID(itemId).get().getQualifiedName());
        defaultJson.addProperty("uuid", UUID.randomUUID().toString());
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


    public Entity create(ItemType type, IWorld world, Position pos) {
        JsonObject defaultJson = new JsonObject();
        defaultJson.addProperty("uuid", UUID.randomUUID().toString());
        defaultJson.add("position", pos.getJSON());
        defaultJson.add("inventory", new JsonArray());
        Entity result = new Entity(this, world, defaultJson);
        type.create(result.body.getInventoryComponent().get());
        return result;
    }

    public static class ItemContainerBehavior extends EntityInstance {

        private Inventory i;

        public ItemContainerBehavior(Entity t) {
            super(t);
            i = new Inventory(t, t.getData().get("inventory").getAsJsonArray());
        }

        @Override
        protected String getQualifiedName() {
            return i.getItemByUUID(i.getItemUUIDs().get(0)).get().getQualifiedName();
        }

        @Override
        public void save() {

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

        @Override
        public Optional<IHumanoidComponent> getHumanoidComponent() {
            return Optional.empty();
        }

        @Override
        public Optional<IPhysicalComponent> getPhysicalComponent() {
            return Optional.empty();
        }

        @Override
        public void registerInventoryChange() {
            if (i.getItemCount() == 0) {
                getEntity().getWorld().removeEntity(getEntity().getUUID());
            }
        }
    }
}
