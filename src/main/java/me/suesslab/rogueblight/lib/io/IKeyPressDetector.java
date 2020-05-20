package me.suesslab.rogueblight.lib.io;

public interface IKeyPressDetector {

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

    public boolean dropKeyPressed();


}
