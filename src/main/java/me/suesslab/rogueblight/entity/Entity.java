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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;

import java.util.UUID;

/**
 *
 * @author justin
 */
public final class Entity {
    
    private JsonObject data;
    
    private UUID uuid;
    
    public EntityInstance body;
    
    private IWorld world;

    private Position position;


    public Entity(EntityType type, IWorld worldInterface, JsonObject input) {
        //load the data variable.
        data = input;
        //Instantiate the position
        position = new Position(0, 0);
        JsonArray position = data.getAsJsonArray("position");
        getPos().setJSON(data.getAsJsonArray("position"));
        //Instantiate the uuid
        uuid = UUID.fromString(data.get("uuid").getAsString());
        //Set the entity data
        this.type = type;
        this.uuid = uuid;
        this.world = worldInterface;
        //Instantiate the entity instance
        body = type.getBody(this);
    }
    
    private EntityType type;
    
    public final EntityType getType() { 
        return type;
    }
    
    public final JsonObject getData() {
        return data;
    }
    
    public final UUID getUUID() {
        return uuid;
    }

    public final Position getPos() {
        return position;
    }

    public final String getQualifiedName() {
        return getData().get("name").getAsString();
    }

    public final IWorld getWorld() {
        return world;
    }

    public final void update() {
        if (body.getInventoryComponent().isPresent()) {
            body.getInventoryComponent().get().update();
        }
        body.update();
    }

    public final void save() {
        //Saves the position of the entity.
        data.add("position", position.getJSON());
        //Saves the type of the entity
        data.addProperty("type", getType().getName());
        //Saves the uuid of the entity
        data.addProperty("uuid", getUUID().toString());
        //Saves the inventory if the entity has one.
        if (body.getInventoryComponent().isPresent()) {
            getData().add("inventory", body.getInventoryComponent().get().getJson());
        }
        //Call for the individual instance to save its properties.
        body.save();
    }
    
}
