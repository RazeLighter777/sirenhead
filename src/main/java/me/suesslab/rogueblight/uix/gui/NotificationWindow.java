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
import sun.font.Decoration;

public class NotificationWindow extends ThreadedFragment {

    Panel notificationBox;
    Button okButton;

    public NotificationWindow(String header, String message, Screen screen) {
        notificationBox = Components.panel().withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withSize(screen.getSize().minus(Size.create(10,10)))
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build();
        notificationBox.requestFocus();
        okButton = Components.button()
                .withAlignmentWithin(notificationBox, ComponentAlignment.BOTTOM_CENTER)
                .withText("OK")
                .build();
        notificationBox.addComponent(okButton);

        notificationBox.addComponent(Components
                .listItem().withText(message)
                .withAlignmentWithin(notificationBox, ComponentAlignment.CENTER)
                .build());
        notificationBox.addComponent(Components.header()
                .withAlignmentWithin(notificationBox, ComponentAlignment.TOP_CENTER)
                .withText(header)
                .build());
        okButton.handleComponentEvents(ComponentEventType.ACTIVATED, componentEvent -> {
            finish();
            return UIEventResponse.processed();
        });
    }

    @NotNull
    @Override
    public Component getRoot() {
        return notificationBox;
    }
}
