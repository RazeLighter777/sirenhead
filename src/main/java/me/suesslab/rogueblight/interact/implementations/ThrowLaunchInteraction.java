package me.suesslab.rogueblight.interact.implementations;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.interact.EntityItemInteraction;
import me.suesslab.rogueblight.item.Item;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public final class ThrowLaunchInteraction extends EntityEntityInteraction {
    public ThrowLaunchInteraction(Entity origin, Entity target) {
        super(origin, target);
    }

    @Override
    public String toString() {
        return MessageFormat.format(ResourceBundle.getBundle("Interactions").getString("ThrowLaunchInteraction"), getOrigin().getQualifiedName(), getTarget().getQualifiedName());
    }

    @Override
    public List<Entity> getRelevantEntities() {
        return Arrays.asList(getOrigin());
    }
}
