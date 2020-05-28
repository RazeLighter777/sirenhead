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

    private IFrameProvider frameProvider;
    private final static Object displayLock = new Object();
    private Thread thread;
    private IKeyStrokeHandler strokeHandler;



    public boolean isMenuOpen() {
        return false;
    }

    private volatile boolean isMenuOpen = false;

    public Display() {
    }

    public void setStrokeHandler(IKeyStrokeHandler strokeHandler) {
        this.strokeHandler = strokeHandler;
    }

    public void setStrokeSupplier(IKeyStrokeSupplier strokeSupplier) {
        this.strokeSupplier = strokeSupplier;
    }



    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
        thread.start();
    }


    public void addMultipleStringSelectionWindow(String promptName, List<String> choices, MultiStringSelectionWindow.Callback callback, boolean blocking) {

    }

    public void addStringSelectionWindow(String promptName, List<String> choices, StringSelectionWindow.Callback t, boolean blocking) {

    }

    public void addMessage(String header, String message, boolean blocking) {

    }

    public void addItemSelectionWindow(String promptName, List<Item> choices, MultiItemSelectionWindow.Callback callback, boolean blocking) {

    }

    protected class ScreenRendererThread implements Runnable {

        private volatile boolean running = true;

        public void terminate() {
            running = false;
        }

        public void run() {
            while (running) {
            }

        }
    }

    public void setFrameProvider(IFrameProvider frameProvider) {
        this.frameProvider = frameProvider;
    }


    public void closeGui() {
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
        return 0;
    }

    public int getScreenY() {
        return 0;
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

    }

    public void addItemSelectionWindow(String promptName, String message, List<Item> items, ItemSelectionWindow.Callback t, boolean blocking) {

    }
    @Override
    public void stop() {
        //TODO: Remove deprecated method.
        thread.stop();
    }

}
