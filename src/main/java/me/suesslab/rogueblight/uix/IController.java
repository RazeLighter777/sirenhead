package me.suesslab.rogueblight.uix;

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

}
