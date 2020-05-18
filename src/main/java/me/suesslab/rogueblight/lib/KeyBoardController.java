package me.suesslab.rogueblight.lib;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;


import java.io.IOException;


public class KeyBoardController implements IController {

    private Direction currentDirection = Direction.NONE;
    private Terminal inputTerminal;

    public KeyBoardController(Terminal inputTerminal) {
        this.inputTerminal = inputTerminal;
    }

    @Override
    public Direction getDirection() {
        try {
            KeyStroke keyStoke = inputTerminal.pollInput();
            if (keyStoke == null) {
                return Direction.NONE;
            }
            KeyType keyType = keyStoke.getKeyType();
            switch (keyType) {
                case ArrowDown:
                    return Direction.S;
                case ArrowUp:
                    return Direction.N;
                case ArrowLeft:
                    return Direction.W;
                case ArrowRight:
                    return Direction.E;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Direction.NONE;

    }

    @Override
    public boolean pickupKeyPressed() {
        KeyStroke keyStoke = null;
        try {
            keyStoke = inputTerminal.pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (keyStoke == null) {
            return false;
        } else {
            if (keyStoke.getKeyType().equals(KeyType.Character)) {
                if (keyStoke.getCharacter() == 'g') {
                    return true;
                }
            }
        }
        return false;
    }


}
