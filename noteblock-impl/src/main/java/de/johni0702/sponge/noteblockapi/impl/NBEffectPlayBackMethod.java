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

import de.johni0702.sponge.noteblockapi.playback.EffectPlayBackMethod;
import de.johni0702.sponge.noteblockapi.playback.LocationBehavior;
import de.johni0702.sponge.noteblockapi.song.Instrument;
import de.johni0702.sponge.noteblockapi.song.NoteBlock;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation for EffectPlayBackMethod.
 */
public class NBEffectPlayBackMethod<T extends LocationBehavior> implements EffectPlayBackMethod<T> {

    /**
     * The game registry.
     */
    private final GameRegistry gameRegistry;

    /**
     * The locationBehavior behavior.
     */
    private T locationBehavior;

    /**
     * Create a new instance.
     * @param gameRegistry The game registry
     * @param locationBehavior The location behavior
     */
    public NBEffectPlayBackMethod(GameRegistry gameRegistry, T locationBehavior) {
        this.gameRegistry = checkNotNull(gameRegistry, "gameRegistry");
        this.locationBehavior = checkNotNull(locationBehavior, "locationBehavior");
    }

    @Override
    public T getLocationBehavior() {
        return locationBehavior;
    }

    @Override
    public void setLocationBehavior(T locationBehavior) {
        this.locationBehavior = checkNotNull(locationBehavior, "locationBehavior");
    }

    @Override
    public void play(SongPlayer songPlayer, Player player, NoteBlock noteBlock, double volume) {
        Location loc = locationBehavior.getLocation(songPlayer, player, noteBlock);
        Instrument instrument = noteBlock.getInstrument();
        float pitch = instrument.getPitch(gameRegistry, noteBlock.getPitch());
        player.playSound(instrument.getSoundType(), loc.getPosition(), volume * 2, pitch);
    }
}
