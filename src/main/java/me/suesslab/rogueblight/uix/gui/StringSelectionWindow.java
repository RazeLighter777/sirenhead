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
    NumberInput numberInput;
    LogArea logArea;
    //Buttons correspond with choices.
    List<String> choices;

    public interface Callback {
        public void callback(Integer selection);
    }
    public StringSelectionWindow(String message, List<String> choices, Screen screen, Callback callback) {
        super(message, screen);
        this.choices = choices;
        this.callback = callback;
        numberInput = Components.horizontalNumberInput(4).withSize(4,1).withInitialValue(1).withMaxValue(choices.size()+1).withMinValue(1).withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        button = Components.button().withText("OK").withAlignmentAround(numberInput, ComponentAlignment.RIGHT_CENTER).build();
        button.handleComponentEvents(ComponentEventType.ACTIVATED, event -> {
            System.out.println(numberInput.getCurrentValue() - 1 );
            callback.callback(numberInput.getCurrentValue() - 1);
            finish();
            return UIEventResponse.pass();
        });
        numberInput.requestFocus();
        logArea = Components.logArea()
                .withSize(box.getWidth() - 4, box.getHeight() - 4)
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .build();
        box.addComponent(logArea);
        box.addComponent(button);
        box.addComponent(numberInput);
        ScrollBar scrollBar = (Components.verticalScrollbar().withSize(1, box.getHeight() -2 ).withAlignmentWithin(box, ComponentAlignment.LEFT_CENTER).withNumberOfScrollableItems(choices.size() + 3).build());
        scrollBar.onValueChange(value -> {
            System.out.println(value.getNewValue());
            renderPage(value.getNewValue());
            return Unit.INSTANCE;
        });
        renderPage(0);
        box.addComponent(scrollBar);
    }



    private VBox getVbox() {
        return Components.vbox()
                .withSize(box.getSize().minus(Size.create(4,4)))
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .build();
    }

    public void renderPage(int progress) {
        logArea.clear();
        for (int i = progress; (i < (progress + logArea.getHeight() - 2)) && (i < choices.size()); i++) {
            logArea.addListItem( " " + i+1 + ": " + choices.get(i));
        }
    }


}
