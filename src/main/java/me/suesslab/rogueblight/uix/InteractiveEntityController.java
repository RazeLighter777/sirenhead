package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.item.ItemContainer;
import me.suesslab.rogueblight.lib.IKeyPressDetector;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.tile.Tile;

import java.util.*;

public class InteractiveEntityController extends EntityController implements IFrameProvider {

    private Entity entity;
    private Display display;
    private SubsystemManager manager;
    private List<List<TextCharacter>> currentFrame;
    private IKeyPressDetector controller;
    private long nextMoveTick = 0;
    public InteractiveEntityController(Entity e, Display display, IKeyPressDetector controller) {
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
                        entity.getPos().getX() + (-(display.getScreenX() - 1) / 2 + xpos),
                        entity.getPos().getY() + (-(display.getScreenY() - 1) / 2 + ypos)
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
            //if a movement key is pressed, move.
            if (updateMovement()) {
                return;
            }
            //if the pickup item key is pressed, pickup the item.
            if (openPickupMenu()) {
                return;
            }
            //If the drop menu key is pressed, open up the drop menu.
            if (openDropMenu()) {
                return;
            }
            //if there are items to drop drop them.
            if (itemDropIndex != null) {
                dropQueuedItems();
            }
            //if there are items to pickup pick them up
            if (pickupItemQueue != null) {
                pickupQueuedItems();
            }
        }
    }



    public void addRelevantInteraction(Interaction e) {
        entity.getWorld().registerInteraction(e);
    }

    private volatile List<Item> itemDropIndex = null;

    //Item Drop code.
    private void dropItem(UUID item) {
        Inventory inv = entity.body.getInventoryComponent().get();
        ItemContainer itemContainer = new ItemContainer();
        entity.getWorld().createEntityInWorld(itemContainer.create(inv,item,entity.getWorld(),entity.getPos()));
    }
    private void dropInventoryItemCallback(List<Item> drops) {
        itemDropIndex = drops;
    }
    private void dropQueuedItems() {
        Iterator<Item> itemIterator = itemDropIndex.iterator();
        while (itemIterator.hasNext()) {
            Item i = itemIterator.next();
            dropItem(i.getUUID());
        }
        nextMoveTick = entity.getWorld().getTick() + 5;
        itemDropIndex = null;
    }
    public boolean openDropMenu() {
        if ((controller.dropKeyPressed()) && (display.isMenuOpen() == false)) {
            Inventory i = entity.body.getInventoryComponent().get();
            display.addItemSelectionWindow("Drop which items?", i.getItems(),this::dropInventoryItemCallback);
            return true;
        }
        return false;
    }



    public boolean updateMovement() {
        Position oldPos = entity.getPos();

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
                break;
            case NE:
                entity.getPos().setY(entity.getPos().getY() - 1);
                entity.getPos().setX(entity.getPos().getX() + 1);
                break;
            case NW:
                entity.getPos().setY(entity.getPos().getY() - 1);
                entity.getPos().setX(entity.getPos().getX() - 1);
                break;
            case SE:
                entity.getPos().setY(entity.getPos().getY() + 1);
                entity.getPos().setX(entity.getPos().getX() + 1);
                break;
            case SW:
                entity.getPos().setY(entity.getPos().getY() + 1);
                entity.getPos().setX(entity.getPos().getX() - 1);
                break;
            case NONE:
            default:
                break;
        }
        if (controller.getDirection() != IKeyPressDetector.Direction.NONE) {
            //Set the nextMoveTick based upon the distance rule and the movement speed.
            nextMoveTick = entity.getWorld().getTick() + (long)(oldPos.distanceTo(entity.getPos()) * (double)(entity.body.getLivingComponent().isPresent() ? 100L / (long) entity.body.getLivingComponent().get().getMovementSpeed() : 1L));
            return true;
        }
        return false;
    }

    //Item pickup code
    //Item Drop code.
    private volatile List<Item> pickupItemQueue = null;
    private void pickupItem(Entity container) {
        Inventory inv = entity.body.getInventoryComponent().get();
    }
    private void pickupItemCallback(List<Item> pickups) {
        pickupItemQueue = pickups;
    }
    private void pickupQueuedItems() {
        Iterator<Item> itemIterator = pickupItemQueue.iterator();
        while (itemIterator.hasNext()) {
            Item i = itemIterator.next();
            Inventory.transferItem(i.getParent(), entity.body.getInventoryComponent().get(), i);
        }
        nextMoveTick = entity.getWorld().getTick() + 5;
        pickupItemQueue = null;
    }
    public boolean openPickupMenu() {
        if ((controller.pickupKeyPressed()) && (display.isMenuOpen() == false)) {
            Inventory i = entity.body.getInventoryComponent().get();
            List<Entity> entitiesAtPosition = entity.getWorld().getEntitiesAtPosition(entity.getPos());
            List<Entity> itemContainersAtPosition = new ArrayList<>();
            for (Entity e : entitiesAtPosition) {
                if (e.body.getPresentedItem().isPresent()) {
                    itemContainersAtPosition.add(e);
                }
            }
            List<Item> itemsAtPosition = new ArrayList<>();
            for (Entity e : itemContainersAtPosition) {
                if (e.body.getInventoryComponent().isPresent()) {
                    if (e.body.getInventoryComponent().get().getItemByUUID(e.body.getPresentedItem().get()).isPresent()) {
                        itemsAtPosition.add(e.body.getInventoryComponent().get().getItemByUUID(e.body.getPresentedItem().get()).get());
                    }
                }
            }
            if (itemsAtPosition.size() == 0) {
                return false;
            }
            else if (itemsAtPosition.size() == 1) {
                pickupItemQueue = itemsAtPosition;
                return true;
            } else {
                display.addItemSelectionWindow("Pick up which items?", itemsAtPosition,this::pickupItemCallback);
                return true;
            }

        }
        return false;
    }

}

