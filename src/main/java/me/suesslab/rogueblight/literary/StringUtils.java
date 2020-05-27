package me.suesslab.rogueblight.literary;

public class StringUtils {

    /**
     * Substitutes the vararg string into the string ex "blah blah does {1} to {2}" ("this", "that") -> "blah blah does this to that"
     * @param str
     * @param a
     * @return
     */
    public static String substitute(String str, String ...a) {
        String result = str;
        int i = 0;
        for (String s : a) {
            result = result.replace("{" + i  + "}", s);
            i++;
        }
        return result;
    }

    /**
     * Capitalizes the first letter in a sentence and adds a period if no punctuation is detected at the end.
     * @param input
     * @return
     */
    public static String makeSentence(String input) {
        String cap = input.substring(0, 1).toUpperCase() + input.substring(1);
        if (!cap.endsWith("!") || !cap.endsWith(".") || !cap.endsWith("?")) {
            cap = cap + ".";
        }
        return cap;
    }

}
