package me.suesslab.rogueblight.aspect;

import me.suesslab.rogueblight.interact.*;

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
     * @return The mass of the object
     */
    public abstract double queryMass();

    //Interactions.

    public Interaction hitWithThrownTool(ThrownTooledInteraction t, double velocity);

    public Interaction hitWithTool(TooledInteraction t, double velocity);

    public Interaction use(EntityEntityInteraction t);

    public Interaction activate(EntityInteraction t);

    public Interaction useToolOn(TooledInteraction t);

    public Interaction hitWithProjectile(ProjectileTooledInteraction t, double velocity);

}
