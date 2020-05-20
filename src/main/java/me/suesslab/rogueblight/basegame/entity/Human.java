package me.suesslab.rogueblight.basegame.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.*;
import me.suesslab.rogueblight.aspect.entity.IHumanoidComponent;
import me.suesslab.rogueblight.aspect.entity.ILivingComponent;
import me.suesslab.rogueblight.aspect.entity.IPhysicalComponent;
import me.suesslab.rogueblight.aspect.entity.StatsComponent;
import me.suesslab.rogueblight.basegame.lib.BasicHumanoid;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.lib.Position;
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
        defaultJson.add("humanoid", BasicHumanoid.generateJson(getConfig().get("defaultHeight").getAsDouble(), HumanoidPose.STANDING).getAsJsonObject());
        Entity result = new Entity(this, world, defaultJson);
        return result;
    }

    public class HumanInstance extends EntityInstance {

        private ILivingComponent humanLivingComponent;
        private BasicHumanoid humanoidComponent;
        private Inventory i;

        private StatsComponent statsComponent;

        public HumanInstance(Entity t) {
            super(t);
            humanLivingComponent = new HumanLivingComponent(t);
            statsComponent = new StatsComponent(t);
            humanoidComponent = new BasicHumanoid(t);
            i = new Inventory(getEntity(), getEntity().getData().get("inventory").getAsJsonArray());
        }

        public class HumanLivingComponent implements  ILivingComponent {

            private Entity entity;

            private double health;


            public HumanLivingComponent(Entity entity) {
                this.entity = entity;
                health = entity.getData().get("health").getAsDouble();
            }

            @Override
            public Emotion getEmotion() {
                return Emotion.NEUTRAL;
            }

            @Override
            public Alignment getAlignment() {
                return Alignment.NEUTRAL;
            }

            @Override
            public double getHealthRemaining() {
                return health;
            }

            @Override
            public double getMaxHealth() {
                return 100;
            }


            public int getMovementSpeed() {
                return 5;
            }

            @Override
            public void save() {
                getEntity().getData().addProperty("health", getHealthRemaining());
            }
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
            return Optional.empty();
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
