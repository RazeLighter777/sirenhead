/*
 * Copyright (C) 2020 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.suesslab.rogueblight.entity;

import java.util.Optional;
import java.util.UUID;

import me.suesslab.rogueblight.aspect.IHumanoidComponent;
import me.suesslab.rogueblight.aspect.ILivingComponent;
import me.suesslab.rogueblight.aspect.IPhysicalComponent;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;

/**
 **
 * @author justin
 */
public abstract class EntityInstance {

    private Entity instance;
    private EntityController entityController;

    public EntityInstance(Entity t) {
        instance = t;
        entityController = new DefaultEntityController(instance);
    }

    protected abstract String getQualifiedName();

    protected final Entity getEntity() {
        return instance;
    }

    public final EntityController getController() {
        return entityController;
    }

    public final void setEntityController(EntityController entityController) {
        this.entityController = entityController;
    }


    //Saves data
    protected void save() {
    }
    
    
    
    //Queries


    public abstract Optional<UUID> getPresentedItem();

    //Components
    
    public abstract Optional<Inventory> getInventoryComponent();
    
    public abstract Optional<ILivingComponent> getLivingComponent();

    public abstract Optional<IHumanoidComponent> getHumanoidComponent();

    public abstract Optional<IPhysicalComponent> getPhysicalComponent();
    
}
