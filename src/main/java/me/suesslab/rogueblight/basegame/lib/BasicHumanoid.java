package me.suesslab.rogueblight.basegame.lib;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.HumanoidPose;
import me.suesslab.rogueblight.aspect.entity.IHumanoidComponent;
import me.suesslab.rogueblight.aspect.entity.StatsComponent;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.literary.EnumResourceWrapper;

public class BasicHumanoid implements IHumanoidComponent {

    public void setHeight(double height) {
        this.height = height;
    }

    private HumanoidPose pose;
    private double height;

    private Entity parent;


    public BasicHumanoid(Entity parent) {
        this.parent = parent;
        JsonObject localData  = parent.getData().getAsJsonObject("humanoid");
        pose = HumanoidPose.valueOf(localData.get("pose").getAsString());
        height = localData.get("height").getAsDouble();
    }

    public static JsonObject generateJson(double height, HumanoidPose defaultPose) {
        JsonObject localData = new JsonObject();
        localData.addProperty("pose", defaultPose.toString());
        localData.addProperty("height", height);
        return localData;
    }



    @Override
    public HumanoidPose getPose() {
        return pose;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Interaction speak(EntityEntityInteraction t, String speech) {
        return null;
    }

    @Override
    public void save() {
        JsonObject localData  = parent.getData().getAsJsonObject("humanoid");
        localData.addProperty("pose", getPose().toString());
        localData.addProperty("height", height);
    }

    @Override
    public void update() {

    }

    public void setPose(HumanoidPose pose) {
        this.pose = pose;
    }
}
