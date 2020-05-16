package me.suesslab.rogueblight.lib;

import java.util.Locale;
import java.util.ResourceBundle;

public class EnumResourceWrapper<T extends Enum<T>> {
    private ResourceBundle bundle;
    private Class<T> clazz;
    public EnumResourceWrapper(Class<T> clazz) {
        this.clazz = clazz;
        bundle = ResourceBundle.getBundle(clazz.getSimpleName());
    }
    public String getString(String name) {
        try {
            T.valueOf(clazz, name);
        } catch (IllegalArgumentException e) {
            return "[STRING NOT IN ENUM LOL]";
        }
        return bundle.getString(name);
    }
    public String getString(T value) {
        return getString(value.name());
    }
}
