package me.suesslab.rogueblight;


import me.suesslab.rogueblight.basegame.BaseGamePlugin;
import me.suesslab.rogueblight.lib.*;
import me.suesslab.rogueblight.lib.audio.AudioManager;
import me.suesslab.rogueblight.lib.io.KeyBoardController;
import me.suesslab.rogueblight.uix.Display;

import java.util.Arrays;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Registry registry = new Registry();
        LevelManager lvm = new LevelManager(registry);
        Display display = new Display();
        AudioManager audioManager = new AudioManager();
        GameController gameController = new GameController(registry, lvm, display, audioManager);
        SubsystemManager partialManager = new SubsystemManager(Arrays.asList(registry, lvm, display, gameController, audioManager));
        registry.addPlugin(new BaseGamePlugin());
        //KeyBoardController controller = new KeyBoardController(display.getTerminal());
        //display.setStrokeHandler(controller);
        gameController.startGame();
        partialManager.requestShutdown();
        /*Human human  = new Human("human");
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
            world.save();
            //System.out.println(world.getWorldData().toString());
        }*/
    }
}
