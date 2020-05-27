package me.suesslab.rogueblight.aspect;

public interface IComponent {
    public void save();

    /**
     * DO NOT CALL AUTOMATICALLY CALLED IN ENTITY
     */
    public void update();
}
