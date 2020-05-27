package me.suesslab.rogueblight.interact;

import me.suesslab.rogueblight.entity.Entity;

import java.util.Arrays;
import java.util.List;

public abstract class ThrownEntityInteraction extends EntityEntityInteraction {

    Entity projectile;

    protected ThrownEntityInteraction(Entity origin, Entity target, Entity projectile) {
        super(origin, target);
        this.projectile = projectile;
    }

    public Entity getProjectile() {
        return projectile;
    }

}
