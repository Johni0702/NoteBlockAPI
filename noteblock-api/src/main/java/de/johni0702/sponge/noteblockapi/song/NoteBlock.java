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

import org.spongepowered.api.data.types.NotePitch;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents one NoteBlock in some Song. It is in no way associated with any block.
 */
public class NoteBlock {

    /**
     * The instrument of this note.
     */
    private final Instrument instrument;

    /**
     * The pitch of this note.
     */
    private final NotePitch pitch;

    /**
     * Create a new note block.
     * @param instrument The instrument
     * @param pitch The pitch
     */
    public NoteBlock(Instrument instrument, NotePitch pitch) {
        this.instrument = checkNotNull(instrument, "instrument");
        this.pitch = checkNotNull(pitch, "pitch");
    }

    /**
     * Returns the instrument of this note block.
     * @return The instrument
     */
    public Instrument getInstrument() {
        return instrument;
    }

    /**
     * Returns the pitch of this note block.
     * @return The pitch
     */
    public NotePitch getPitch() {
        return pitch;
    }
}
