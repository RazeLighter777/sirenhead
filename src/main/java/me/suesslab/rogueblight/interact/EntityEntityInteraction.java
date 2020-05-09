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

import java.util.Optional;
import me.suesslab.rogueblight.entity.Entity;

/**
 *
 * @author justin
 */
public abstract class EntityEntityInteraction extends EntityInteraction {

    protected final Entity origin;
    
    protected EntityEntityInteraction(long time, Entity origin, Entity target) {
        super(time, origin);
        this.origin = origin;
        this.target = target;
    }
    
    @Override
    public void interact() {
       run(origin, target);
    }
    
    public abstract void run(Entity origin, Entity target);
    
    public final Entity getOrigin() {
        return origin;
    }
    
    
}
