package me.suesslab.rogueblight.uix;

import junit.framework.TestCase;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.basegame.BaseGamePlugin;
import me.suesslab.rogueblight.basegame.item.Stone;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;
import me.suesslab.rogueblight.item.ItemContainer;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Registry;
import me.suesslab.rogueblight.world.World;
import org.junit.Test;

import java.util.Arrays;

public class EntityDisplayControllerTest extends TestCase {

    @Test
    public void test() throws InterruptedException {
        Registry registry = new Registry();
        LevelManager lvm = new LevelManager(registry);
        Display display = new Display();
        SubsystemManager partialManager = new SubsystemManager(Arrays.asList(registry, lvm, display));
        registry.addPlugin(new BaseGamePlugin());
        World world = new World(lvm);
        ItemContainer itemContainer = new ItemContainer();
        Entity stoneOnGround = itemContainer.create(new Stone(),world, new Position(10,0));
        EntityDisplayController playerController = new EntityDisplayController(stoneOnGround, display);
        stoneOnGround.body.setEntityController(playerController);
        display.setFrameProvider(playerController);
    }
}