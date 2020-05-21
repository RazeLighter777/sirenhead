package me.suesslab.rogueblight.lib.audio;

import me.suesslab.rogueblight.SubsystemManager;
import me.suesslab.rogueblight.lib.ISubsystem;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class AudioManager implements ISubsystem {

    private SubsystemManager manager;
    Sequencer sequencer;

    @Override
    public void init(SubsystemManager manager) {
        this.manager = manager;
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
        } catch (MidiUnavailableException e) {
            manager.getLogger().warning("MIDI will not play back on this machine.");
        }
    }

    @Override
    public void stop() {
        sequencer.stop();
    }
    public void muteBackgroundSounds() {
        sequencer.stop();
    }
    public void setBackgroundSound(String fileName) {
        String path = manager.getDataPath()+ "/sound/" + fileName;
        File soundFile = new File(path);
        if (fileName.endsWith(".MID")) {
            try {
                Sequence sequence = MidiSystem.getSequence(soundFile);
                sequencer.setSequence(sequence);
                sequencer.start();
                sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
