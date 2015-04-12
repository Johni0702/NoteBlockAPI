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
package de.johni0702.sponge.noteblockapi;

import com.flowpowered.math.vector.Vector3d;
import de.johni0702.sponge.noteblockapi.playback.*;
import de.johni0702.sponge.noteblockapi.song.Song;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import de.johni0702.sponge.noteblockapi.songplayer.SongProvider;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.world.Location;

/**
 * Create different type of song players.
 */
public interface SongPlayers {

    /**
     * Create a new song player without a song.
     * @return The new song player
     */
    SongPlayer createSongPlayer();

    /**
     * Create a new song player with the specified song provider.
     * @param songProvider The song provider
     * @return The new song player
     */
    SongPlayer createSongPlayer(SongProvider songProvider);

    /**
     * Create a new song player with the specified song.
     * @param song The song
     * @return The new song player
     */
    SongPlayer createSongPlayer(Song song);

    /**
     * Create a new song player looping the specified song.
     * @param song Array of songs
     * @return The new song player
     */
    SongPlayer createLoopingSongPlayer(Song... song);

    /**
     * Creates a new playback method which plays notes at a specific note block.
     * @param location The location of the note block
     * @return The play back method
     */
    BlockPlayBackMethod createBlockPlayBack(Location location);

    /**
     * Creates a new playback method which plays notes as sound effects (no block, no particles).
     * The created playback spawns particles at a fixed position.
     * @param gameRegistry The game registry
     * @param location The location of the sound effect
     * @return The play back method
     */
    EffectPlayBackMethod<FixedLocation> createSoundEffectPlayBack(GameRegistry gameRegistry, Location location);

    /**
     * Creates a new playback method which plays notes as sound effects (no block, no particles).
     * The created playback spawns particles relative to the player's position.
     * @param gameRegistry The game registry
     * @param offset The offset of the sound effect
     * @return The play back method
     */
    EffectPlayBackMethod<RelativeLocation> createSoundEffectPlayBack(GameRegistry gameRegistry, Vector3d offset);

    /**
     * Creates a new playback method which plays notes as sound effects (no block, no particles).
     * The created playback spawns particles at the position returned by the specified LocationBehavior
     * @param gameRegistry The game registry
     * @param location The location behavior
     * @param <T> Type of the location behavior
     * @return The play back method
     */
    <T extends LocationBehavior> EffectPlayBackMethod<T> createSoundEffectPlayBack(GameRegistry gameRegistry, T location);

}
