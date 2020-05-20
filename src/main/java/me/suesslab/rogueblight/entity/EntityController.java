package me.suesslab.rogueblight.entity;

import me.suesslab.rogueblight.interact.Interaction;

public abstract class EntityController {

    private Entity parent;

    public EntityController(Entity parent) {
        this.parent = parent;
    }

    public void handleInteraction(Interaction i) {

    }

    public abstract void update();
}
