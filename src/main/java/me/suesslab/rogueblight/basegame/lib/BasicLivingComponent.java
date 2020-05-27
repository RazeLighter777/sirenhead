package me.suesslab.rogueblight.basegame.lib;


import me.suesslab.rogueblight.aspect.Alignment;
import me.suesslab.rogueblight.aspect.Emotion;
import me.suesslab.rogueblight.aspect.entity.ILivingComponent;
import me.suesslab.rogueblight.aspect.entity.StatsComponent;
import me.suesslab.rogueblight.basegame.entity.Human;
import me.suesslab.rogueblight.entity.Entity;

public class BasicLivingComponent implements ILivingComponent {

    private Entity entity;

    private double health;


    @Override
    public double getThrowingStrength() {
        return entity.body.getStats().isPresent() ? entity.body.getStats().get().getStat(StatsComponent.Stat.STRENGTH) * 10 : 10;
    }

    @Override
    public double getOrientationAngle() {
        return 0;
    }

    public BasicLivingComponent(Entity entity) {
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
        entity.getData().addProperty("health", getHealthRemaining());
    }

    @Override
    public void update() {

    }
}
