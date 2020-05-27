package me.suesslab.rogueblight.aspect.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.aspect.IComponent;
import me.suesslab.rogueblight.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public final class StatsComponent implements IComponent {

    Map<Stat, Integer> stats = new HashMap<>();
    Entity parent;

    public StatsComponent(Entity e, int str, int resist, int agil, int intel) {
        parent = e;
        setStat(Stat.STRENGTH, str);
        setStat(Stat.RESISTANCE, resist);
        setStat(Stat.AGILITY, agil);
        setStat(Stat.INTEL, intel);
    }

    public StatsComponent(Entity e) {
        parent = e;
        JsonObject stats = e.getData().get("stats").getAsJsonObject();
        setStat(Stat.STRENGTH, stats.get("STRENGTH").getAsInt());
        setStat(Stat.RESISTANCE, stats.get("RESISTANCE").getAsInt());
        setStat(Stat.AGILITY, stats.get("AGILITY").getAsInt());
        setStat(Stat.INTEL, stats.get("INTEL").getAsInt());
    }

    public Integer getStat(Stat stat) {
        return stats.get(stat);
    }

    public void setStat(Stat stat, Integer level) {
        stats.put(stat, level);
    }

    @Override
    public void save() {
        parent.getData().add("stats", new JsonObject());
        JsonObject statsObject = parent.getData().get("stats").getAsJsonObject();
        statsObject.addProperty("STRENGTH", getStat(Stat.STRENGTH));
        statsObject.addProperty("RESISTANCE", getStat(Stat.RESISTANCE));
        statsObject.addProperty("AGILITY", getStat(Stat.AGILITY));
        statsObject.addProperty("INTEL", getStat(Stat.INTEL));
    }

    @Override
    public void update() {

    }


    public enum Stat {
        STRENGTH,
        RESISTANCE,
        AGILITY,
        INTEL,
    }


}
