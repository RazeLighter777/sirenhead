package me.suesslab.rogueblight.entity;

public abstract class EntityController {

    private Entity parent;

    public EntityController(Entity parent) {
        this.parent = parent;
    }

    public abstract void update();
}
