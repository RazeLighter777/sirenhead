package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.gui2.*;
import me.suesslab.rogueblight.item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemSelectionWindow extends BasicWindow {
    public interface Callback {
        public void run(Optional<Item> itemSelected);
    }
    public ItemSelectionWindow(String header, String message, List<Item> items, Callback t) {
        super(header);
        setHints(Arrays.asList(Hint.CENTERED));
        RadioBoxList<String> radioBoxList = new RadioBoxList<>();
        Panel panel = new Panel();
        for (Item i : items) {
            radioBoxList.addItem(i.getQualifiedName());
        }
        panel.addComponent(new Label(message));
        panel.addComponent(radioBoxList);
        panel.addComponent(new Button("OK", () -> {
            if (radioBoxList.getCheckedItemIndex() != -1) {
                t.run(Optional.of(items.get(radioBoxList.getCheckedItemIndex())));
            }
            else {
                t.run(Optional.empty());
            }
            close();
        }));
        panel.addComponent(new Button("CANCEL", () -> {
            t.run(Optional.empty());
            close();
        }));
        setComponent(panel);
    }
}
