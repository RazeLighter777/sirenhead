package me.suesslab.rogueblight.interact.implementations;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.ThrownEntityInteraction;
import me.suesslab.rogueblight.interact.ThrownTooledInteraction;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.literary.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GenericProjectileCollisionInteraction  extends ThrownEntityInteraction {


    public GenericProjectileCollisionInteraction(Entity origin, Entity target, Entity projectile) {
        super(origin, target, projectile);
    }

    @Override
    public String toString() {
        return StringUtils.makeSentence(MessageFormat.format(ResourceBundle.getBundle("Interactions").getString("GenericProjectileCollisionInteraction"), getOrigin().getQualifiedName(), getProjectile(), getTarget()));
    }


    @Override
    public List<Entity> getRelevantEntities() {
        return Arrays.asList(getOrigin(), getTarget(), getProjectile());
    }

}
