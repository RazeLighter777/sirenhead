package me.suesslab.rogueblight.lib.io;

import me.suesslab.rogueblight.lib.Direction;
import org.hexworks.zircon.api.uievent.*;

public interface IInputHandler {

    public abstract class callback {
        public abstract UIEventResponse callback(UIEvent event);
    }

    public void registerCallback(UIEventType type, callback callback);

    public UIEventResponse handleKeyboardEvent(KeyboardEvent event);

    public UIEventResponse handleMouseEvent(MouseEvent event);

}
