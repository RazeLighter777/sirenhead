package me.suesslab.rogueblight.lib.io;

import me.suesslab.rogueblight.uix.InteractiveEntityController;
import org.hexworks.zircon.api.uievent.*;

public class KeyBoardEventHandler extends InputHandler {


    public KeyBoardEventHandler(InteractiveEntityController interactiveEntityController) {
        super(interactiveEntityController);
    }

    @Override
    public UIEventResponse handleKeyboardEvent(KeyboardEvent event) {
        if (event.getCode().equals(KeyCode.KEY_G)) {
            interactiveEntityController.openPickupMenu();
        }
        else if (event.getCode().equals(KeyCode.KEY_D)) {
            interactiveEntityController.openDropMenu();
        }
        else if (event.getCode().equals(KeyCode.SLASH)) {
            interactiveEntityController.openLogMenu();
        }
        return UIEventResponse.pass();
    }

    @Override
    public UIEventResponse handleMouseEvent(MouseEvent event) {
        return UIEventResponse.pass();
    }
}
