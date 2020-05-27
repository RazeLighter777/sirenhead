package me.suesslab.rogueblight.lib;

import com.google.gson.JsonArray;

public class Vector  {
    public Vector(double x, double y) {
        this.x = x; this.y = y;
    }

    private double x;
    private double y;


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double distanceTo(Vector other) {
        return Math.sqrt(Math.pow(other.getX() - getX(),2) + Math.pow(other.getY() - getY(), 2));
    }

    public void set(Vector other) {
        setX(other.getX());
        setY(other.getY());
    }

    public JsonArray getJSON() {
        JsonArray coords = new JsonArray();
        coords.add(x);
        coords.add(y);
        return coords;
    }

    public void setJSON(JsonArray a) {
        setX(a.get(0).getAsDouble());
        setY(a.get(1).getAsDouble());
    }

    public boolean equals(Position other) {
        if ((other.getX() == getX()) && (other.getY() == getY())) {
            return true;
        }
        return false;
    }

    public Vector divide(double divisor) {
        return multiply(1/divisor);
    }

    public Vector multiply(double multiplier) {
        return (new Vector(getX() * multiplier, getY() * multiplier));
    }

    public double dotProduct(Vector vector) {
        return getX() * vector.getX() + getY()*vector.getY();
    }

    public double magnitude() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public Vector add(Vector other) {
        return new Vector(other.getX() + getX(), other.getY() + getY());
    }

    public Vector subtract(Vector other) {
        return new Vector(getX() - other.getX(), getY() - other.getY());
    }

    public Vector(JsonArray a) {
        setX(a.get(0).getAsInt());
        setY(a.get(1).getAsInt());
    }

}
