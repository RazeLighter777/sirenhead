package me.suesslab.rogueblight;


public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SubsystemManager manager = SubsystemManager.getInstance();
        manager.requestShutdown();
        return;
    }
}
