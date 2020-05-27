package me.suesslab.rogueblight.interact;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.item.Item;

public abstract class ThrownTooledInteraction  extends ThrownEntityInteraction {
    private Item tool;

    private Item getTool() {
        return tool;
    }

    public ThrownTooledInteraction(Entity origin, Entity target, Entity projectile, Item tool) {
        super(origin, target, projectile);
        this.tool = tool;
    }
}
