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
    Button button, next, prev;
    VBox vbox;
    int page;
    //Buttons correspond with choices.
    List<String> choices;
    RadioButtonGroup radioButtonGroup;
    List<Component> components;

    public interface Callback {
        void callback(Integer selection);
    }
    public StringSelectionWindow(String message, List<String> choices, Screen screen, Callback callback) {
        super(message, screen);
        components = new ArrayList<>();
        this.choices = choices;
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
        radioButtonGroup = Components.radioButtonGroup().build();
        //Add the radioboxes to the new vbox
        for (int i = 0; i < (vbox.getHeight() - 2); i++) {
            int actualItem = page * (vbox.getHeight() - 2) + i;
            if (actualItem < choices.size()) {
                RadioButton rb = Components.radioButton().withKey(""  + (actualItem + 1)).withText("#" + actualItem + " : " + choices.get(i)).build();
                radioButtonGroup.addComponent(rb);
                vbox.addComponent(rb);
            }
        }
        button = Components.button().withText("OK").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        next = Components.button().withText("NEXT >").withSize(8,1).withAlignmentWithin(box, ComponentAlignment.TOP_RIGHT).build();
        prev = Components.button().withText("< PREV").withSize(8,1).withAlignmentAround(next, ComponentAlignment.LEFT_CENTER).build();
        button.handleComponentEvents(ComponentEventType.ACTIVATED, event -> {
            if (radioButtonGroup.getSelectedButton().isPresent()) {
                System.out.println(Integer.parseInt(radioButtonGroup.getSelectedButton().get().getKey()) - 1 );
                callback.callback(Integer.parseInt(radioButtonGroup.getSelectedButton().get().getKey()) - 1 );
                finish();
            }
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
