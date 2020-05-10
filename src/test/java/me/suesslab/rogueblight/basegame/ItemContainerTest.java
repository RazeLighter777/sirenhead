package me.suesslab.rogueblight.basegame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityType;
import me.suesslab.rogueblight.world.IWorld;

import java.util.UUID;

public class ItemContainerTest extends TestCase {
    public static void main(String[] args) {
        EntityType containerType = new ItemContainer();
        JsonParser parser = new JsonParser();
        JsonObject data;
        data = parser.parse("{\n" +
                "\"position\" : [0, 0],\n" +
                "\"uuid\" : \"123e4567-e89b-12d3-a456-556642440000\",\n" +
                "\"type\" : \"itemContainer\",\n" +
                "\"name\" : \"Test Container\"\n" +
                "}\n").getAsJsonObject();
        Entity container = containerType.create(data, UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), new IWorld() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        System.out.println(container.getQualifiedName());
    }
}