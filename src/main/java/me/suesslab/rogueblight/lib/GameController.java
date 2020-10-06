package me.suesslab.rogueblight.lib;

import com.google.gson.JsonParser;
import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.lib.audio.AudioManager;
import me.suesslab.rogueblight.entity.interactive.ZirconDisplay;
import me.suesslab.rogueblight.uix.IFrameProvider;
import me.suesslab.rogueblight.world.World;

import java.io.*;
import java.util.*;

public class GameController implements  ISubsystem, IFrameProvider  {

    private Registry registry;
    private LevelManager levelManager;
    private ZirconDisplay zirconDisplay;
    private SubsystemManager manager;
    private AudioManager audioManager;

    public GameController(Registry registry, LevelManager lvm, ZirconDisplay zirconDisplay, AudioManager audioManager) {
        this.registry = registry;
        this.levelManager = lvm;
        this.zirconDisplay = zirconDisplay;
        this.audioManager = audioManager;
    }


    @Override
    public void init(SubsystemManager manager) {
        zirconDisplay.setFrameProvider(this);
        this.manager = manager;
    }

    public void startGame() {
        audioManager.setBackgroundSound("mainmenu.MID");
        zirconDisplay.getzDriver().addMessage("WELCOME", ResourceBundle.getBundle("Game").getString("MOTD"), true);
        choice = -2;
        while (true) {

            if (!zirconDisplay.isMenuOpen()) {
               zirconDisplay.getzDriver().addStringSelectionWindow(ResourceBundle.getBundle("Game").getString("Title"), new ArrayList<>(Arrays.asList("SELECT SAVE", "EXIT")), false, this::mainMenuCallBack);
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
        String fileName  = zirconDisplay.getzDriver().fileSelectionDialog("Load your game", "Select a world file (extension is .json)");
        File file = new File(fileName);
        if (!file.exists() ||  !fileName.endsWith(".json")) {
            zirconDisplay.getzDriver().addMessage("EAT MY ASS", "That file doesn't have the right extension. You need a .json", true);
        }
        try {
            FileReader fileReader = new FileReader(file);
            World world = new World(levelManager, JsonParser.parseReader(fileReader).getAsJsonObject());
            zirconDisplay.getzDriver().addMessage("Great Victory!", "Once upon a time, angels ruled the earth. \nThose destined as traitors were cast down to earth. You inhabit the remnants.", true);
            audioManager.muteBackgroundSounds();
            //Attach to the first local player in the save.
            Entity player = null;
            for (Entity e : world.getEntities().values()) {
                if (e.getData().has("isLocalPlayer")) {
                    //KeyBoardController controller = new KeyBoardController(display.getTerminal());
                    //display.setStrokeHandler(controller);
                    //InteractiveEntityController playerController = new InteractiveEntityController(e, display, controller);
                    //display.setFrameProvider(playerController);
                    //e.body.setEntityController(playerController);
                    player = e;
                    break;
                }
            }
            zirconDisplay.closeGui();
            fileReader.close();
            while (true) {
                world.update();
                if (!world.getEntities().containsValue(player)) {
                    break;
                }
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
