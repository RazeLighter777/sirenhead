package me.suesslab.rogueblight.lib.io;

import org.hexworks.zircon.api.uievent.KeyboardEvent;
import org.hexworks.zircon.api.uievent.MouseEvent;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.hexworks.zircon.api.uievent.UIEventType;

public class KeyBoardEventHandler implements  IInputHandler {
    @Override
    public void registerCallback(UIEventType type, callback callback) {

    }

    @Override
    public UIEventResponse handleKeyboardEvent(KeyboardEvent event) {
        return null;
    }

    @Override
    public UIEventResponse handleMouseEvent(MouseEvent event) {
        return null;
    }
}
