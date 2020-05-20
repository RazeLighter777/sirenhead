package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.gui2.*;

import java.util.Arrays;

public class NotificationWindow extends BasicWindow {

    public NotificationWindow(String header, String notification) {
        super(header);
        setHints(Arrays.asList(Hint.CENTERED));
        Panel panel = new Panel();
        Label label = new Label(notification);
        Button button = new Button("OK", () -> close());
        panel.addComponent(label);
        panel.addComponent(button);
        setComponent(panel);
    }
}
