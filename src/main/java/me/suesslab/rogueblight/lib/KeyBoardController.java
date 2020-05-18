package me.suesslab.rogueblight.lib;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;


import java.io.IOException;
import java.util.Optional;

import static com.googlecode.lanterna.input.KeyType.ArrowDown;
import static com.googlecode.lanterna.input.KeyType.ArrowUp;


public class KeyBoardController implements IKeyPressDetector {

    private Terminal inputTerminal;
    private Optional<KeyStroke> currentKey;

    public KeyBoardController(Terminal inputTerminal) {
        this.inputTerminal = inputTerminal;
        currentKey = Optional.empty();
    }

    private Optional<KeyStroke> getCurrentKey() {
        if (!currentKey.isPresent()) {
            getNextKey();
        }
        return currentKey;
    }
    private void getNextKey() {
        KeyStroke nextKey = null;
        try {
            nextKey = inputTerminal.pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (nextKey == null) {
            currentKey = Optional.empty();
        } else {
            currentKey = Optional.of(nextKey);
        }
    }
    @Override
    public Direction getDirection() {
        Optional<KeyStroke> key = getCurrentKey();
        if (key.isPresent()) {
            switch (key.get().getKeyType()) {
                case ArrowDown:
                    getNextKey();
                    return Direction.S;
                case ArrowUp:
                    getNextKey();
                    return Direction.N;
                case ArrowLeft:
                    getNextKey();
                    return Direction.W;
                case ArrowRight:
                    getNextKey();
                    return Direction.E;
            }
        }
        return Direction.NONE;
    }

    @Override
    public boolean pickupKeyPressed() {
        Optional<KeyStroke> keyStroke = getCurrentKey();
        if (keyStroke.isPresent()) {
            if (keyStroke.get().getCharacter() == 'g') {
                getNextKey();
                return true;
            }
        }
        return false;
    }


}
