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


import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import org.spongepowered.api.event.AbstractEvent;
import org.spongepowered.api.event.Cancellable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base class for events having to do with a song player.
 */
public abstract class SongPlayerEvent extends AbstractEvent implements Cancellable {

    /**
     * The song player.
     */
    private final SongPlayer songPlayer;

    /**
     * Whether this event is cancelled.
     */
    private boolean cancelled;

    /**
     * Create a new song player event.
     * @param songPlayer The song player
     */
    public SongPlayerEvent(SongPlayer songPlayer) {
        this.songPlayer = checkNotNull(songPlayer, "songPlayer");
    }

    /**
     * Return the song player associated with this event.
     * @return The song player
     */
    public SongPlayer getSongPlayer() {
        return songPlayer;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
