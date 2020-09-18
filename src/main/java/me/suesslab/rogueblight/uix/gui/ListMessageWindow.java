package me.suesslab.rogueblight.uix.gui;

import kotlin.Unit;
import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged;
import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.builder.component.LogAreaBuilder;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.hexworks.zircon.api.uievent.ComponentEventType;
import org.hexworks.zircon.api.uievent.UIEventResponse;

import java.util.List;

public class ListMessageWindow extends StandardWindow {

    Button button;
    LogArea logArea;
    protected List<String> items;
    int currentPage = 0;
    int pageNumbers = 0;
    public ListMessageWindow(String message, List<String> items, Screen screen) {
        super(message, screen);
        this.items = items;
        button = Components.button().withText("OK").withAlignmentWithin(box, ComponentAlignment.BOTTOM_CENTER).build();
        button.handleComponentEvents(ComponentEventType.ACTIVATED, componentEvent -> {
            finish();
            return UIEventResponse.pass();
        });
        logArea = Components.logArea()
                .withSize(box.getWidth() - 4, box.getHeight() -4)
                .withAlignmentWithin(box, ComponentAlignment.CENTER)
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .build();
        box.addComponent(logArea);
        box.addComponent(button);
        ScrollBar scrollBar = (Components.verticalScrollbar().withSize(1, box.getHeight() -2 ).withAlignmentWithin(box, ComponentAlignment.LEFT_CENTER).withNumberOfScrollableItems(items.size() + 3).build());
        scrollBar.onValueChange(value -> {
            System.out.println(value.getNewValue());
            renderPage(value.getNewValue());
            return Unit.INSTANCE;
        });
        renderPage(0);
        box.addComponent(scrollBar);
    }

    public void renderPage(int progress) {
        logArea.clear();
        for (int i = progress; (i < (progress + logArea.getHeight() - 2)) && (i < items.size()); i++) {
            logArea.addListItem(items.get(i));
        }
    }
}
