package me.suesslab.rogueblight.uix.gui;

import com.googlecode.lanterna.gui2.RadioBoxList;
import kotlin.Unit;
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

/**
 * THIS CODE IS ACCURSED!
 * I'm not sure how it works but it does,
 * I think putting radiobuttons in a scrollbar isn't supported yet and I did it in a hacky way.
 * I redrew the components in the box every time the scrollbar moves.
 * basically the buttons / scrollbar would get shifted for no reason when I moved the screen, crashing the program.
 * Somehow creating a locked position for them worked well.
 * //TODO:FIX this bullshit
 */
public class StringSelectionWindow extends StandardWindow {

    Callback callback;
    Button button;
    RadioButtonGroup radioBoxList;
    VBox vbox;
    //Buttons correspond with choices.
    List<String> choices;
    List<RadioButton> buttons;
    List<Component> components;
    ScrollBar scrollBar;
    Position lockedScrollBarPosition, lockedButtonPosition;
    public interface Callback {
        public void callback(Integer selection);
    }
    public StringSelectionWindow(String message, List<String> choices, Screen screen, Callback callback) {
        super(message, screen);
        this.choices = choices;
        this.callback = callback;
        buttons = new ArrayList<>();
        components = new ArrayList<>();
        vbox = getVbox();
        button = Components.button().withText("OK").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();

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
        scrollBar = (Components.verticalScrollbar().withSize(1, box.getHeight() - 2 ).withAlignmentWithin(box, ComponentAlignment.LEFT_CENTER).withNumberOfScrollableItems(choices.size() + 3).build());
        scrollBar.onValueChange(value -> {
            System.out.println(value.getNewValue());
            renderPage(value.getNewValue());
            return Unit.INSTANCE;
        });

        components.add(scrollBar);
        components.add(vbox);
        components.add(button);
        lockedScrollBarPosition = scrollBar.getPosition();
        lockedButtonPosition = button.getPosition();
        scrollBar.setCurrentValue(6);
        //renderPage(0);
    }

    private void renderPage(Integer progress) {
        box.clear();
        //Replace the vbox
        VBox newVbox = getVbox();
        components.remove(vbox);
        components.add(newVbox);
        vbox = newVbox;
        radioBoxList = Components.radioButtonGroup().build();
        //Add the radioboxes to the new vbox
        for (int i = progress; (i < (progress + vbox.getHeight() - 2)) && (i < choices.size()); i++) {
            RadioButton radioButton = Components.radioButton()
                    .withText(choices.get(i))
                    .withKey("" + i)
                    .build();
            radioBoxList.addComponent(radioButton);
            newVbox.addComponent(radioButton);
        }
        scrollBar.moveTo(lockedScrollBarPosition);
        button.moveTo(lockedButtonPosition);
        System.out.println(button.getPosition());
        components.forEach(box::addComponent);
    }

    private VBox getVbox() {
        return Components.vbox()
                .withSize(box.getSize().minus(Size.create(4,4)))
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .build();
    }


}
