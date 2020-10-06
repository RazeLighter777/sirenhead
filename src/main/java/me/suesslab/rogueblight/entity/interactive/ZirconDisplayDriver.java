package me.suesslab.rogueblight.entity.interactive;

import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.uix.OpenMenu;
import me.suesslab.rogueblight.uix.gui.*;
import org.hexworks.zircon.api.component.AttachedComponent;

import java.util.List;

public class ZirconDisplayDriver implements  IDriver {

    ZirconDisplay zirconDisplay;
    public ZirconDisplayDriver(ZirconDisplay zirconDisplay) {

        this.zirconDisplay = zirconDisplay;
    }

    @Override
    public void addMultipleStringSelectionWindow(String promptName, List<String> choices, boolean blocking, MultiStringSelectionWindow.Callback t) {
        ThreadedFragment tf = new MultiStringSelectionWindow(promptName, choices, zirconDisplay.screen, t);
        AttachedComponent ac = zirconDisplay.screen.addFragment(tf);
        OpenMenu om = new OpenMenu(ac, tf);
        if (blocking) {
            om.waitForClose();
        } else {
            zirconDisplay.openMenus.add(om);
        }
    }

    @Override
    public void addStringSelectionWindow(String promptName, List<String> choices, boolean blocking, StringSelectionWindow.Callback t) {
        ThreadedFragment tf = new StringSelectionWindow(promptName, choices, zirconDisplay.screen, t);
        AttachedComponent ac = zirconDisplay.screen.addFragment(tf);
        OpenMenu om = new OpenMenu(ac, tf);
        if (blocking) {
            om.waitForClose();
        } else {
            zirconDisplay.openMenus.add(om);
        }
    }

    @Override
    public void addMessage(String header, String message, boolean blocking) {
        ThreadedFragment tf = new NotificationWindow(header, message, zirconDisplay.screen);
        AttachedComponent ac = zirconDisplay.screen.addFragment(tf);
        OpenMenu om = new OpenMenu(ac, tf);
        if (blocking) {
            om.waitForClose();
        } else {
            zirconDisplay.openMenus.add(om);
        }
    }

    @Override
    public void addItemSelectionWindow(String promptName, List<Item> choices, boolean blocking) {

    }

    @Override
    public void listMessage(String promptName, List<String> items, boolean blocking) {
        ThreadedFragment tc = new ListMessageWindow(promptName, items, zirconDisplay.screen);
        AttachedComponent ac = zirconDisplay.screen.addFragment(tc);
        if (blocking) {
            OpenMenu om = new OpenMenu(ac, tc);
            om.waitForClose();
        } else {
            zirconDisplay.openMenus.add(new OpenMenu(ac, tc));
        }
    }

    @Override
    public void addItemSelectionWindow(String promptName, String message, List<Item> items, boolean blocking) {

    }

    //XXX Remote sessions shouldn't open file selection dialogs. Should only be a ZirconDisplayDriver thing.
    @Override
    public String fileSelectionDialog(String title, String desc) {
        return null;
    }

    @Override
    public boolean isMenuOpen() {
        return zirconDisplay.isMenuOpen();
    }
}
