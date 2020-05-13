package me.suesslab.rogueblight.basegame.item;

import com.google.gson.JsonObject;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.item.ItemInstance;
import me.suesslab.rogueblight.item.ItemType;

import java.util.UUID;

public class Stone extends ItemType {

    public Stone() {
        super("stone");
    }

    @Override
    protected ItemInstance getBody(Item t) {
        return new StoneInstance(t);
    }

    @Override
    public Item create(JsonObject input, Inventory i) {
        Item item = new Item(this, UUID.randomUUID(), i);
        return item;
    }

    @Override
    public Item create(Inventory i) {
        return create(null, i);
    }

    public static class StoneInstance extends ItemInstance {

        public StoneInstance(Item t) {
            super(t);
        }

        @Override
        public void update() {

        }

        @Override
        protected String getQualifiedName() {
            return "Stone";
        }

        @Override
        public void touch(Interaction action, String message) {

        }

        @Override
        public double queryMass() {
            return 1.0;
        }
    }
}
