package me.suesslab.rogueblight.interact.implementations;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.literary.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public final class PickupInteraction extends EntityEntityInteraction {

    public PickupInteraction(Entity origin, Entity target) {
        super(origin, target);
    }

    @Override
    public String toString() {
        return StringUtils.makeSentence(MessageFormat.format(ResourceBundle.getBundle("Interactions").getString("PickupInteraction"), getOrigin().getQualifiedName(), getTarget().getQualifiedName()));
    }


    @Override
    public List<Entity> getRelevantEntities() {
        return Arrays.asList(getOrigin(), getTarget());
    }
}
