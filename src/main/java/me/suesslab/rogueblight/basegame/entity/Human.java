package me.suesslab.rogueblight.basegame.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.*;
import me.suesslab.rogueblight.aspect.entity.IHumanoidComponent;
import me.suesslab.rogueblight.aspect.entity.ILivingComponent;
import me.suesslab.rogueblight.aspect.entity.IPhysicalComponent;
import me.suesslab.rogueblight.aspect.entity.StatsComponent;
import me.suesslab.rogueblight.basegame.lib.BasicHumanoid;
import me.suesslab.rogueblight.basegame.lib.BasicLivingComponent;
import me.suesslab.rogueblight.basegame.lib.MomentumSubjectPhysicalObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Vector;
import me.suesslab.rogueblight.world.IWorld;

import java.util.Optional;
import java.util.UUID;

public class Human extends EntityType {

    public Human(String name) {
        super(name);
    }

    @Override
    protected EntityInstance getBody(Entity t) {
        return new HumanInstance(t);
    }

    @Override
    public Entity create(JsonObject input, IWorld world) {
        return new Entity(this, world, input);
    }

    @Override
    public Entity create(IWorld world, Position pos) {
        JsonObject defaultJson = new JsonObject();
        defaultJson.addProperty("uuid", UUID.randomUUID().toString());
        defaultJson.add("position", pos.getJSON());
        defaultJson.add("inventory", new JsonArray());
        defaultJson.add("name", getConfig().get("defaultHumanName"));
        defaultJson.add("health", getConfig().get("defaultHumanHealth"));
        defaultJson.add("stats", getConfig().get("defaultStats").getAsJsonObject());
        defaultJson.add("physical", MomentumSubjectPhysicalObject.generateJson(new Vector(0d, 0d)));
        defaultJson.add("humanoid", BasicHumanoid.generateJson(getConfig().get("defaultHeight").getAsDouble(), HumanoidPose.STANDING).getAsJsonObject());
        Entity result = new Entity(this, world, defaultJson);
        return result;
    }

    public class HumanInstance extends EntityInstance {

        private ILivingComponent humanLivingComponent;
        private BasicHumanoid humanoidComponent;
        private MomentumSubjectPhysicalObject physicalComponent;
        private Inventory i;

        private StatsComponent statsComponent;

        public HumanInstance(Entity t) {
            super(t);
            humanLivingComponent = new BasicLivingComponent(t);
            statsComponent = new StatsComponent(t);
            humanoidComponent = new BasicHumanoid(t);
            physicalComponent = new MomentumSubjectPhysicalObject(t);
            i = new Inventory(getEntity(), getEntity().getData().get("inventory").getAsJsonArray());
        }

        @Override
        protected String getQualifiedName() {
            return getEntity().getData().get("name").getAsString();
        }

        @Override
        public Optional<UUID> getPresentedItem() {
            return Optional.empty();
        }

        @Override
        protected void save() {
        }

        @Override
        public Optional<Inventory> getInventoryComponent() {
            return Optional.of(i);
        }

        @Override
        public Optional<ILivingComponent> getLivingComponent() {
            return Optional.of(humanLivingComponent);
        }

        @Override
        public Optional<IHumanoidComponent> getHumanoidComponent() {
            return Optional.of(humanoidComponent);
        }

        @Override
        public Optional<IPhysicalComponent> getPhysicalComponent() {
            return Optional.of(physicalComponent);
        }

        @Override
        public void registerInventoryChange() {
        }

        @Override
        public Optional<StatsComponent> getStats() {
            return Optional.empty();
        }
    }


}
