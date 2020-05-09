/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.lib.Subsystem;

/**
 *
 * @author justin
 */
public class Display implements Subsystem {

    public static Display mInstance = null;
    
    private SubsystemManager manager;

    private Terminal terminal;
    private Screen screen;
    
    public Display() {
    }

    private Terminal createTerminal() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal term = defaultTerminalFactory.createTerminal();
        return term;
    }

    public static Display getInstance() {
        if (null == mInstance) {
            mInstance = new Display();
        }
        return mInstance;
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
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
