package me.suesslab.rogueblight.interact;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.item.Item;

public abstract class ThrownTooledInteraction  extends TooledInteraction {

    public Entity getThrower() {
        return thrower;
    }

    private Entity thrower;

    public ThrownTooledInteraction(Entity thrower, Entity origin, Entity target, Item tool) {
        super(origin, target, tool);
        this.thrower = thrower;
    }
}
