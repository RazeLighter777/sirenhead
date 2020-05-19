package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.ArrayList;
import java.util.List;


public class MultiStringSelectionWindow extends BasicWindow {
    CheckBoxList<String> box;
    public interface Callback {
        public void run(List<Integer> optionsSelected);
    }
    private List<Integer> getCheckedItemIndices() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < box.getItemCount(); i++) {
            if (box.isChecked(i)) {
                result.add(i);
            }
        }
        return result;
    }
    public MultiStringSelectionWindow(String category, List<String> options, Callback t) {
        super(category);
        box = new CheckBoxList<>();
        Panel panel = new Panel();
        panel.addComponent(box);
        for (String s : options) {
            box.addItem(s);
        }
        panel.addComponent(new Button("OK", ()-> {t.run(getCheckedItemIndices()); close();}));
        setSize(new TerminalSize(10, 5));
        setComponent(panel);
    }
}
