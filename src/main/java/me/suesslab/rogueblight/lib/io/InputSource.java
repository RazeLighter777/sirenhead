package me.suesslab.rogueblight.lib.io;

public abstract class InputSource {
    protected IInputHandler inputHandler;

    public InputSource(IInputHandler handler) {
        inputHandler = handler;
    }

}
