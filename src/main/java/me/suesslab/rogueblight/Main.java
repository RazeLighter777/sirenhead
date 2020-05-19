package me.suesslab.rogueblight;


import com.google.gson.JsonParser;
import me.suesslab.rogueblight.basegame.BaseGamePlugin;
import me.suesslab.rogueblight.basegame.entity.Human;
import me.suesslab.rogueblight.basegame.item.Stone;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.item.ItemContainer;
import me.suesslab.rogueblight.lib.KeyBoardController;
import me.suesslab.rogueblight.lib.LevelManager;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.lib.Registry;
import me.suesslab.rogueblight.uix.Display;
import me.suesslab.rogueblight.uix.InteractiveEntityController;
import me.suesslab.rogueblight.world.World;

import java.awt.*;
import java.util.Arrays;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Registry registry = new Registry();
        LevelManager lvm = new LevelManager(registry);
        Display display = new Display();
        SubsystemManager partialManager = new SubsystemManager(Arrays.asList(registry, lvm, display));
        registry.addPlugin(new BaseGamePlugin());
        World world = new World(lvm);
        Human human  = new Human("human");
        human.setConfig(JsonParser.parseString("{\"defaultHumanName\" :  \"Steve\", \"defaultHumanHealth\": 100.0, \"defaultStats\" : {\n" +
                "      \"STRENGTH\" : 1,\n" +
                "      \"INTEL\" : 1,\n" +
                "      \"RESISTANCE\" : 1,\n" +
                "      \"AGILITY\" : 1\n" +
                "    }}").getAsJsonObject());
        Entity avatar = human.create(world, new Position(0,0));
        world.createEntityInWorld(avatar);
        ItemContainer itemContainer = new ItemContainer();
        Stone stone = new Stone();
        world.createEntityInWorld(itemContainer.create(stone, world, new Position(1,0)));
        world.createEntityInWorld(itemContainer.create(stone, world, new Position(4,-3)));
        world.createEntityInWorld(itemContainer.create(stone, world, new Position(3,2)));
        KeyBoardController controller = new KeyBoardController(display.getTerminal());
        display.setKeyBoardController(controller);
        InteractiveEntityController playerController = new InteractiveEntityController(avatar, display, controller);
        avatar.body.setEntityController(playerController);
        display.setFrameProvider(playerController);
        while (true) {
            world.update();
            //System.out.println(avatar.getPos().getJSON().toString());
        }
    }
}
