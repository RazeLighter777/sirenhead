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

import com.google.gson.JsonObject;
import java.util.UUID;

/**
 *
 * @author justin
 */
public abstract class EntityType {
    
    private String name;
    private UUID id;
    protected JsonObject config;
    protected IEntityBehavior body;
    
    private EntityType(String name, JsonObject config, IEntityBehavior body) {
        this.name = name;
        this.id = UUID.nameUUIDFromBytes(name.getBytes());
        this.config = config;
        this.body = body;
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
    
    protected final IEntityBehavior getBody() {
        return body;
    }
    
    public abstract Entity create(JsonObject input);
    
    
}
