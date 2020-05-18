package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDisplayController extends EntityController implements IFrameProvider {

    private Entity entity;
    private Display display;
    private SubsystemManager manager;
    private List<List<TextCharacter>> currentFrame;
    private IController controller;
    private long nextMoveTick = 0;

    public EntityDisplayController(Entity e, Display display, IController controller) {
        super(e);
        this.controller = controller;
        this.display = display;
        currentFrame = new ArrayList<>();
        this.entity = e;
    }

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        currentFrame.clear();
        blankCanvas();
        constructMap();
        printBottomText();
        return Optional.of(currentFrame);
    }

    private void constructMap() {
        for (int xpos = 1; xpos < display.getScreenX() - 1; xpos++) {
            for (int ypos = 1; ypos < display.getScreenY() - 1; ypos++) {
                //The position of the tile we are getting
                Position absolutePosition = new Position(
                        //The x position of the tile we're drawing
                        entity.getPos().getX() + (-(display.getScreenX() - 1)/2 + xpos),
                        entity.getPos().getY() + (-(display.getScreenY() - 1)/2 + ypos)
                );
                Optional<Tile> tileAtPosition = entity.getWorld().getTileAtPosition(absolutePosition);
                //TODO: Add method for objects to specify their appearance
                if (tileAtPosition.isPresent()) {
                    currentFrame.get(xpos).set(ypos, new TextCharacter('#'));
                }
                List<Entity> entitiesAtPosition = entity.getWorld().getEntitiesAtPosition(absolutePosition);
                if (!entitiesAtPosition.isEmpty()) {
                    currentFrame.get(xpos).set(ypos, new TextCharacter('E'));
                }
                if (absolutePosition.equals(entity.getPos())) {
                    currentFrame.get(xpos).set(ypos, new TextCharacter('@'));
                }

            }
        }
    }
    private void printBottomText() {

    }

    private void blankCanvas() {
        for (int xpos = 0; xpos < display.getScreenX(); xpos++) {
            currentFrame.add(xpos, new ArrayList<>());
            for (int ypos = 0; ypos < display.getScreenY(); ypos++) {
                currentFrame.get(xpos).add(ypos, new TextCharacter(' '));
            }
        }
    }

    @Override
    public void update() {
        if (entity.getWorld().getTick() >= nextMoveTick) {
            switch (controller.getDirection()) {
                case S:
                    entity.getPos().setY(entity.getPos().getY() + 1);
                    break;
                case W:
                    entity.getPos().setX(entity.getPos().getX() - 1);
                    break;
                case E:
                    entity.getPos().setX(entity.getPos().getX() + 1);
                    break;
                case N:
                    entity.getPos().setY(entity.getPos().getY() - 1);
                case NE:
                    break;
                case NW:
                    break;
                case SE:
                    break;
                case SW:
                    break;
                case NONE:
                default:
                    break;
            }
            if (controller.getDirection() != IController.Direction.NONE) {
                nextMoveTick = entity.getWorld().getTick() + (entity.body.getLivingComponent().isPresent() ? 100L / (long)entity.body.getLivingComponent().get().getMovementSpeed()  : 1L);
            }
        }

    }
}
