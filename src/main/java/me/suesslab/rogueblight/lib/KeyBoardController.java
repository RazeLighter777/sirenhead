package me.suesslab.rogueblight.lib;

import me.suesslab.rogueblight.uix.IController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardController implements IController, KeyListener {
    private Direction currentDirection = Direction.NONE;
    @Override
    public Direction getDirection() {
        return currentDirection;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keycode = keyEvent.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_UP:
                currentDirection = Direction.N;
                break;
            case KeyEvent.VK_DOWN:
                currentDirection = Direction.S;
                break;
            case KeyEvent.VK_LEFT:
                currentDirection = Direction.W;
                break;
            case KeyEvent.VK_RIGHT:
                currentDirection = Direction.E;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keycode = keyEvent.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                currentDirection = Direction.NONE;
                break;
        }
    }

}
