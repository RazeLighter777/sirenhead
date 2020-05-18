package me.suesslab.rogueblight.aspect;

import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.interact.Interaction;

public interface IHumanoidComponent extends IComponent {

    public HumanoidPose getPose();

    public double getHeight();

    //Interactions
    public Interaction speak(EntityEntityInteraction t, String speech);
}
