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
package me.suesslab.rogueblight.interact;

import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.item.Item;

/**
 *
 * @author justin
 */
public abstract class EntityItemInteraction extends Interaction {

    public Entity getOrigin() {
        return origin;
    }

    protected Entity origin;

    public Item getTarget() {
        return target;
    }

    protected Item target;

    public EntityItemInteraction(Entity origin, Item target) {
        this.target = target;
    }

}
