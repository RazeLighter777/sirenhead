package me.suesslab.rogueblight.uix;

import com.googlecode.lanterna.TextCharacter;

import java.util.List;
import java.util.Optional;

public interface IFrameProvider {

    public Optional<List<List<TextCharacter>>> getFrame();

}
