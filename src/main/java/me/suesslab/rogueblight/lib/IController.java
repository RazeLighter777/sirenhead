package me.suesslab.rogueblight.lib;

public interface IController {

    enum Direction {
        W,
        E,
        N,
        S,
        NE,
        NW,
        SE,
        SW,
        NONE
    }

    public Direction getDirection();

    public boolean pickupKeyPressed();
}
