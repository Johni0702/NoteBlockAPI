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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TestLayer {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructorEmpty() {
        Layer layer = new Layer();
        assertNull(layer.getName());
        assertEquals(0, layer.getVolume(), 0);
    }

    @Test
    public void testConstructorName() {
        Layer layer = new Layer("Testing");
        assertEquals("Testing", layer.getName());
        assertEquals(1, layer.getVolume(), 0);
    }

    @Test
    public void testConstructorNameVolume() {
        Layer layer = new Layer("Testing", 0.5);
        assertEquals("Testing", layer.getName());
        assertEquals(0.5, layer.getVolume(), 0.005);
    }

    @Test
    public void testConstructorVolumeGreater() {
        assertEquals(1, new Layer("", 1).getVolume(), 0);
        exception.expect(IllegalArgumentException.class);
        new Layer("Testing", 2);
    }

    @Test
    public void testConstructorVolumeSmaller() {
        assertEquals(0, new Layer("", 0).getVolume(), 0);
        exception.expect(IllegalArgumentException.class);
        new Layer("Testing", -1);
    }

    @Test
    public void testSetGetName() {
        Layer layer = new Layer();

        assertNull(layer.getName());
        layer.setName("Testing");
        assertEquals("Testing", layer.getName());
    }

    @Test
    public void testSetGetVolume() {
        Layer layer = new Layer();

        assertEquals(0, layer.getVolume(), 0);
        layer.setVolume(1);
        assertEquals(1, layer.getVolume(), 1);
    }

    @Test
    public void testSetVolumeGreater() {
        Layer layer = new Layer();

        exception.expect(IllegalArgumentException.class);
        layer.setVolume(2);
    }

    @Test
    public void testSetVolumeSmaller() {
        Layer layer = new Layer();

        exception.expect(IllegalArgumentException.class);
        layer.setVolume(-1);
    }

    @Test
    public void testGetNotes() {
        NoteBlock noteBlock = mock(NoteBlock.class);
        Layer layer = new Layer();

        assertEquals(0, layer.getNotes().size());
        layer.getNotes().put(42, noteBlock);
        assertEquals(1, layer.getNotes().size());
        assertSame(noteBlock, layer.getNotes().get(42));
    }

    @Test
    public void testGetNote() {
        NoteBlock noteBlock = mock(NoteBlock.class);
        Layer layer = new Layer();
        layer.getNotes().put(42, noteBlock);

        assertSame(noteBlock, layer.getNoteBlock(42));
    }

    @Test
    public void testSetNote() {
        NoteBlock noteBlock = mock(NoteBlock.class);
        Layer layer = new Layer();

        layer.setNoteBlock(42, noteBlock);
        assertSame(noteBlock, layer.getNotes().get(42));
    }

}
