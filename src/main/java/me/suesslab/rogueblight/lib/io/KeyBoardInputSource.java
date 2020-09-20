package me.suesslab.rogueblight.lib.io;

import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.hexworks.zircon.api.uievent.MouseEvent;
import org.hexworks.zircon.api.uievent.MouseEventType;
import org.hexworks.zircon.api.uievent.UIEvent;

public class KeyBoardInputSource extends InputSource {

    protected Screen screen;

    public KeyBoardInputSource(Screen sc, InputHandler inputHandler) {
        super(inputHandler);
        registerInputHandler();
    }

    protected void registerInputHandler() {
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED, (keyboardEvent, uiEventPhase) ->
        {
            return inputHandler.handleKeyboardEvent(keyboardEvent);
        });
        screen.handleMouseEvents(MouseEventType.MOUSE_CLICKED, (mouseEvent, uiEventPhase) ->
        {
            return inputHandler.handleMouseEvent(mouseEvent);
        });
    }
}
