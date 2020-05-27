package me.suesslab.rogueblight.basegame.lib;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.entity.IPhysicalComponent;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.*;
import me.suesslab.rogueblight.interact.implementations.GenericProjectileCollisionInteraction;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Vector;

import java.awt.*;

public class MomentumSubjectPhysicalObject implements IPhysicalComponent {

    protected Vector getMomentum() {
        return momentum;
    }

    /**
     *
     * @return The throwing entity. Null if this entity is not being thrown.
     */
    protected Entity getThrower() {
        return thrower;
    }

    private Vector momentum;
    protected Entity entity;
    //NOTE: Check for null.
    private Entity thrower;

    public MomentumSubjectPhysicalObject(Entity entity) {
        this.entity = entity;
        momentum = new Vector(entity.getData().get("physical").getAsJsonObject().get("momentum").getAsJsonArray());
    }

    @Override
    public String getNounDescription() {
        return "A physical object";
    }


    public static JsonObject generateJson(Vector momentum) {
        JsonObject result = new JsonObject();
        result.add("momentum", momentum.getJSON());
        return result;
    }
    /**
     *
     * @return Physical objects are white by default
     */
    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    /**
     * Physical objects don't objects occupy tiles by default.
     * @return
     */
    @Override
    public boolean occupiesTile() {
        return false;
    }

    @Override
    public double getMass() {
        return 1;
    }

    @Override
    public Interaction hitWithThrownTool(ThrownTooledInteraction t, Vector velocity) {
        return new NullInteraction();
    }

    @Override
    public Interaction hitWithTool(TooledInteraction t, Vector velocity) {
        return new NullInteraction();
    }


    @Override
    public Interaction use(EntityEntityInteraction t) {
        return new NullInteraction();
    }

    @Override
    public Interaction activate(EntityInteraction t) {
        return new NullInteraction();
    }

    @Override
    public Interaction useToolOn(TooledInteraction t) {
        return new NullInteraction();
    }

    @Override
    public Interaction hitWithTooledProjectile(ProjectileTooledInteraction t, Vector velocity) {
        return new NullInteraction();
    }

    @Override
    public Interaction hitWithThrownEntity(ThrownEntityInteraction t, Vector velocity) {
        return new NullInteraction();
    }

    @Override
    public void save() {
        entity.getData().add("physical", new JsonObject());
        entity.getData().get("physical").getAsJsonObject().add("momentum", momentum.getJSON());
    }


    @Override
    public Interaction impartForce(EntityEntityInteraction t, Vector force) {
        momentum = force.add(force);
        thrower = t.getOrigin();
        return new NullInteraction();
    }

    /**
     * Call this method in the update loop.
     * Will apply the momentum of the object to get its velocity. Will apply friction from tiles
     */
    protected final void updatePositionBasedOnMomentum() {
        //Get the velocity of the entity
        Vector velocity = getVelocity();
        //Calculate the new position based on the velocity.
        Position newPos = new Position((int)Math.round(entity.getPos().getX() + velocity.getX()), (int)Math.round(entity.getPos().getY() + velocity.getY()));
        //Only apply the velocity if its significant.
        if (Math.round(velocity.getY()) > 1 || Math.round(velocity.getX()) > 1) {
            if (entity.getWorld().isOccupied(newPos)) {
                //Impart momentum onto tile occupying entities.
                for (Entity e : entity.getWorld().getEntitiesAtPosition(newPos)) {
                    //TODO: Make it so entities collide with tiles in the way.
                    if (e == entity) {
                        continue;
                    }
                    if (e.body.getPhysicalComponent().isPresent()) {
                        if (e.body.getPhysicalComponent().get().occupiesTile()) {
                            collide(e);
                            momentum = new Vector(0,0);
                            return;
                        }
                    }
                }
            } else {

                //If the tile is unoccupied, move there.
                entity.getPos().setX(newPos.getX());
                entity.getPos().setY(newPos.getY());
            }
            //Apply friction
            if (entity.getWorld().getTileAtPosition(entity.getPos()).isPresent()) {
                momentum = momentum.multiply(entity.getWorld().getTileAtPosition(entity.getPos()).get().body.getFriction());
            }
        } else {
            //Set the momentum to zero if its insignificant
            momentum = new Vector(0,0);
            thrower = null;
        }

    }

    /**
     * Override this method to change collision properties.
     * @param other
     */
    protected void collide(Entity other) {
        if (thrower != null && other.body.getPhysicalComponent().isPresent()) {
            ThrownEntityInteraction interaction = new GenericProjectileCollisionInteraction(thrower, other, entity);
            entity.body.getPhysicalComponent().get().hitWithThrownEntity(interaction, getVelocity());
            entity.sendInteraction(interaction);
        }
    }

    private Vector getVelocity() {
        return momentum.divide(getMass());
    }

    /**
     * DO NOT CALL, AUTOMATICALLY CALLED IN ENTITY.
     */
    @Override
    public final void update() {
        //System.out.println(momentum.getJSON().toString());
        updatePositionBasedOnMomentum();
    }

}
