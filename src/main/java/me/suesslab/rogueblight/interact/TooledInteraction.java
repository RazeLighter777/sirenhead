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

/**
 *
 * @author justin
 */
public abstract class TooledInteraction extends EntityEntityInteraction {
    
    private final Item tool;
    
    public TooledInteraction(long time, Entity origin, Entity target, Item tool) {
        super(time, origin, target);
        this.tool = tool;
    }
    @Override
    public final void interact() {
        run(origin, target, tool);
    }
    public abstract void run(Entity origin, Entity target, Item tool);
    
    public final Item getTool() {
        return tool;
    }

}
