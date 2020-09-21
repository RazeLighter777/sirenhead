package me.suesslab.rogueblight.uix.gui;

import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.MouseEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MultiStringSelectionWindow extends StandardWindow {

    MultiStringSelectionWindow.Callback callback;
    Button button, next, prev;
    VBox vbox;
    int page;
    //Buttons correspond with choices.
    List<String> choices;
    List<Component> components;
    List<Integer> selections;

    public interface Callback {
        void callback(List<Integer> selections);
    }
    public MultiStringSelectionWindow(String message, List<String> choices, Screen screen, MultiStringSelectionWindow.Callback callback) {
        super(message, screen);
        components = new ArrayList<>();
        this.choices = choices;
        this.selections = new ArrayList<>();
        this.callback = callback;
        vbox = getVbox();
        renderPage();
    }



    private VBox getVbox() {
        return Components.vbox()
                .withSize(box.getSize().minus(Size.create(4,4)))
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .build();
    }

    public void renderPage() {
        box.clear();
        components.clear();
        //Replace the vbox
        VBox newVbox = getVbox();
        components.add(newVbox);
        vbox = newVbox;
        //Add the radioboxes to the new vbox
        for (int i = 0; i < (vbox.getHeight() - 2); i++) {
            int actualItem = page * (vbox.getHeight() - 2) + i;
            if (actualItem < choices.size()) {
                CheckBox cb = Components.checkBox().withText(actualItem + " : " + choices.get(actualItem)).build();
                cb.handleMouseEvents(MouseEventType.MOUSE_PRESSED, (event,some) -> {
                    System.out.println(cb.getCheckBoxState());
                    //YOYOYO SOME BULLSHIT YES THIS IS BACKWARDS BUT IT WORKS SO FUCK IT
                    if (cb.getCheckBoxState().equals(DefaultCheckBox.CheckBoxState.UNCHECKED)) {
                        selections.add(actualItem);
                    } else if (cb.getCheckBoxState().equals(DefaultCheckBox.CheckBoxState.CHECKED)) {
                        selections.remove(Integer.valueOf(actualItem));
                    }
                    return UIEventResponse.pass();
                });
                if (selections.contains(actualItem)) {
                    cb.setSelected(true);
                }
                vbox.addComponent(cb);
            }
        }
        button = Components.button().withText("OK").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        next = Components.button().withText("NEXT >").withSize(8,1).withAlignmentWithin(box, ComponentAlignment.TOP_RIGHT).build();
        prev = Components.button().withText("< PREV").withSize(8,1).withAlignmentAround(next, ComponentAlignment.LEFT_CENTER).build();
        button.handleComponentEvents(ComponentEventType.ACTIVATED, event -> {
            callback.callback(selections);
            finish();
            return UIEventResponse.pass();
        });
        next.handleComponentEvents(ComponentEventType.ACTIVATED, event -> {
            if (page < choices.size() / (vbox.getHeight() - 2)) {
                page++;
                renderPage();
            }
            return UIEventResponse.pass();
        });
        prev.handleComponentEvents(ComponentEventType.ACTIVATED, event -> {
            if (page > 0) {
                page--;
                renderPage();
            }
            return UIEventResponse.pass();
        });
        components.add(Components.header().withText(header).withAlignmentWithin(box, ComponentAlignment.TOP_LEFT).build());
        if (choices.size() / (vbox.getHeight() - 2) > 1 ) {
            components.add(next);
            components.add(prev);

        }
        components.add(button);
        for (Component c : components) {
            box.addComponent(c);
        }
    }



}