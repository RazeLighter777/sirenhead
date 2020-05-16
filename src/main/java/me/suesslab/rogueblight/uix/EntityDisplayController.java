package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDisplayController extends EntityController implements IFrameProvider {

    private Entity entity;
    private Display display;
    private SubsystemManager manager;
    private List<List<TextCharacter>> currentFrame;

    public EntityDisplayController(Entity e, Display display) {
        super(e);
        this.display = display;
        currentFrame = new ArrayList<>();
    }

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        return Optional.empty();
    }

    private void constructMap() {

    }

    @Override
    public void update() {

    }
}
