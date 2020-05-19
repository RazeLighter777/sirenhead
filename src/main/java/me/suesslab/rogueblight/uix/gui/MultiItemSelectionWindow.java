package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.Panel;
import me.suesslab.rogueblight.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiItemSelectionWindow extends BasicWindow {
    CheckBoxList<String> box;
    List<Item> options;
    public interface Callback {
        public void run(List<Item> optionsSelected);
    }
    private List<Item> getCheckedItems() {
        ArrayList<Item> result = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (box.isChecked(i)) {
                result.add(options.get(i));
            }
        }
        return result;
    }
    public MultiItemSelectionWindow(String category, List<Item> options, MultiItemSelectionWindow.Callback t) {
        super(category);
        setHints(Arrays.asList(Hint.CENTERED));
        this.options = options;
        box = new CheckBoxList<>();
        Panel panel = new Panel();
        panel.addComponent(box);
        for (Item i : options) {
            box.addItem(i.getQualifiedName());
        }
        panel.addComponent(new Button("OK", ()-> {t.run(getCheckedItems()); close();}));
        panel.addComponent(new Button("CANCEL", () -> {t.run(new ArrayList<>()); close();}));
        //setSize(new TerminalSize(10, 5));
        setComponent(panel);
    }
}
