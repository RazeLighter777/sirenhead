package me.suesslab.rogueblight.basegame.entity;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.IHumanoidComponent;
import me.suesslab.rogueblight.aspect.ILivingComponent;
import me.suesslab.rogueblight.aspect.IPhysicalComponent;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;

import java.util.Optional;
import java.util.UUID;

public class Player extends EntityType {

    protected Player(String name) {
        super(name);
    }

    @Override
    protected EntityInstance getBody(Entity t) {
        return new PlayerInstance(t);
    }

    @Override
    public Entity create(JsonObject input, IWorld world) {
        return new Entity(this, world, input);
    }

    @Override
    public Entity create(IWorld world, Position pos) {
        return null;
    }

    public static class PlayerInstance extends EntityInstance {

        public PlayerInstance(Entity t) {
            super(t);
        }

        @Override
        protected String getQualifiedName() {
            return null;
        }

        @Override
        public Optional<UUID> getPresentedItem() {
            return Optional.empty();
        }

        @Override
        public Optional<Inventory> getInventoryComponent() {
            return Optional.empty();
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
    }
}
