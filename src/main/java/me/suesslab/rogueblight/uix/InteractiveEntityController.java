package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.entity.EntityController;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.interact.implementations.DropInteraction;
import me.suesslab.rogueblight.interact.implementations.PickupInteraction;
import me.suesslab.rogueblight.interact.implementations.ThrowLaunchInteraction;
import me.suesslab.rogueblight.item.Inventory;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.item.itemcontainer.ItemContainer;
import me.suesslab.rogueblight.lib.Vector;
import me.suesslab.rogueblight.lib.Position;
import me.suesslab.rogueblight.literary.StringLog;
import me.suesslab.rogueblight.tile.Tile;

import java.util.*;

public class InteractiveEntityController extends EntityController implements IFrameProvider {

    private Entity entity;
    private Display display;
    private SubsystemManager manager;
    private List<List<TextCharacter>> currentFrame;
   //private IActionSupplier controller;
    private long nextMoveTick = 0;
    private StringLog relevantInteractionsLog;
    private boolean freeCursorMode = false;
    private Vector cursorPosition = new Vector(0,0);
    public InteractiveEntityController(Entity e, Display display) {
        super(e);
        //this.controller = controller;
        this.display = display;
        currentFrame = new ArrayList<>();
        this.entity = e;
        relevantInteractionsLog = new StringLog(100);
        e.getData().addProperty("isLocalPlayer", true);
    }

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        currentFrame.clear();
        blankCanvas();
        constructMap();
        drawBottomLog();
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
                System.out.println(new Position((int)((display.getScreenX()-1) * cursorPosition.getX()), (int)((display.getScreenY()-1) * cursorPosition.getY())).getJSON().toString());
                if (new Position(xpos, ypos).equals(new Position((int)((display.getScreenX()-1) * cursorPosition.getX()), (int)((display.getScreenY()-1) * cursorPosition.getY())))) {
                    currentFrame.get(xpos).set(ypos, currentFrame.get(xpos).get(ypos).withBackgroundColor(TextColor.ANSI.YELLOW));
                }
            }
        }
    }

    private void blankCanvas() {
        for (int xpos = 0; xpos < display.getScreenX(); xpos++) {
            currentFrame.add(xpos, new ArrayList<>());
            for (int ypos = 0; ypos < display.getScreenY(); ypos++) {
                currentFrame.get(xpos).add(ypos, new TextCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            }
        }
    }

    private void drawBottomLog() {
        if (relevantInteractionsLog.size() > 0) {
            int ypos = display.getScreenY() - 1;
            for (int xpos = 0; (xpos < display.getScreenX()) && (xpos < relevantInteractionsLog.getTop().length()); xpos++) {
                currentFrame.get(xpos).set(ypos, new TextCharacter(relevantInteractionsLog.getTop().charAt(xpos), TextColor.ANSI.CYAN, TextColor.ANSI.BLACK));
            }
        }

    }

    @Override
    public void update() {
        if (entity.getWorld().getTick() >= nextMoveTick) {
            //if a movement key is pressed, move.
            if (!freeCursorMode) {
                if (updateMovement()) {
                    return;
                }
            } else {
                if (updateCursorMovement()) {
                    return;
                }
            }
            //If the free cursor key is pressed, free the cursor.
            //if (controller.freeCursorKeyPressed()) {
            //    freeCursorMode = !freeCursorMode;
            //    System.out.println("Free cursor mode: " + freeCursorMode);
            //}

            //if the pickup item key is pressed, pickup the item.
            if (openPickupMenu()) {
                return;
            }
            //If the drop menu key is pressed, open up the drop menu.
            if (openDropMenu()) {
                return;
            }
            if (openLogMenu()) {
                return;
            }
            if (openThrowItemMenu()) {
                return ;
            }
            //if there are items to drop drop them.
            if (itemDropQueue != null) {
                dropQueuedItems();
            }

            //if there are items to pickup pick them up
            if (pickupItemQueue != null) {
                pickupQueuedItems();
            }
            //if there are items to throw, throw them.
            if (throwItemQueue != null) {
                throwQueuedItem();
            }
        }
    }

    private boolean updateCursorMovement() {
        //Make sure the cursor position is between 0 and 1
        if (cursorPosition.getY() > 1d) {
            cursorPosition.setY(1d);
        }
        if (cursorPosition.getX() > 1d) {
            cursorPosition.setX(1d);
        }
        if (cursorPosition.getX() < 0d) {
            cursorPosition.setX(0d);
        }
        if (cursorPosition.getY() < 0d) {
            cursorPosition.setY(0d);
        }
        //Alter the cursor position based on the values here
        //switch(controller.getDirection()) {
        //    case N:
        //        cursorPosition.add(new Vector(0,-.1d));
        //        return true;
        //    case S:
        //        cursorPosition.add(new Vector(0, .1d));
        //        return true;
        //    case W:
        //        cursorPosition.add(new Vector(-.1d, 0));
        //        return true;
        //    case E:
        //        cursorPosition.add(new Vector(.1d, 0));
        //        return true;
        //}
        //return false;
    }

    private volatile List<Item> itemDropQueue = null;
    //Item Drop code. Returns the entity as an item container on the ground
    private Entity dropItem(UUID item) {
        Inventory inv = entity.body.getInventoryComponent().get();
        ItemContainer itemContainer = new ItemContainer();
        Entity result = itemContainer.create(inv,item,entity.getWorld(),entity.getPos());
        entity.sendInteraction(new DropInteraction(entity, result.body.getInventoryComponent().get().getItemByUUID(item).get()));
        entity.getWorld().createEntityInWorld(result);
        return result;
    }
    private void dropInventoryItemCallback(List<Item> drops) {
        itemDropQueue = drops;
    }
    private void dropQueuedItems() {
        Iterator<Item> itemIterator = itemDropQueue.iterator();
        while (itemIterator.hasNext()) {
            Item i = itemIterator.next();
            dropItem(i.getUUID());
            nextMoveTick = entity.getWorld().getTick() + 5;
        }
        itemDropQueue = null;
    }
    public boolean openDropMenu() {
        if ((controller.dropKeyPressed()) && (display.isMenuOpen() == false)) {
            Inventory i = entity.body.getInventoryComponent().get();
 //           display.addItemSelectionWindow("Drop which items?", i.getItems(),this::dropInventoryItemCallback, false);
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
        if (controller.getDirection() != IActionSupplier.Direction.NONE) {
            //Set the nextMoveTick based upon the distance rule and the movement speed.
            nextMoveTick = entity.getWorld().getTick() + (long)(oldPos.distanceTo(entity.getPos()) * (double)(entity.body.getLivingComponent().isPresent() ? 100L / (long) entity.body.getLivingComponent().get().getMovementSpeed() : 1L));
            return true;
        }
        return false;
    }

    //Item pickup code
    //Item Drop code.
    private volatile List<Item> pickupItemQueue = null;
    private void pickupItemCallback(List<Item> pickups) {
        pickupItemQueue = pickups;
    }
    private void pickupQueuedItems() {
        Iterator<Item> itemIterator = pickupItemQueue.iterator();
        while (itemIterator.hasNext()) {
            Item i = itemIterator.next();
            entity.sendInteraction(new PickupInteraction(entity, i.getParent().getParent()));
            Inventory.transferItem(i.getParent
                    (), entity.body.getInventoryComponent().get(), i);
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
                //display.addItemSelectionWindow("Pick up which items?", itemsAtPosition,this::pickupItemCallback, false);
                return true;
            }

        }
        return false;
    }

    public boolean openLogMenu() {
        if (controller.logKeyPressed() && !display.isMenuOpen()) {
            display.listMessage("Event Log", relevantInteractionsLog.getLog(), false);
            return true;
        }
        return false;
    }

    @Override
    public void handleInteraction(Interaction i) {
        relevantInteractionsLog.log(i);
    }


    //throw item code
    private volatile Item throwItemQueue = null;
    private void throwItem(UUID item, Vector force) {
        Entity thrownItem = dropItem(item);
        ThrowLaunchInteraction interaction = new ThrowLaunchInteraction(entity, thrownItem);
        entity.sendInteraction(interaction);
        thrownItem.body.getPhysicalComponent().ifPresent(component -> component.impartForce(interaction, force));
    }
    private void throwItemCallback(Optional<Item> throwableItem) {
        throwableItem.ifPresent(item -> throwItemQueue=item);
    }
    private void throwQueuedItem() {
        if (throwItemQueue != null) {
            //Calculate strength and throw the item.
            throwItem(throwItemQueue.getUUID(), entity.body.getLivingComponent().isPresent() ? new Vector(Math.cos(entity.body.getLivingComponent().get().getOrientationAngle()), Math.sin(entity.body.getLivingComponent().get().getOrientationAngle())).multiply(entity.body.getLivingComponent().get().getThrowingStrength()) : new Vector(1,1));
        }
        throwItemQueue = null;
    }
    private boolean openThrowItemMenu() {
        if (!display.isMenuOpen() && controller.throwKeyPressed() && entity.body.getInventoryComponent().isPresent()) {
            Inventory i = entity.body.getInventoryComponent().get();
            //display.addItemSelectionWindow("Throw which item?", "Select item", i.getItems(), this::throwItemCallback, false);
            return true;
        }
        return false;
    }
}

