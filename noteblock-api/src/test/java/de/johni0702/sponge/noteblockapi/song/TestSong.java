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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class TestSong {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorFull() {
        Song song = new Song(42, "Awesome", "SomeGuy", "SomeOtherGuy", "This is awesome!", 2);
        assertEquals(42, song.getLength());
        assertEquals("Awesome", song.getName());
        assertEquals("SomeGuy", song.getAuthor());
        assertEquals("SomeOtherGuy", song.getOriginalAuthor());
        assertEquals("This is awesome!", song.getDescription());
        assertEquals(2, song.getTempo(), 0);
    }

    @Test
    public void testConstructorEmpty() {
        Song song = new Song(0, null, null, null, null, 0);
        assertEquals(0, song.getLength());
        assertEquals(null, song.getName());
        assertEquals(null, song.getAuthor());
        assertEquals(null, song.getOriginalAuthor());
        assertEquals(null, song.getDescription());
        assertEquals(0, song.getTempo(), 0);
    }

    @Test
    public void testLayers() {
        Song song = new Song(0, null, null, null, null, 0);
        Layer layer = new Layer();
        assertEquals(0, song.getLayers().size());
        song.getLayers().add(layer);
        assertEquals(1, song.getLayers().size());
        assertSame(layer, song.getLayers().get(0));
    }

    @Test
    public void testUpdateLength() {
        Song song = new Song(0, null, null, null, null, 0);
        Layer layer = new Layer();
        layer.setNoteBlock(42, mock(NoteBlock.class));
        song.getLayers().add(layer);

        assertEquals(0, song.getLength());

        song.updateLength();

        assertEquals(42, song.getLength());
    }

}
