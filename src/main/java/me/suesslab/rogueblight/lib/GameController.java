package me.suesslab.rogueblight.lib;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.uix.Display;
import me.suesslab.rogueblight.uix.IFrameProvider;

import java.util.List;
import java.util.Optional;

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

    }

    @Override
    public void stop() {

    }

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        return Optional.empty();
    }
}
