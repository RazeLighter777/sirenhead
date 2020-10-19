package me.suesslab.rogueblight.entity.interactive;

import me.suesslab.rogueblight.item.Item;
import me.suesslab.rogueblight.uix.gui.MultiStringSelectionWindow;
import me.suesslab.rogueblight.uix.gui.StringSelectionWindow;

import java.util.List;

public interface IDriver {

    public void addMultipleStringSelectionWindow(String promptName, List<String> choices, boolean blocking, MultiStringSelectionWindow.Callback t);

    public void addStringSelectionWindow(String promptName, List<String> choices, boolean blocking, StringSelectionWindow.Callback t);

    public void addMessage(String header, String message, boolean blocking);

    public void addItemSelectionWindow(String promptName, List<Item> choices, boolean blocking);

    public void listMessage(String promptName, List<String> items, boolean blocking);

    public void addItemSelectionWindow(String promptName, String message, List<Item> items, boolean blocking);

    //public String fileSelectionDialog(String title, String desc);

    public boolean isMenuOpen();

}
