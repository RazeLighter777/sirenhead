package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.gui2.*;

import java.util.List;

public class ListMessageWindow  extends BasicWindow  {
    public ListMessageWindow(String header, List<String> message) {
        super(header);
        Panel panel = new Panel();
        for (String s : message) {
            panel.addComponent(new Label(s));
        }
        panel.addComponent(new Button("Close", () -> {close(); }));
        setComponent(panel);
    }
}
