/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.suesslab.rogueblight.uix;


import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.googlecode.lanterna.TextCharacter;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.lib.ISubsystem;
import me.suesslab.rogueblight.uix.gui.*;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.component.AttachedComponent;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.screen.Screen;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author justin
 */
public class Display implements ISubsystem {


    private SubsystemManager manager;
    //private IKeyStrokeSupplier strokeSupplier;

    private IFrameProvider frameProvider;
    private final static Object displayLock = new Object();
    private Thread thread;
    //private IKeyStrokeHandler strokeHandler;
    private CopyOnWriteArrayList<OpenMenu> openMenus;
    TileGrid tileGrid;
    Screen screen;



    public boolean isMenuOpen() {
        return !openMenus.isEmpty();
    }

    private volatile boolean isMenuOpen = false;

    public Display() {
        openMenus = new CopyOnWriteArrayList<>();
    }

    //public void setStrokeHandler(IKeyStrokeHandler strokeHandler) {
    //    this.strokeHandler = strokeHandler;
    //}

    //public void setStrokeSupplier(IKeyStrokeSupplier strokeSupplier) {
    //    this.strokeSupplier = strokeSupplier;
    //}



    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
        tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder().withSize(60, 30).withDefaultTileset(CP437TilesetResources.bisasam16x16()).build());
        screen  = Screen.create(tileGrid);
        screen.display();
        screen.setTheme(ColorThemes.amigaOs());
        //addMessage("hello", "this is a", false);
        ArrayList<String> testArray = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            testArray.add("option" + i);
        }
        addStringSelectionWindow("test", testArray, true, this::selection);
        screen.display();
        thread = new ScreenRendererThread();
        thread.start();
    }

    public void selection(Integer selection) {
        System.out.println(selection);
    }


    public void addMultipleStringSelectionWindow(String promptName, List<String> choices, boolean blocking) {
        //screen.addComponent(Components);
    }

    public void addStringSelectionWindow(String promptName, List<String> choices, boolean blocking, StringSelectionWindow.Callback t) {
        ThreadedFragment tf = new StringSelectionWindow(promptName, choices, screen, t);
        AttachedComponent ac = screen.addFragment(tf);
        OpenMenu om = new OpenMenu(ac, tf);
        if (blocking) {
            om.waitForClose();
        } else {
            openMenus.add(om);
        }
    }

    public void addMessage(String header, String message, boolean blocking) {
        ThreadedFragment tf = new NotificationWindow(header, message, screen);
        AttachedComponent ac = screen.addFragment(tf);
        if (blocking) {
            OpenMenu openMenu = new OpenMenu(ac, tf);
            openMenu.waitForClose();

        } else {
            openMenus.add(new OpenMenu(ac, tf));
        }
    }

    public void addItemSelectionWindow(String promptName, List<Item> choices, boolean blocking) {

    }

    protected class ScreenRendererThread extends Thread {

        private volatile boolean running = true;

        public void terminate() {
            running = false;
        }

        public void run() {
            while (running) {
                Iterator<OpenMenu> iterator = openMenus.iterator();
                while (iterator.hasNext()) {
                    try {
                        OpenMenu m = iterator.next();
                        if (m.closeIfReady()) {
                            openMenus.remove(m);
                            manager.getLogger().warning("Menu removed");
                        }
                    } catch (ConcurrentModificationException e) {
                        manager.getLogger().warning("Concurrent Modification Exception with the menu");
                    }

                }
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
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON Save Files", "json");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("."));
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getAbsolutePath());
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    public void listMessage(String promptName, List<String> items, boolean blocking) {
        ThreadedFragment tc = new ListMessageWindow(promptName, items, screen);
        AttachedComponent ac = screen.addFragment(tc);
        if (blocking) {
            OpenMenu om = new OpenMenu(ac, tc);
            om.waitForClose();
        } else {
            openMenus.add(new OpenMenu(ac, tc));
        }
    }

    public void addItemSelectionWindow(String promptName, String message, List<Item> items, boolean blocking) {

    }
    @Override
    public void stop() {
        //TODO: Remove deprecated method.
        thread.stop();
    }

}
