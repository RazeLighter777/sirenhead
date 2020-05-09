/*
 * Copyright (C) 2020 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.suesslab.rogueblight;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import me.suesslab.rogueblight.lib.Subsystem;
import me.suesslab.rogueblight.uix.Display;

/**
 *
 * @author justin
 */
public class SubsystemManager {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    List<Subsystem> subs = Arrays.asList(new Subsystem[]{
        new Display()
    }
    );

    public SubsystemManager() {
        LOGGER.info("Initializing subsystem manager");
        subs.forEach(system -> {system.init(this);});
    }
    
    private void shutdown() {
        LOGGER.info("Shutting down subsystems");
        subs.forEach(system -> {system.stop();});
    }
    
    public Logger getLogger() {
        return LOGGER;
    }
    
    public boolean requestShutdown() {
        LOGGER.info("Shutdown requested");
        shutdown();
        return false;
    }
}
