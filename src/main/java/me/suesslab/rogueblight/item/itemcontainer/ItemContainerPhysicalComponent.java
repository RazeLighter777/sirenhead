package me.suesslab.rogueblight.item.itemcontainer;

import me.suesslab.rogueblight.aspect.entity.IPhysicalComponent;
import me.suesslab.rogueblight.basegame.lib.MomentumSubjectPhysicalObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.item.Item;

import java.util.UUID;

public class ItemContainerPhysicalComponent extends MomentumSubjectPhysicalObject {
    UUID containedItem;
    public ItemContainerPhysicalComponent(Entity entity, UUID containedItem) {
        super(entity);
        this.containedItem = containedItem;
    }
    @Override
    public double getMass() {
        if (entity.body.getInventoryComponent().isPresent()) {
            if (entity.body.getInventoryComponent().get().getItemByUUID(containedItem).isPresent()) {
                return entity.body.getInventoryComponent().get().getItemByUUID(containedItem).get().body.queryMass();
            }
        }
        return 1;
    }

    public void setContainedItem(UUID containedItem) {
        this.containedItem = containedItem;
    }
}
