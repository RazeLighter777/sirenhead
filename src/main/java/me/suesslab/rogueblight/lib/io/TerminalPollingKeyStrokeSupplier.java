package me.suesslab.rogueblight.lib.io;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class TerminalPollingKeyStrokeSupplier implements  IKeyStrokeSupplier {
    Terminal terminal;

    public TerminalPollingKeyStrokeSupplier(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public KeyStroke getKeyStroke() {
        try {
            return terminal.pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
