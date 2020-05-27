package me.suesslab.rogueblight.aspect.entity;

import me.suesslab.rogueblight.aspect.HumanoidPose;
import me.suesslab.rogueblight.aspect.IComponent;
import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.interact.Interaction;

public interface IHumanoidComponent extends IComponent {

    public HumanoidPose getPose();

    public double getHeight();


    //Interactions
    public Interaction speak(EntityEntityInteraction t, String speech);
}
