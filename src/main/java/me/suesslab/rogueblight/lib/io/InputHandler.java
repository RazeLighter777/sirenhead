package me.suesslab.rogueblight.lib.io;

import me.suesslab.rogueblight.entity.interactive.InteractiveEntityController;
import org.hexworks.zircon.api.uievent.*;

public abstract class InputHandler {


    protected final InteractiveEntityController interactiveEntityController;

    public InputHandler(InteractiveEntityController interactiveEntityController) {
        this.interactiveEntityController = interactiveEntityController;
    }

    public abstract UIEventResponse handleKeyboardEvent(KeyboardEvent event);

    public abstract UIEventResponse handleMouseEvent(MouseEvent event);

}
