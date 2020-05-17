package me.suesslab.rogueblight.literary;

public class StringUtils {
    public static String substitute(String str, String ...a) {
        String result = str;
        int i = 0;
        for (String s : a) {
            result = result.replace("{" + i  + "}", s);
            i++;
        }
        return result;
    }
}
