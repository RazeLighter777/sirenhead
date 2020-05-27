package me.suesslab.rogueblight.aspect.entity;

import me.suesslab.rogueblight.aspect.IComponent;
import me.suesslab.rogueblight.interact.*;
import me.suesslab.rogueblight.lib.Vector;

import java.awt.*;

public interface IPhysicalComponent extends IComponent {

    //Properties

    /**
     *
     * @return A noun with optionally attached adjectives without articles. EX: "light-blue rock with dispersed yellow crystals"
     */
    public String getNounDescription();

    /**
     * @return The color of this object
     */
    public Color getColor();

    /**
     *
     * @return whether the entity can cohabit tiles with other entities that also set this function to true.
     */
    public boolean occupiesTile();


    /**
     *
     * @return The mass of the object
     */
    public double getMass();



    //Interactions.

    public Interaction hitWithThrownTool(ThrownTooledInteraction t, Vector velocity);

    public Interaction hitWithTool(TooledInteraction t, Vector velocity);

    public Interaction use(EntityEntityInteraction t);

    public Interaction activate(EntityInteraction t);

    public Interaction useToolOn(TooledInteraction t);

    public Interaction hitWithTooledProjectile(ProjectileTooledInteraction t, Vector velocity);

    public Interaction hitWithThrownEntity(ThrownEntityInteraction t, Vector velocity);

    public Interaction impartForce(EntityEntityInteraction t, Vector force);

}
