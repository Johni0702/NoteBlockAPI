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
package de.johni0702.sponge.noteblockapi.impl;

import com.flowpowered.math.vector.Vector3d;
import de.johni0702.sponge.noteblockapi.playback.LocationBehavior;
import de.johni0702.sponge.noteblockapi.song.Instrument;
import de.johni0702.sponge.noteblockapi.song.NoteBlock;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.type.NotePitch;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.extent.Extent;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TestNBEffectPlayBackMethod {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructor() {
        GameRegistry gameRegistry = mock(GameRegistry.class);
        LocationBehavior locationBehavior = mock(LocationBehavior.class);
        NBEffectPlayBackMethod<LocationBehavior> playBackMethod = new NBEffectPlayBackMethod<>(gameRegistry, locationBehavior);
        assertSame(locationBehavior, playBackMethod.getLocationBehavior());
    }

    @Test
    public void testConstructorGameRegistryNull() {
        LocationBehavior locationBehavior = mock(LocationBehavior.class);
        exception.expect(NullPointerException.class);
        new NBEffectPlayBackMethod<>(null, locationBehavior);
    }

    @Test
    public void testConstructorLocationBehaviorNull() {
        GameRegistry gameRegistry = mock(GameRegistry.class);
        exception.expect(NullPointerException.class);
        new NBEffectPlayBackMethod<>(gameRegistry, null);
    }

    @Test
    public void testSetGet() {
        GameRegistry gameRegistry = mock(GameRegistry.class);
        LocationBehavior locationBehavior = mock(LocationBehavior.class);
        NBEffectPlayBackMethod<LocationBehavior> playBackMethod = new NBEffectPlayBackMethod<>(gameRegistry, mock(LocationBehavior.class));
        playBackMethod.setLocationBehavior(locationBehavior);
        assertSame(locationBehavior, playBackMethod.getLocationBehavior());
    }

    @Test
    public void testSetNull() {
        GameRegistry gameRegistry = mock(GameRegistry.class);
        NBEffectPlayBackMethod<LocationBehavior> playBackMethod =  new NBEffectPlayBackMethod<>(gameRegistry, mock(LocationBehavior.class));
        exception.expect(NullPointerException.class);
        playBackMethod.setLocationBehavior(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPlay() {
        GameRegistry gameRegistry = mock(GameRegistry.class);
        Vector3d vector = Vector3d.ONE;
        Location location = new Location(mock(Extent.class), vector);
        SongPlayer songPlayer = mock(SongPlayer.class);
        Player player = mock(Player.class);
        NotePitch pitch = mock(NotePitch.class);
        NoteBlock noteBlock = new NoteBlock(Instrument.STICKS, pitch);
        LocationBehavior locationBehavior = mock(LocationBehavior.class);

        when(gameRegistry.getAllOf(NotePitch.class)).thenReturn((List) Arrays.asList(pitch));
        when(locationBehavior.getLocation(songPlayer, player, noteBlock)).thenReturn(location);

        NBEffectPlayBackMethod<LocationBehavior> playBackMethod = new NBEffectPlayBackMethod<>(gameRegistry, locationBehavior);

        playBackMethod.play(songPlayer, player, noteBlock, 0.7);

        verify(player).playSound(SoundTypes.NOTE_STICKS, vector, 1.4, 0.5);
    }
}
