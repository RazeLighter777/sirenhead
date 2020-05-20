package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.gui2.*;
import me.suesslab.rogueblight.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringSelectionWindow extends BasicWindow {
    private RadioBoxList box;
    public interface Callback {
        public void run(Integer optionsSelected);
    }
    public StringSelectionWindow(String category, List<String> options, Callback t) {
        super(category);
        setHints(Arrays.asList(Window.Hint.CENTERED));
        box = new RadioBoxList<>();
        Panel panel = new Panel();
        panel.addComponent(box);
        for (String i : options) {
            box.addItem(i);
        }
        panel.addComponent(new Button("OK", ()-> {t.run(box.getCheckedItemIndex()); close();}));
        panel.addComponent(new Button("CANCEL", () -> {t.run(-1); close();}));
        //setSize(new TerminalSize(10, 5));
        setComponent(panel);
    }
}
