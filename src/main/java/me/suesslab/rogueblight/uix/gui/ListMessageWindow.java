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
import org.jetbrains.annotations.NotNull;
import sun.tools.jconsole.Plotter;

import java.util.List;

public class ListMessageWindow extends ThreadedFragment {

    Panel box;
    Button button;
    LogArea logArea;
    List<String> items;
    int currentPage = 0;
    int pageNumbers = 0;
    public ListMessageWindow(String message, List<String> items, Screen screen) {
        this.items = items;
        box = Components.panel()
                .withSize(screen.getSize().minus(Size.create(10,10)))
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build();
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
    @NotNull
    @Override
    public Component getRoot() {
        return box;
    }

    public void renderPage(int progress) {
        logArea.clear();
        for (int i = progress; (i < (progress + logArea.getHeight() - 2)) && (i < items.size()); i++) {
            logArea.addListItem(items.get(i));
        }
    }
}
