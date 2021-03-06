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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.spongepowered.api.data.type.NotePitch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TestNoteBlock {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructor() {
        NotePitch notePitch = mock(NotePitch.class);
        NoteBlock layer = new NoteBlock(Instrument.STICKS, notePitch);
        assertEquals(Instrument.STICKS, layer.getInstrument());
        assertEquals(notePitch, layer.getPitch());
    }

    @Test
    public void testConstructorNoInstrument() {
        exception.expect(NullPointerException.class);
        new NoteBlock(null, mock(NotePitch.class));
    }

    @Test
    public void testConstructorNoPitch() {
        exception.expect(NullPointerException.class);
        new NoteBlock(Instrument.STICKS, null);
    }

}
