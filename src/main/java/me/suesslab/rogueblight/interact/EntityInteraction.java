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
public abstract class EntityInteraction extends Interaction {
    
    protected Entity target;
    
    public EntityInteraction(long time, Entity target) {
        super(time);
        this.target = target;
    }
    
    @Override
    public void interact() {
       run(target);
    }
    
    public abstract void run(Entity target);
        
    
}
