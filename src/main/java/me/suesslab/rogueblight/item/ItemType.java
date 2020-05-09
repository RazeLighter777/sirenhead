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
package me.suesslab.rogueblight.item;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityInstance;
import me.suesslab.rogueblight.world.IWorld;

import java.util.UUID;

/**
 *
 * @author justin
 */
public abstract class ItemType {

    private String name;
    private UUID id;
    protected JsonObject config;

    protected ItemType(String name, JsonObject config) {
        this.name = name;
        this.id = UUID.nameUUIDFromBytes(name.getBytes());
        this.config = config;
    }
    
    public final UUID getId() {
        return id;
    }
    
    public final String getName() {
        return name;
    }
    
    public final JsonObject getConfig() {
        return config;
    }
    
    protected abstract ItemInstance getBody(Item t);
    
    public abstract Item create(JsonObject input, UUID uuid, IWorld world);
    
    
}
