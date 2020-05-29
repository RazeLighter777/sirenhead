package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.gui2.RadioBoxList;
import me.suesslab.rogueblight.interact.Interaction;
import me.suesslab.rogueblight.item.Item;
import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StringSelectionWindow extends StandardWindow {

    Callback callback;
    Button button;
    RadioButtonGroup radioBoxList;
    VBox vbox;
    public interface Callback {
        public void callback(Integer selection);
    }
    public StringSelectionWindow(String message, List<String> choices, Screen screen, Callback callback) {
        super(message, screen);
        this.callback = callback;
        vbox = Components.vbox()
                .withSize(box.getSize().minus(Size.create(4,4)))
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .build();
        button = Components.button().withText("OK").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        radioBoxList = Components.radioButtonGroup()
                .build();
        for (int i = 0; i < choices.size(); i++) {
            RadioButton radioButton = Components.radioButton()
                    .withText(choices.get(i))
                    .withKey(""+i)
                    .build();
            vbox.addComponent(radioButton);
            radioBoxList.addComponent(radioButton);
        }
        button = Components.button().withText("OK").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        button.handleComponentEvents(ComponentEventType.ACTIVATED, componentEvent -> {
            finish();
            Integer selection = -1;
            if (radioBoxList.getSelectedButton().isPresent()) {
                selection = Integer.parseInt(radioBoxList.getSelectedButton().get().getKey());
            }
            callback.callback(selection);
            return UIEventResponse.pass();
        });
        box.addComponent(vbox);
        box.addComponent(button);


    }


}
