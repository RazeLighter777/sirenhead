package me.suesslab.rogueblight.interact;

import me.suesslab.rogueblight.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public final class NullInteraction extends Interaction {
    @Override
    public String toString() {
        return "Nothing happens";
    }

    @Override
    public List<Entity> getRelevantEntities() {
        return new ArrayList<>();
    }
}
