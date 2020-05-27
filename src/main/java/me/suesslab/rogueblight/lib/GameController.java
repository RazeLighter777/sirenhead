package me.suesslab.rogueblight.lib;

import com.google.gson.JsonParser;
import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.lib.audio.AudioManager;
import me.suesslab.rogueblight.lib.io.KeyBoardController;
import me.suesslab.rogueblight.uix.Display;
import me.suesslab.rogueblight.uix.IFrameProvider;
import me.suesslab.rogueblight.uix.InteractiveEntityController;
import me.suesslab.rogueblight.world.World;

import java.io.*;
import java.util.*;

public class GameController implements  ISubsystem, IFrameProvider  {

    private Registry registry;
    private LevelManager levelManager;
    private Display display;
    private SubsystemManager manager;
    private AudioManager audioManager;

    public GameController(Registry registry, LevelManager lvm, Display display, AudioManager audioManager) {
        this.registry = registry;
        this.levelManager = lvm;
        this.display = display;
        this.audioManager = audioManager;
    }
    @Override
    public void init(SubsystemManager manager) {
        display.setFrameProvider(this);
        this.manager = manager;
    }

    public void startGame() {
        audioManager.setBackgroundSound("mainmenu.MID");
        display.addMessage("WELCOME", ResourceBundle.getBundle("Game").getString("MOTD"), true);
        choice = -2;
        while (true) {

            if (!display.isMenuOpen()) {
                display.addStringSelectionWindow(ResourceBundle.getBundle("Game").getString("Title"), new ArrayList<>(Arrays.asList("SELECT SAVE", "EXIT")), this::mainMenuCallBack, false);
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
            display.addMessage("EAT MY ASS", "That file doesn't have the right extension. You need a .json", true);
        }
        try {
            FileReader fileReader = new FileReader(file);
            World world = new World(levelManager, JsonParser.parseReader(fileReader).getAsJsonObject());
            display.addMessage("Great Victory!", "Once upon a time, angels ruled the earth. \nThose destined as traitors were cast down to earth. You inhabit the remnants.", true);
            audioManager.muteBackgroundSounds();
            //Attach to the first local player in the save.
            Entity player = null;
            for (Entity e : world.getEntities().values()) {
                if (e.getData().has("isLocalPlayer")) {
                    KeyBoardController controller = new KeyBoardController(display.getTerminal());
                    display.setStrokeHandler(controller);
                    InteractiveEntityController playerController = new InteractiveEntityController(e, display, controller);
                    display.setFrameProvider(playerController);
                    e.body.setEntityController(playerController);
                    player = e;
                    break;
                }
            }
            display.closeGui();
            fileReader.close();
            while (true) {
                world.update();
                if (!world.getEntities().containsValue(player)) {
                    break;
                }
                System.out.println(player.getPos().getJSON().toString());
                if (world.getTick() % 1000 == 0) {
                    manager.getLogger().info("Saving world");
                    FileWriter writer = new FileWriter(file, false);
                    world.save();
                    writer.write(world.getWorldData().toString());
                    writer.close();
                }
            }
            //display.setFrameProvider(this);
        } catch (FileNotFoundException e) {;
            return;
        } catch (IOException e) {
            e.printStackTrace();
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
