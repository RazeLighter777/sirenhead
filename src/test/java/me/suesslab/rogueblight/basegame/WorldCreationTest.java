package me.suesslab.rogueblight.basegame;
import junit.framework.TestCase;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.basegame.item.Stone;
import me.suesslab.rogueblight.item.itemcontainer.ItemContainer;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Registry;
import me.suesslab.rogueblight.world.World;
import org.junit.Test;

import java.util.Arrays;

public class WorldCreationTest extends TestCase {
    @Test
    public static void test() {
        Registry registry = new Registry();
        LevelManager lvm = new LevelManager(registry);
        SubsystemManager partialManager = new SubsystemManager(Arrays.asList(registry, lvm));
        registry.addPlugin(new BaseGamePlugin());
        World world = new World(lvm);
        Stone stone = new Stone();
        ItemContainer itemContainer = new ItemContainer();
        world.createEntityInWorld(itemContainer.create(stone, world, new Position(0,0)));
        world.getTileAtPosition(new Position(0,0));
        world.save();
        System.out.println(world.getWorldData().toString());
    }
}