package me.suesslab.rogueblight.literary;

import me.suesslab.rogueblight.interact.Interaction;

import java.util.ArrayList;

public final class StringLog {
    private int size;

    public ArrayList<String> getLog() {
        return log;
    }

    private ArrayList<String> log;
    public StringLog(int size) {
        this.size = size;
        log = new ArrayList<>();
    }

    public void log(Interaction interaction) {
        log.add(interaction.toString());
        if (log.size() > size) {
            log.remove(0);
        }
    }

    /**
     *
     * @return The top string in the log and a multiplier for every message repeated. If there are no strings on the log it returns ""
     */
    public String getTop() {
        String result = "";
        if (size() > 0) {
            result = log.get(log.size() - 1);
        } else {
            return "";
        }
        int i = 1;
        while (true) {
            if (size() - 1 - i < 0) {
                break;
            }
            if (get(size() - 1 - i).equals(result)) {
                i++;
            } else {
                break;
            }
        }
        return result + ((i > 1) ? "x" + i : "");
    }

    public int size() {
        return log.size();
    }

    public String get(int i) {
        return log.get(i);
    }


}
