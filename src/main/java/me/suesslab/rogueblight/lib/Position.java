package me.suesslab.rogueblight.lib;

import com.google.gson.JsonArray;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public Position(JsonArray a) {
        this(0,0);
        setJSON(a);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double distanceTo(Position other) {
        return Math.sqrt(Math.pow(other.getX() - getX(),2) + Math.pow(other.getY() - getY(), 2));
    }

    public JsonArray getJSON() {
        JsonArray coords = new JsonArray();
        coords.add(x);
        coords.add(y);
        return coords;
    }

    public void setJSON(JsonArray a) {
        setX(a.get(0).getAsInt());
        setY(a.get(1).getAsInt());
    }

    public boolean equals(Position other) {
        if ((other.getX() == getX()) && (other.getY() == getY())) {
            return true;
        }
        return false;
    }
}
