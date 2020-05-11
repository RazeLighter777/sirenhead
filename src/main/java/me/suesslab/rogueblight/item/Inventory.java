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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.suesslab.rogueblight.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author justin
 */
public final class Inventory {


    private Entity parent;
    private ArrayList<Item> items = new ArrayList<>();

    public Inventory(Entity parent) {
        this.parent = parent;

    }

    public Inventory(Entity parent, JsonArray json) {
        for (JsonElement itemData : json) {
            parent.getWorld().loadItemIntoInventory(itemData.getAsJsonObject(), this);
        }
    }

    public final void update() {
        items.forEach(item -> item.body.update());
    }
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<String> getItemNames() {
        ArrayList<String> result = new ArrayList<>();
        items.forEach(item -> {
            result.add(item.getQualifiedName());
        });
        Collections.sort(result);
        return result;
    }

    public ArrayList<UUID> getItemUUIDs() {
        ArrayList<UUID> result = new ArrayList<>();
        items.forEach(item -> {
            result.add(item.getUUID());
        });
        Collections.sort(result);
        return result;
    }

    public ArrayList<Item> getItemsByQualifiedName(String name) {
        ArrayList<Item> result = new ArrayList<>();
        items.forEach(item -> {
            if (item.getQualifiedName().equals(name)) {
                result.add(item);
            }
        });
        return result;
    }

    public ArrayList<Item> getItemsByTypeName(String name) {
        ArrayList<Item> result = new ArrayList<>();
        items.forEach(item -> {
            if (item.getType().getName().equals(name)) {
                result.add(item);
            }
        });
        return result;
    }

    public Optional<Item> getItemByUUID(UUID uuid) {
        for (Item i : items) {
            if (i.getUUID().equals(uuid)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
    private boolean removeItem(Item i) {
        if (items.contains(i)) {
            items.remove(i);
            return true;
        }
        return false;
    }

    public static boolean transferItem(Inventory i1, Inventory i2, Item i) {
        if (i1.removeItem(i)) {
            i2.addItem(i);
            return true;
        }
        return false;
    }


    protected void addItem(Item i) {
        i.setParent(this);
        items.add(i);
    }

    public JsonArray getJson() {
        JsonArray array = new JsonArray();
        items.forEach(Item::save);
        items.forEach(item -> array.add(item.getData()));
        return array;
    }

}
