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
package de.johni0702.sponge.noteblockapi.event;

import com.google.common.base.Preconditions;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import org.spongepowered.api.entity.player.Player;

import java.util.List;

/**
 * Dispatched when a player stops listening to a song.
 */
public class PlayerStopListenSongEvent extends PlayerListenSongEvent {

    /**
     * Whether listening was cancelled or the song ended normally.
     */
    private final boolean wasCancelled;

    /**
     * Create a new player stop listen song event.
     * @param songPlayer The song player
     * @param players List of players
     * @param wasCancelled Whether listening was cancelled or ended normally
     */
    public PlayerStopListenSongEvent(SongPlayer songPlayer, List<Player> players, boolean wasCancelled) {
        super(songPlayer, players);
        this.wasCancelled = wasCancelled;
    }

    /**
     * Return whether listening was cancelled or ended normally.
     * @return {@code true} if listening was cancelled, {@code false} if the song ended normally
     */
    public boolean wasCancelled() {
        return wasCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        Preconditions.checkState(wasCancelled || !cancel, "Cannot cancel the event if the song has ended normally.");
        super.setCancelled(cancel);
    }
}
