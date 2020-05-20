package me.suesslab.rogueblight.lib;

import com.google.gson.JsonParser;
import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.uix.Display;
import me.suesslab.rogueblight.uix.IFrameProvider;
import me.suesslab.rogueblight.uix.InteractiveEntityController;
import me.suesslab.rogueblight.world.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GameController implements  ISubsystem, IFrameProvider  {

    private Registry registry;
    private LevelManager levelManager;
    private Display display;

    public GameController(Registry registry, LevelManager lvm, Display display) {
        this.registry = registry;
        this.levelManager = lvm;
        this.display = display;
    }
    @Override
    public void init(SubsystemManager manager) {
        display.setFrameProvider(this);
    }

    public void startGame() {
        display.addNotificationWindow("WELCOME", ResourceBundle.getBundle("Game").getString("MOTD"));
        while (display.isMenuOpen()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        choice = -2;
        while (true) {
            if (!display.isMenuOpen()) {
                display.addStringSelectionWindow("Main Menu", new ArrayList<>(Arrays.asList("SELECT SAVE", "EXIT")), this::mainMenuCallBack);
            } else {
                if (newChoice) {
                    if (!executeChoice()) {
                        break;
                    }
                }

            }

        }
    }

    private Integer choice;
    private boolean newChoice = false;
    private void mainMenuCallBack(Integer i) {
        choice = i;
        newChoice = true;
    }
    private boolean executeChoice() {
        switch (choice) {
            case -2:
            case -1:
                newChoice = false;
                return true;
            case 1:
                newChoice = false;
                return false;
            case 0:
                selectSave();
                newChoice = false;
                System.out.println("Starting game");
                return true;
        }
        return false;
    }

    private void selectSave() {
        String fileName  = display.fileSelectionDialog("Load your game", "Select a world file (extension is .json)");
        File file = new File(fileName);
        if (!file.exists() ||  !fileName.endsWith(".json")) {
            display.blockingMessage("EAT MY ASS", "That file doesn't have the right extension. You need a .json");
        }
        try {
            FileReader fileReader = new FileReader(file);
            World world = new World(levelManager, JsonParser.parseReader(fileReader).getAsJsonObject());
            display.blockingMessage("Great Victory!", "World loaded successfully!");
            //Attach to the first local player in the save.
            Entity player = null;
            for (Entity e : world.getEntities().values()) {
                if (e.getData().has("isLocalPlayer")) {
                    KeyBoardController controller = new KeyBoardController(display.getTerminal());
                    display.setKeyBoardController(controller);
                    InteractiveEntityController playerController = new InteractiveEntityController(e, display, controller);
                    display.setFrameProvider(playerController);
                    e.body.setEntityController(playerController);
                    player = e;
                    break;
                }
            }
            display.closeGui();
            while (true) {
                world.update();
                if (!world.getEntities().containsValue(player)) {
                    break;
                }
                world.save();
            }
            //display.setFrameProvider(this);
        } catch (FileNotFoundException e) {
            display.blockingMessage("GLITCH", "FileNotFoundException");
            return;
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        return Optional.empty();
    }
}
