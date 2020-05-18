/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.lib.ISubsystem;
import org.w3c.dom.Text;

/**
 *
 * @author justin
 */
public class Display implements ISubsystem {

    
    private SubsystemManager manager;

    public Terminal getTerminal() {
        return terminal;
    }

    private Terminal terminal;
    private Screen screen;
    private IFrameProvider frameProvider;
    private final static Object displayLock = new Object();
    private Thread thread;
    
    public Display() {
        setFrameProvider(new NullFrameProvider());
        thread = new Thread(new ScreenRendererThread());
    }

    private Terminal createTerminal() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal term = defaultTerminalFactory.createTerminal();

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
        } catch (IOException e) {
            manager.getLogger().severe("Unable to create terminal");
        }
        thread.start();
    }

    protected class ScreenRendererThread implements  Runnable {

        private volatile boolean running = true;

        public void terminate() {
            running  = false;
        }
        public void run() {
            while (running) {
                Optional<List<List<TextCharacter>>> frame = frameProvider.getFrame();
                try {
                    TimeUnit.MILLISECONDS.sleep((long)(1.0/(double)getRefreshRate() * 1000));
                } catch (InterruptedException e) {
                    manager.getLogger().warning("Interrupted exception on frame");
                }
                frameProvider.getFrame().ifPresent(Display.this::drawFrame);

                try {
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

    @Override
    public void stop() {
        //TODO: Remove deprecated method.
        thread.stop();
    }

}
