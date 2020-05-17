package me.suesslab.rogueblight.interact;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.item.Item;

public abstract class ProjectileTooledInteraction extends TooledInteraction {

    public Entity getProjectile() {
        return projectile;
    }
    protected Entity projectile;
    public ProjectileTooledInteraction(Entity origin, Entity target, Item tool, Entity projectile) {
        super(origin, target, tool);
        this.projectile = projectile;
    }

}
