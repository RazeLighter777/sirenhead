package me.suesslab.rogueblight.uix.gui;

import org.hexworks.zircon.api.component.Fragment;

public abstract class ThreadedFragment implements Fragment {

    private boolean finished  = false;

    public final boolean isFinished() {
        return finished;
    }

    public final void finish() {
        this.finished = true;
    }


}
