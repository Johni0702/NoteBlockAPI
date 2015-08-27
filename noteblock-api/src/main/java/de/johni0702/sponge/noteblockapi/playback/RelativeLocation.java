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

import com.flowpowered.math.vector.Vector3d;
import de.johni0702.sponge.noteblockapi.song.NoteBlock;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Location behavior relative to the player's position (not view).
 */
public final class RelativeLocation implements LocationBehavior {
    private Vector3d offset;

    public RelativeLocation() {
        this(Vector3d.ZERO);
    }

    public RelativeLocation(Vector3d offset) {
        this.offset = checkNotNull(offset, "offset");
    }

    @Override
    public Location<World> getLocation(SongPlayer songPlayer, Player player, NoteBlock noteBlock) {
        return player.getLocation().add(offset);
    }

    /**
     * Return the offset at which the notes are played.
     * @return The offset
     */
    public Vector3d getOffset() {
        return offset;
    }

    /**
     * Set the offset at which the notes are played.
     * @param offset The offset
     */
    public void setOffset(Vector3d offset) {
        this.offset = checkNotNull(offset, "offset");
    }
}
