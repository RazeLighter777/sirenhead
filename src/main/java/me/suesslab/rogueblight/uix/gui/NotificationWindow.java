package me.suesslab.rogueblight.uix.gui;

import org.hexworks.zircon.api.ComponentAlignments;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.jetbrains.annotations.NotNull;

public class NotificationWindow extends ThreadedFragment {

    VBox notificationBox;
    Button okButton;

    public NotificationWindow(String header, String message, Position pos) {
        notificationBox = Components.vbox().withSize(message.length() + 7,5).withPosition(pos).build();
        notificationBox.addComponent(Components.header().withAlignment(ComponentAlignments.INSTANCE.alignmentWithin(notificationBox,ComponentAlignment.TOP_CENTER)).withText(header).build());
        notificationBox.addComponent(Components.label().withText(message).withSize(message.length() + 5, 1).withAlignment(ComponentAlignments.INSTANCE.alignmentWithin(notificationBox, ComponentAlignment.CENTER)).build());
        okButton = Components.button().withPosition(Position.create(0,1).relativeToTopOf(notificationBox)).withSize(4,1).withText("OK").build();
        okButton.handleComponentEvents(ComponentEventType.ACTIVATED, componentEvent -> {
            finish();
            return UIEventResponse.processed();
        });
        notificationBox.addComponent(okButton);
    }

    @NotNull
    @Override
    public Component getRoot() {
        return notificationBox;
    }
}
