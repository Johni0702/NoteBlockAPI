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
package de.johni0702.sponge.noteblockapi.fade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLinearFade {

    @Test
    public void testFadeIn() {
        LinearFade fade = new LinearFade();
        assertEquals(0, fade.getVolume(10, 0, true), 0.001d);
        assertEquals(0.1, fade.getVolume(10, 1, true), 0.001d);
        assertEquals(0.3, fade.getVolume(10, 3, true), 0.001d);
        assertEquals(0.5, fade.getVolume(10, 5, true), 0.001d);
        assertEquals(0.6, fade.getVolume(10, 6, true), 0.001d);
        assertEquals(0.9, fade.getVolume(10, 9, true), 0.001d);
        assertEquals(1, fade.getVolume(10, 10, true), 0.001d);
    }

    @Test
    public void testFadeOut() {
        LinearFade fade = new LinearFade();
        assertEquals(1, fade.getVolume(10, 0, false), 0.001d);
        assertEquals(0.9, fade.getVolume(10, 1, false), 0.001d);
        assertEquals(0.6, fade.getVolume(10, 4, false), 0.001d);
        assertEquals(0.5, fade.getVolume(10, 5, false), 0.001d);
        assertEquals(0.3, fade.getVolume(10, 7, false), 0.001d);
        assertEquals(0.2, fade.getVolume(10, 8, false), 0.001d);
        assertEquals(0, fade.getVolume(10, 10, false), 0.001d);
    }

}
