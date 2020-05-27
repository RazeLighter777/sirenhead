package me.suesslab.rogueblight.lib.io;

import com.googlecode.lanterna.TerminalPosition;

public interface IActionSupplier {

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

    public boolean logKeyPressed();

    public boolean throwKeyPressed();

    public boolean freeCursorKeyPressed();


}
