package me.suesslab.rogueblight.aspect;

import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.interact.Interaction;

public interface IHumanoidComponent {

    public HumanoidPose getPose();

    public double getHeight();

    //Interactions
    public Interaction speak(EntityEntityInteraction t, String speech);
}
