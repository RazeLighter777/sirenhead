package me.suesslab.rogueblight.uix.gui;

import org.hexworks.zircon.api.ComponentAlignments;
import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.modifier.Border;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.jetbrains.annotations.NotNull;

public class NotificationWindow extends StandardWindow {

    Button okButton;

    public NotificationWindow(String header, String message, Screen screen) {
        super(header, screen);
        box.requestFocus();
        okButton = Components.button()
                .withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER)
                .withText("OK")
                .build();
        box.addComponent(okButton);
        box.addComponent(Components
                .listItem().withText(message)
                .withSize(Size.create(30, 4))
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .build());
        okButton.handleComponentEvents(ComponentEventType.ACTIVATED, componentEvent -> {
            finish();
            return UIEventResponse.processed();
        });
    }

}
