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
package de.johni0702.sponge.noteblockapi.songplayer;

import de.johni0702.sponge.noteblockapi.fade.Fade;
import de.johni0702.sponge.noteblockapi.playback.PlayBackMethod;
import de.johni0702.sponge.noteblockapi.song.Song;
import org.spongepowered.api.entity.player.Player;

import java.util.List;

/**
 * A player for note block songs.
 */
public interface SongPlayer {

    /**
     * Return the current song.
     * @return The song
     */
    Song getSong();

    /**
     * Set the current song but does not yet start playing it.
     * If this player is active, this stops the player.
     * If the player cannot be stopped due to the event being cancelled, this has no effect.
     * @param song The new song
     */
    void setSong(Song song);

    /**
     * Return the song provider for this player.
     * @return The song provider
     */
    SongProvider getSongProvider();

    /**
     * Set the song provider.
     * If this player is currently active this does not change the current song.
     * If this player is not playing then the current song is updated with the next song from the song provider.
     * @param songProvider The song provider
     */
    void setSongProvider(SongProvider songProvider);

    /**
     * Return the current volume.
     * @return The current volume (0 being silent, 1 being full)
     */
    double getVolume();

    /**
     * Set the volume.
     * @param volume The new volume (0 being silent, 1 being full)
     */
    void setVolume(double volume);

    /**
     * Return the current volume for the specified player.
     * This volume does not have {@link #getVolume()} nor the volume of the layers factored in.
     * @param player The player
     * @return The current volume (0 being silent, 1 being full)
     */
    double getVolume(Player player);

    /**
     * Set the volume for the specified player.
     * @param player The player
     * @param volume The new volume (0 being silent, 1 being full)
     */
    void setVolume(Player player, double volume);

    /**
     * Return whether this player is currently active.
     * @return {@code true} if playing, {@code false} otherwise
     */
    boolean isPlaying();

    /**
     * Returns the current position in the song.
     * @return Position in ticks or {@code -1} if inactive
     */
    int currentTick();

    /**
     * Return the list of playback methods.
     * Changes to the returned list reflect onto this song player and vice versa.
     * @return List of playback methods
     */
    List<PlayBackMethod> getPlayBackMethods();

    /**
     * Returns the fade used when fading in.
     * @return The fade or {@code null} when no fading should be done
     */
    Fade getFadeIn();

    /**
     * Returns the fade used when fading out.
     * @return The fade or {@code null} when no fading should be done
     */
    Fade getFadeOut();

    /**
     * Returns the duration of the fade in operation in ticks.
     * @return The duration
     */
    int getFadeInDuration();

    /**
     * Returns the duration of the fade out operation in ticks.
     * @return The duration
     */
    int getFadeOutDuration();

    /**
     * Set the fade used when fading in.
     * @param fade The fade or {@code null} to disable fading
     */
    void setFadeIn(Fade fade);

    /**
     * Set the fade used when fading in.
     * @param fade The fade or {@code null} to disable fading
     */
    void setFadeOut(Fade fade);

    /**
     * Set the duration of the fade in operation.
     * @param duration Duration in ticks
     */
    void setFadeInDuration(int duration);

    /**
     * Set the duration of the fade out operation.
     * @param duration Duration in ticks
     */
    void setFadeOutDuration(int duration);

    /**
     * Returns all players listening to this song player.
     * @return Unmodifiable list of players
     */
    List<Player> getListeners();

    /**
     * Add listener to this song player.
     * @param player The player to be added
     */
    void addListener(Player player);

    /**
     * Remove listener from this song player.
     * @param player The player to be removed
     */
    void removeListener(Player player);

    /**
     * Start this player.
     * @return {@code true} if the player started playing, {@code false} if the event was cancelled
     */
    boolean start();

    /**
     * Stop this player. If the player is started again afterwards, the same song will be played again.
     * @return {@code true} if the player stopped playing, {@code false} if the event was cancelled
     */
    boolean stop();
}
