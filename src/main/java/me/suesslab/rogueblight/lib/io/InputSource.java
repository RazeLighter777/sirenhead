package me.suesslab.rogueblight.lib.io;

public abstract class InputSource {
    protected InputHandler inputHandler;

    public InputSource(InputHandler handler) {
        inputHandler = handler;
    }

}
