package me.suesslab.rogueblight.interact.implementations;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.EntityItemInteraction;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.literary.StringLog;
import me.suesslab.rogueblight.literary.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public final class DropInteraction extends EntityItemInteraction {

    public DropInteraction(Entity origin, Item target) {
        super(origin, target);
    }

    @Override
    public String toString() {
        return StringUtils.makeSentence(MessageFormat.format(ResourceBundle.getBundle("Interactions").getString("DropInteraction"), getOrigin().getQualifiedName(), getTarget().getQualifiedName()));
    }

    @Override
    public List<Entity> getRelevantEntities() {
        return Arrays.asList(getOrigin());
    }
}
