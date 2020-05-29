package me.suesslab.rogueblight.uix;

import me.suesslab.rogueblight.uix.gui.ThreadedFragment;
import org.hexworks.zircon.api.component.AttachedComponent;

public class OpenMenu {
    ThreadedFragment tf;
    AttachedComponent ac;
    public OpenMenu(AttachedComponent ac, ThreadedFragment tf) {
        this.ac = ac;
        this.tf = tf;
    }

    public void waitForClose() {
        while (true) {
            if (closeIfReady()) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean closeIfReady() {
        if (tf.isFinished()) {
            ac.detach();
            return true;
        }
        return false;
    }
}
