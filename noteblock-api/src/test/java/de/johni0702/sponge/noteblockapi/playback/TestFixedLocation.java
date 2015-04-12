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
package de.johni0702.sponge.noteblockapi.playback;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.extent.Extent;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class TestFixedLocation {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Location mockLocation() {
        return new Location(mock(Extent.class), 0, 0, 0);
    }

    @Test
    public void testConstructorGet() {
        Location location = mockLocation();
        FixedLocation tested = new FixedLocation(location);
        assertSame(location, tested.getLocation());
    }

    @Test
    public void testConstructorNull() {
        exception.expect(NullPointerException.class);
        new FixedLocation(null);
    }

    @Test
    public void testSetGet() {
        Location location = mockLocation();
        FixedLocation tested = new FixedLocation(mockLocation());
        tested.setLocation(location);
        assertSame(location, tested.getLocation());
    }

    @Test
    public void testSetNull() {
        Location location = mockLocation();
        FixedLocation tested = new FixedLocation(location);

        exception.expect(NullPointerException.class);
        tested.setLocation(null);
    }

    @Test
    public void testGetLocation() {
        Location location = mockLocation();
        FixedLocation tested = new FixedLocation(location);
        assertSame(location, tested.getLocation(null, null, null));
    }

}
