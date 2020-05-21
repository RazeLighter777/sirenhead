package me.suesslab.rogueblight.lib.io;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;


public class KeyBoardController implements IActionSupplier, IKeyStrokeHandler {


    KeyStroke currentEvent;
    Terminal terminal;
    public KeyBoardController(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public Direction getDirection() {
        if ((currentEvent != null) ) {
            switch (currentEvent.getKeyType()) {
                case ArrowUp:
                    currentEvent = null;
                    return Direction.N;
                case ArrowDown:
                    currentEvent = null;
                    return Direction.S;
                case ArrowLeft:
                    currentEvent = null;
                    return Direction.W;
                case ArrowRight:
                    currentEvent = null;
                    return Direction.E;
            }
        }
        return Direction.NONE;
    }

    @Override
    public boolean pickupKeyPressed() {
        return isKeyPressed('g');
    }

    @Override
    public boolean dropKeyPressed() {
        return isKeyPressed('d');
    }

    @Override
    public boolean logKeyPressed() {
        return isKeyPressed('/');
    }

    private boolean isKeyPressed(Character c) {
        if (currentEvent != null) {
            if (currentEvent.getKeyType().equals(KeyType.Character)) {
                if (currentEvent.getCharacter().equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void handleInput(KeyStroke keyStroke) {
        currentEvent = keyStroke;
    }
}
