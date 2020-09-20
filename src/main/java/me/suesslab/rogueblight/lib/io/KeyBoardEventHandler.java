package me.suesslab.rogueblight.lib.io;

import me.suesslab.rogueblight.uix.InteractiveEntityController;
import org.hexworks.zircon.api.uievent.KeyboardEvent;
import org.hexworks.zircon.api.uievent.MouseEvent;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.hexworks.zircon.api.uievent.UIEventType;

public class KeyBoardEventHandler extends InputHandler {


    public KeyBoardEventHandler(InteractiveEntityController interactiveEntityController) {
        super(interactiveEntityController);
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
