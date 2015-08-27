/*
 * This file is part of NoteBlockAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 Johni0702 <https://github.com/johni0702>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.johni0702.sponge.noteblockapi.song;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.type.NotePitch;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;

import java.util.Iterator;

/**
 * Represents an Instrument in note block songs.
 */
public enum Instrument {
    PIANO(SoundTypes.NOTE_PIANO),
    BASS(SoundTypes.NOTE_BASS),
    BASS_DRUM(SoundTypes.NOTE_BASS_DRUM),
    SNARE_DRUM(SoundTypes.NOTE_SNARE_DRUM),
    STICKS(SoundTypes.NOTE_STICKS);

    /**
     * The sound corresponding to this instrument.
     */
    private final SoundType soundType;

    Instrument(SoundType soundType) {
        this.soundType = soundType;
    }

    /**
     * Returns the sound corresponding to instrument.
     * @return The sound type
     */
    public SoundType getSoundType() {
        return soundType;
    }

    /**
     * Converts the specified note pitch to a sound effect pitch.
     * @param registry The game registry
     * @param pitch The note
     * @return The sound effect pitch in range [0, 2]
     */
    public float getPitch(GameRegistry registry, NotePitch pitch) {
        Iterator<? extends NotePitch> iter = registry.getAllOf(NotePitch.class).iterator();
        return PITCH[Iterators.indexOf(iter, Predicates.equalTo(pitch))];
    }

    /**
     * Returns the instrument corresponding to the id in the nbs file.
     * If the id is a custom instrument, PIANO is returned.
     * @param id The id
     * @return The instrument
     */
    public static Instrument forId(int id) {
        if (id < 0 || id >= values().length) {
            return PIANO;
        }
        return values()[id];
    }

    /**
     * Maps each NotePitch id to a pitch value as used by the /playsound command.
     * Values taken from the <a href="http://minecraft.gamepedia.com/Note_block?cookieSetup=true#Usage"> minecraft wiki</a>.
     */
    private static final float[] PITCH = {
            0.5f, 0.53f, 0.56f, 0.6f, 0.63f, 0.67f, 0.7f, 0.75f, 0.8f, 0.85f, 0.9f, 0.95f,
            1.0f, 1.05f, 1.1f, 1.2f, 1.25f, 1.32f, 1.4f, 1.5f, 1.6f, 1.7f, 1.8f, 1.9f, 2.0f
    };
}
