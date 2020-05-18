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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import me.suesslab.rogueblight.aspect.IComponent;
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

    /**
     * DO NOT OVERRIDE UNLESS YOU NEED TO KEEP CHUNKS LOADED. THIS WILL IMPACT PERFORMANCE
     * @return the number of 16 x 16 chunks to keep loaded.
     */
    public int forceLoadedChunksRadius() {
        return 0;
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

    protected final List<IComponent> getComponents() {
        ArrayList<IComponent> components = new ArrayList<>();
        if (getHumanoidComponent().isPresent()) {
            components.add(getHumanoidComponent().get());
        }
        if (getLivingComponent().isPresent()) {
            components.add(getLivingComponent().get());
        }
        if (getPhysicalComponent().isPresent()) {
            components.add(getPhysicalComponent().get());
        }
        return components;
    }

    //Saves data
    protected void save() {
        //Save data of each component.
        getComponents().forEach(IComponent::save);
    }
    
    
    
    //Queries


    public abstract Optional<UUID> getPresentedItem();

    //Components
    
    public abstract Optional<Inventory> getInventoryComponent();
    
    public abstract Optional<ILivingComponent> getLivingComponent();

    public abstract Optional<IHumanoidComponent> getHumanoidComponent();

    public abstract Optional<IPhysicalComponent> getPhysicalComponent();

    public abstract void registerInventoryChange();

}
