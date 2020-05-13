package me.suesslab.rogueblight.basegame;

import com.google.gson.JsonParser;
import junit.framework.TestCase;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.basegame.item.Stone;
import me.suesslab.rogueblight.item.ItemContainer;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Registry;
import me.suesslab.rogueblight.world.World;
import org.junit.Test;

import java.util.Arrays;

public class LoadTest extends TestCase {
    @Test
    public static void test() {
        Registry registry = new Registry();
        LevelManager lvm = new LevelManager(registry);
        SubsystemManager partialManager = new SubsystemManager(Arrays.asList(registry, lvm));
        registry.addPlugin(new BaseGamePlugin());
        World world = new World(lvm, JsonParser.parseString("{\"entities\":[{\"uuid\":\"7de05faa-2fc0-4e3f-aadc-e1e0a527c2f5\",\"position\":[0,0],\"inventory\":[{\"type\":\"stone\",\"uuid\":\"5fb22b2c-1a1d-4a35-b8db-7c61026cd664\",\"name\":\"Stone\"}],\"type\":\"itemContainer\",\"name\":\"Stone\"}],\"map\":[],\"mapType\":\"flatBrickMap\"}").getAsJsonObject());
        world.save();
        System.out.println(world.getWorldData().toString());
        world.getEntitiesAtPosition(new Position(0,0)).forEach(entity -> System.out.println(entity.getUUID()));
    }
}
