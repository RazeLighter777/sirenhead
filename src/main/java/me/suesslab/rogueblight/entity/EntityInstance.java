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

import me.suesslab.rogueblight.interact.EntityEntityInteraction;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.interact.TooledInteraction;
import me.suesslab.rogueblight.item.Inventory;

/**
 **
 * @author justin
 */
public abstract class EntityInstance {

    private Entity instance;

    public EntityInstance(Entity t) {
        instance = t;
    }

    protected final Entity getEntity() {
        return instance;
    }

    //Updates
    public abstract void update();

    //Saves data
    public void save() {
    }

    //Interactions
    
    public abstract void touch(Interaction action, String message);
    
    
    
    //Queries
    
    public abstract double queryMass();

    public abstract Optional<UUID> getPresentedItem();

    //Components
    
    public abstract Optional<Inventory> getInventoryComponent();
    
    public abstract Optional<ILivingComponent> getLivingComponent();
    
}
