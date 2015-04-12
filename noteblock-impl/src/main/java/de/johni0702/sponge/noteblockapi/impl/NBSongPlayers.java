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
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import de.johni0702.sponge.noteblockapi.SongPlayers;
import de.johni0702.sponge.noteblockapi.playback.*;
import de.johni0702.sponge.noteblockapi.song.Song;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import de.johni0702.sponge.noteblockapi.songplayer.SongProvider;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.world.Location;

import java.util.Iterator;

/**
 * Implementation of SongPlayers
 */
public class NBSongPlayers implements SongPlayers {

    private final Provider<NBSongPlayer> songPlayerProvider;

    @Inject
    public NBSongPlayers(Provider<NBSongPlayer> songPlayerProvider) {
        this.songPlayerProvider = songPlayerProvider;
    }

    @Override
    public SongPlayer createSongPlayer() {
        return songPlayerProvider.get();
    }

    @Override
    public SongPlayer createSongPlayer(SongProvider songProvider) {
        SongPlayer songPlayer = createSongPlayer();
        songPlayer.setSongProvider(songProvider);
        return songPlayer;
    }

    @Override
    public SongPlayer createSongPlayer(Song song) {
        SongPlayer songPlayer = createSongPlayer();
        songPlayer.setSong(song);
        return songPlayer;
    }

    @Override
    public SongPlayer createLoopingSongPlayer(final Song...song) {
        return createSongPlayer(new SongProvider() {
            private Iterator<Song> iter = Iterables.cycle(song).iterator();
            @Override
            public Song getNextSong(SongPlayer songPlayer) {
                return iter.next();
            }
        });
    }

    @Override
    public BlockPlayBackMethod createBlockPlayBack(Location location) {
        return new NBBlockPlayBackMethod(location);
    }

    @Override
    public EffectPlayBackMethod<FixedLocation> createSoundEffectPlayBack(GameRegistry gameRegistry, Location location) {
        return new NBEffectPlayBackMethod<>(gameRegistry, new FixedLocation(location));
    }

    @Override
    public EffectPlayBackMethod<RelativeLocation> createSoundEffectPlayBack(GameRegistry gameRegistry, Vector3d offset) {
        return new NBEffectPlayBackMethod<>(gameRegistry, new RelativeLocation(offset));
    }

    @Override
    public <T extends LocationBehavior> EffectPlayBackMethod<T> createSoundEffectPlayBack(GameRegistry gameRegistry, T location) {
        return new NBEffectPlayBackMethod<>(gameRegistry, location);
    }
}
