package me.suesslab.rogueblight.basegame;
import com.google.gson.JsonObject;
import junit.framework.TestCase;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.item.ItemContainer;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Registry;
import me.suesslab.rogueblight.world.World;
import org.junit.Test;

import java.util.Arrays;

public class ItemContainerTest extends TestCase {
    @Test
    public static void test() {
        Registry registry = new Registry();
        registry.addPlugin(new BaseGamePlugin());
        LevelManager lvm = new LevelManager(registry);
        World world = new World(lvm, new JsonObject());
        //SubsystemManager partialManager = new SubsystemManager(Arrays.asList(registry, lvm));
        ItemContainer itemContainer = new ItemContainer();
        Stone stone = new Stone();
        world.createEntityInWorld(itemContainer.create(stone, world, new Position(0,0)));

        world.save();
        world.save();
        world.save();
    }
}