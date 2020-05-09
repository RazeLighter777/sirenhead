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
import java.util.UUID;

/**
 *
 * @author justin
 */
public final class Item {
    
    private JsonObject data;
    
    private final UUID uuid;
    
    private Inventory inventory; 
    
    private ItemType type;
    
    public IItemBehavior body;
    
    protected Item(ItemType type, UUID uuid, Inventory inventory) {
        this.type = type;
        this.uuid = uuid;
        this.inventory = inventory;
    }
    
    public final ItemType getType() { 
        return type;
    }
    
    public final JsonObject getData() {
        return data;
    }
    
    public final UUID getUUID() {
        return uuid;
    }
    
    public final String getQualifiedName() {
        return getData().get("name").getAsString();
    }
    
    public final Inventory getInventory() {
        return inventory;
    }
    
    protected final void registerParent(Inventory i) {
        this.inventory = i;
    }
    
}
