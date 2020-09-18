package me.suesslab.rogueblight.uix.gui;

import kotlin.Unit;
import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MultiStringSelectionWindow extends StandardWindow {

    Callback callback;
    Button button;
    TextArea textArea;
    LogArea logArea;
    //Buttons correspond with choices.
    List<String> choices;

    public interface Callback {
        public void callback(List<Integer> selection);
    }
    public MultiStringSelectionWindow(String message, List<String> choices, Screen screen, Callback callback) {
        super(message, screen);
        this.choices = choices;
        this.callback = callback;
        textArea = Components.textArea().withSize(10,1).withText("EX: 1-2,4").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        button = Components.button().withText("OK").withAlignmentAround(textArea, ComponentAlignment.RIGHT_CENTER).build();
        button.handleComponentEvents(ComponentEventType.ACTIVATED, event ->
        {
            Optional<List<Integer>> result = parseRanges(textArea.getText());
            if (result.isPresent()) {
                callback.callback(result.get());
                finish();
                return UIEventResponse.pass();
            }
            return UIEventResponse.processed();
        });
        logArea = Components.logArea()
                .withSize(box.getWidth() - 4, box.getHeight() - 4)
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .build();
        box.addComponent(logArea);
        box.addComponent(button);
        box.addComponent(textArea);
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
            logArea.addListItem("" + i + ":\t" + choices.get(i));
        }
    }

    /**
     *
     * @param range The range string
     * @return A list of the selected numbers.
     */
    private Optional<List<Integer>> parseRanges(String range) {
        String[] tokens = range.split(",");
        List<Integer> selectedValues = new ArrayList<>();
        for (String s : tokens) {
            if (s.contains("-")) {
                String[] words = s.split("-");
                if (words.length != 2 || Integer.parseInt(words[1]) > choices.size() || Integer.parseInt(words[0]) < 1 || Integer.parseInt(words[0]) >= Integer.parseInt(words[1])) {
                    return Optional.empty();
                }
                for (int i = Integer.parseInt(words[0]); i < Integer.parseInt(words[1]); i++) {
                    if (!selectedValues.contains(i)) {
                        selectedValues.add(i);
                    }
                }
            } else {
                if (Integer.parseInt(s) > choices.size() || Integer.parseInt(s) < 1) {
                    return Optional.empty();
                }
                if (!selectedValues.contains(Integer.parseInt(s))) {
                    selectedValues.add(Integer.parseInt(s));
                }
            }
        }
        return Optional.of(selectedValues);
    }


}