package me.suesslab.rogueblight.lib;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.uix.Display;
import me.suesslab.rogueblight.uix.IFrameProvider;

import java.io.File;
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
        System.out.println("file");
    }

    @Override
    public void stop() {

    }

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        return Optional.empty();
    }
}
