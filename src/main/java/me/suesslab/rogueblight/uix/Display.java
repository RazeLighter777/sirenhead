/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.lib.io.IKeyStrokeHandler;
import me.suesslab.rogueblight.lib.io.IKeyStrokeSupplier;
import me.suesslab.rogueblight.lib.ISubsystem;
import me.suesslab.rogueblight.lib.io.TerminalPollingKeyStrokeSupplier;
import me.suesslab.rogueblight.uix.gui.*;

/**
 * @author justin
 */
public class Display implements ISubsystem {


    private SubsystemManager manager;
    private IKeyStrokeSupplier strokeSupplier;

    public Terminal getTerminal() {
        return terminal;
    }

    private Terminal terminal;
    private Screen screen;
    private MultiWindowTextGUI gui;
    private IFrameProvider frameProvider;
    private final static Object displayLock = new Object();
    private Thread thread;
    private IKeyStrokeHandler strokeHandler;

    public boolean isMenuOpen() {
        if (gui.getWindows().isEmpty()) {
            isMenuOpen = false;
        }
        return isMenuOpen;
    }

    private volatile boolean isMenuOpen = false;

    public Display() {
        setFrameProvider(new NullFrameProvider());
        thread = new Thread(new ScreenRendererThread());
    }

    public void setStrokeHandler(IKeyStrokeHandler strokeHandler) {
        this.strokeHandler = strokeHandler;
    }

    public void setStrokeSupplier(IKeyStrokeSupplier strokeSupplier) {
        this.strokeSupplier = strokeSupplier;
    }

    private Terminal createTerminal() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal term = defaultTerminalFactory.createTerminal();
        //SwingTerminalFrame term = defaultTerminalFactory.createSwingTerminal();
        //SwingTerminalFrame terminal = new SwingTerminalFrame("Game", TerminalEmulatorAutoCloseTrigger.CloseOnEscape);
        //terminal.setSize(100, 100);
        //terminal.setVisible(true);
        return term;
    }

    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
        try {
            terminal = createTerminal();
            manager.getLogger().info("Terminal created");
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            gui = new MultiWindowTextGUI(screen);
            strokeSupplier = new TerminalPollingKeyStrokeSupplier(terminal);
            //terminal.requestFocus();
            //terminal.setFocusable(true);
            //terminal.setFocusTraversalKeysEnabled(false);
        } catch (IOException e) {
            manager.getLogger().severe("Unable to create terminal");
        }
        thread.start();
    }


    public void addMultipleStringSelectionWindow(String promptName, List<String> choices, MultiStringSelectionWindow.Callback callback, boolean blocking) {
        MultiStringSelectionWindow window = new MultiStringSelectionWindow(promptName, choices, callback);
        if (blocking) {
            gui.addWindowAndWait(window);
        } else {
            isMenuOpen = true;
            gui.addWindow(new MultiStringSelectionWindow(promptName, choices, callback));
        }
    }

    public void addStringSelectionWindow(String promptName, List<String> choices, StringSelectionWindow.Callback t, boolean blocking) {
        StringSelectionWindow window = new StringSelectionWindow(promptName, choices, t);
        if (blocking) {
            gui.addWindowAndWait(window);
        } else {
            isMenuOpen = true;
            gui.addWindow(window);
        }
    }

    public void addMessage(String header, String message, boolean blocking) {
        NotificationWindow window = new NotificationWindow(header, message);
        if (blocking) {
            gui.addWindowAndWait(window);
        } else {
            isMenuOpen = true;
            gui.addWindow(window);
        }
    }

    public void addItemSelectionWindow(String promptName, List<Item> choices, MultiItemSelectionWindow.Callback callback, boolean blocking) {
        MultiItemSelectionWindow itemSelectionWindow = new MultiItemSelectionWindow(promptName, choices, callback);
        if (blocking) {
            gui.addWindowAndWait(itemSelectionWindow);
        } else {
            isMenuOpen = true;
            gui.addWindow(new MultiItemSelectionWindow(promptName, choices, callback));
        }
    }

    protected class ScreenRendererThread implements Runnable {

        private volatile boolean running = true;

        public void terminate() {
            running = false;
        }

        public void run() {
            while (running) {
                 Optional<List<List<TextCharacter>>> frame = frameProvider.getFrame();
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (1.0 / (double) getRefreshRate() * 1000));
                } catch (InterruptedException e) {
                    manager.getLogger().warning("Interrupted exception on frame");
                }



                try {

                    if (isMenuOpen()) {
                        gui.updateScreen();
                    } else {
                        screen.setCursorPosition(null);
                        frameProvider.getFrame().ifPresent(Display.this::drawFrame);
                    }
                    if (strokeHandler != null) {
                        KeyStroke stroke = strokeSupplier.getKeyStroke();
                        if ((isMenuOpen()) && stroke!=null) {
                            gui.handleInput(stroke);
                        }
                        if (!isMenuOpen() && stroke!=null) {
                            strokeHandler.handleInput(stroke);
                        }
                    }
                    screen.refresh();
                    screen.doResizeIfNecessary();
                    terminal.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    public void setFrameProvider(IFrameProvider frameProvider) {
        this.frameProvider = frameProvider;
    }


    public void closeGui() {
        for (Window w : gui.getWindows()) {
            w.close();
        }
    }
    protected void drawFrame(List<List<TextCharacter>> frame) {
        synchronized (displayLock) {
            //Drop the frame if it doesn't fit the screen
            if (frame.size() != getScreenX()) {
                manager.getLogger().warning("Frame dropped, out of x bounds.");
                return;
            }
            if (frame.get(0).size() != getScreenY()) {
                manager.getLogger().warning("Frame dropped, out of y bounds");
                return;
            }
            for (int xpos = 0; xpos < getScreenX(); xpos++) {
                for (int ypos = 0; ypos < getScreenY(); ypos++) {
                    TextCharacter result = new TextCharacter(' ');
                    try {
                        result = frame.get(xpos).get(ypos);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        manager.getLogger().warning("Tried to draw out of bounds character on " + xpos + " " + ypos);
                    }
                    screen.setCharacter(xpos, ypos, result);

                }
            }
        }
    }

    public int getRefreshRate() {
        return Integer.parseInt(ResourceBundle.getBundle("Game").getString("refreshRate"));
    }


    public int getScreenX() {
        return screen.getTerminalSize().getColumns();
    }

    public int getScreenY() {
        return screen.getTerminalSize().getRows();
    }

    public String fileSelectionDialog(String title, String desc) {
        File input = new FileDialogBuilder()
                .setTitle(title)
                .setDescription(desc)
                .setActionLabel("Open")
                .build()
                .showDialog(gui);
        if (input==null) {
            return "";
        }
        return input.getAbsolutePath();
    }

    public void listMessage(String promptName, List<String> items, boolean blocking) {
        if (blocking) {
            gui.addWindowAndWait(new ListMessageWindow(promptName, items));
        } else {
            isMenuOpen = true;
            gui.addWindow(new ListMessageWindow(promptName, items));
        }
    }
    @Override
    public void stop() {
        //TODO: Remove deprecated method.
        thread.stop();
        try {
            terminal.clearScreen();
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
