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

import me.suesslab.rogueblight.lib.Registry;
import me.suesslab.rogueblight.lib.ISubsystem;
import me.suesslab.rogueblight.uix.Display;

/**
 *
 * @author justin
 */
public class SubsystemManager {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static SubsystemManager mInstance;

    public static SubsystemManager getInstance() {
        if (mInstance == null) {
            mInstance = new SubsystemManager(Arrays.asList(new Display(), new Registry()));
        }
        return mInstance;
    }

    List<ISubsystem> subs;

    public SubsystemManager(List<ISubsystem> subs) {
        this.subs = subs;
        LOGGER.info("Initializing subsystem manager");
        subs.forEach(system -> system.init(this));
    }
    
    private void shutdown() {
        LOGGER.info("Shutting down subsystems");
        subs.forEach(ISubsystem::stop);
    }
    
    public Logger getLogger() {
        return LOGGER;
    }
    
    public boolean requestShutdown() {
        LOGGER.info("Shutdown requested");
        return false;
    }

    //Returns the path to the directory the program is operating in.
    public String getDataPath() {
        return System.getProperty("user.dir") + "/game";
    }

    public String getTypeConfigFileName() {
        return "types.json";
    }
}
