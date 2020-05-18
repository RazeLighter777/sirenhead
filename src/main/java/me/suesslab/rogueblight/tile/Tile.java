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
package me.suesslab.rogueblight.tile;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.ItemInstance;
import me.suesslab.rogueblight.item.ItemType;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.world.IWorld;

import java.util.UUID;

/**
 *
 * @author justin
 */
public final class Tile {

    private TileType type;
    private IWorld world;
    private Position pos;
    public TileInstance body;

    public IWorld getWorld() {
        return world;
    }

    public JsonObject getData() {
        return data;
    }

    protected JsonObject data;



    public Tile(TileType type, IWorld world, JsonObject data, Position pos) {
        this.type = type;
        this.world = world;
        this.pos = pos;
        this.data = data;
        body = type.getBody(this);
    }

    public final void save() {
        data.addProperty("type", getType().getName());
        body.save();
    }

    public final Position getPos() {
        return pos;
    }
    public final TileType getType() {
        return type;
    }
}
