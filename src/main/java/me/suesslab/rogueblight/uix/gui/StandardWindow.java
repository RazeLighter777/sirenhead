package me.suesslab.rogueblight.uix.gui;

import org.hexworks.zircon.api.ComponentDecorations;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.component.Component;
import org.hexworks.zircon.api.component.ComponentAlignment;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.screen.Screen;
import org.jetbrains.annotations.NotNull;

public abstract class StandardWindow extends  ThreadedFragment {
    protected Panel box;
    protected String header;
    public StandardWindow(String header, Screen screen) {
        this.header = header;
        box = Components.panel()
                .withSize(screen.getSize().minus(Size.create(10,10)))
                .withDecorations(ComponentDecorations.box(BoxType.DOUBLE))
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build();
        box.addComponent(Components.header().withText(header).withAlignmentWithin(box, ComponentAlignment.TOP_CENTER).build());
        //box.clear();
    }

    @NotNull
    @Override
    public final Component getRoot() {
        return box;
    }
}
