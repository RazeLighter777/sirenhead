package me.suesslab.rogueblight.literary;

import java.util.Locale;
import java.util.ResourceBundle;

public final class EnumResourceWrapper<T extends Enum<T>> {
    private Class<T> clazz;
    public EnumResourceWrapper(Class<T> clazz) {
        this.clazz = clazz;
    }
    public String getString(String name) {
        try {
            T.valueOf(clazz, name);
        } catch (IllegalArgumentException e) {
            return "[STRING NOT IN ENUM LOL]";
        }
        return ResourceBundle.getBundle(clazz.getSimpleName()).getString(name);
    }
    public String getString(T value) {
        return ResourceBundle.getBundle(clazz.getSimpleName()).getString(value.name());
    }
}
