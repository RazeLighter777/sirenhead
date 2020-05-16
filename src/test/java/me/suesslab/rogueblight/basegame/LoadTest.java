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
        World world = new World(lvm, JsonParser.parseString("{\"entities\":[{\"uuid\":\"85c3f453-9bbc-4827-9962-cdea532ab73b\",\"position\":[0,0],\"inventory\":[{\"type\":\"stone\",\"uuid\":\"017415c8-f024-4c89-aea8-3afc87cf2575\",\"name\":\"Stone\"}],\"type\":\"itemContainer\",\"name\":\"Stone\"}],\"map\":[[[0,0],{\"type\":\"brickTile\"}]],\"mapType\":\"flatBrickMap\"}").getAsJsonObject());

        world.save();
        System.out.println(world.getWorldData().toString());
        world.getEntitiesAtPosition(new Position(0,0)).forEach(entity -> System.out.println(entity.getUUID()));
    }
}
