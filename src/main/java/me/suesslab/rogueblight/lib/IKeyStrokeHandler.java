package me.suesslab.rogueblight.lib;

import com.googlecode.lanterna.input.KeyStroke;
import jdk.internal.jline.internal.Nullable;

public interface IKeyStrokeHandler {
    public void handleInput(@Nullable KeyStroke keyStroke);
}
