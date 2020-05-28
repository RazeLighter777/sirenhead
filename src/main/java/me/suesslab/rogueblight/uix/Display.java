/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.suesslab.rogueblight.uix;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.lib.io.IKeyStrokeHandler;
import me.suesslab.rogueblight.lib.io.IKeyStrokeSupplier;
import me.suesslab.rogueblight.lib.ISubsystem;
import me.suesslab.rogueblight.lib.io.TerminalPollingKeyStrokeSupplier;
import me.suesslab.rogueblight.uix.gui.*;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.AttachedComponent;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

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
    private ArrayList<AttachedComponent> attachedComponentList;
    TileGrid tileGrid;
    Screen screen;



    public boolean isMenuOpen() {
        return false;
    }

    private volatile boolean isMenuOpen = false;

    public Display() {
        attachedComponentList = new ArrayList<>();
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
        tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder().withSize(60, 30).withDefaultTileset(CP437TilesetResources.bisasam16x16()).build());
        screen  = Screen.create(tileGrid);
        screen.display();
        screen.setTheme(ColorThemes.arc());

        addMessage("hello", "world", true);
        screen.display();
        thread.start();
    }


    public void addMultipleStringSelectionWindow(String promptName, List<String> choices, boolean blocking) {
        //screen.addComponent(Components);
    }

    public void addStringSelectionWindow(String promptName, List<String> choices, boolean blocking) {

    }

    public void addMessage(String header, String message, boolean blocking) {
        if (blocking) {
            ThreadedFragment tf = new NotificationWindow(header, message, Position.create(0,0));
            AttachedComponent ac = screen.addFragment(tf);
            screen.display();
            while (true) {
                if (tf.isFinished()) {
                    ac.detach();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else {
            screen.addFragment(new NotificationWindow(header, message, Position.create(0,0)));
        }
    }

    public void addItemSelectionWindow(String promptName, List<Item> choices, boolean blocking) {

    }

    protected class ScreenRendererThread implements Runnable {

        private volatile boolean running = true;

        public void terminate() {
            running = false;
        }

        public void run() {
            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void setFrameProvider(IFrameProvider frameProvider) {
        this.frameProvider = frameProvider;
    }


    public void closeGui() {
    }
    protected void drawFrame(List<List<TextCharacter>> frame) {

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
        return "";
    }

    public void listMessage(String promptName, List<String> items, boolean blocking) {

    }

    public void addItemSelectionWindow(String promptName, String message, List<Item> items, boolean blocking) {

    }
    @Override
    public void stop() {
        //TODO: Remove deprecated method.
        thread.stop();
    }

}
