package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;

import java.util.List;
import java.util.Optional;

public class NullFrameProvider implements IFrameProvider {

    @Override
    public Optional<List<List<TextCharacter>>> getFrame() {
        return Optional.empty();
    }
}
