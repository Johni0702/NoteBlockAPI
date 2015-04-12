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
package de.johni0702.sponge.noteblockapi.song;

import java.util.SortedMap;
import java.util.TreeMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A layer of NoteBlocks in a Song.
 */
public class Layer {

    /**
     * The name of this layer.
     */
    private String name;

    /**
     * The volume of this layer with 0 being silent and 1 being full.
     */
    private double volume;

    /**
     * Notes for each tick (if there is one)
     */
    private final SortedMap<Integer, NoteBlock> noteBlocks = new TreeMap<>();

    /**
     * Create a new layer.
     */
    public Layer() {
    }

    /**
     * Create a new layer.
     * @param name Name of the layer
     */
    public Layer(String name) {
        this(name, 1);
    }

    /**
     * Create a new layer.
     * @param name Name of the layer
     * @param volume Volume with 0 being silent and 1 being full
     */
    public Layer(String name, double volume) {
        this.name = name;
        this.setVolume(volume);
    }

    /**
     * Returns the name of this layer.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this layer.
     * @param name The name
     */
    public void setName(String name) {
        this.name = checkNotNull(name, "name");
    }

    /**
     * Returns the volume of this layer.
     * @return The volume with 0 being silent and 1 being full
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Set the volume of this layer.
     * @param volume The volume with 0 being silent and 1 being full
     */
    public void setVolume(double volume) {
        checkArgument(volume >= 0 && volume <= 1, "volume must be between 0 and 1 (inclusive)");
        this.volume = volume;
    }

    /**
     * Get the note block at the specified time.
     * @param tick Time in ticks
     * @return The note block or {@code null} if there is none
     */
    public NoteBlock getNoteBlock(int tick) {
        return noteBlocks.get(tick);
    }

    /**
     * Set the note block at the specified position.
     * @param tick Time in ticks
     * @param noteBlock The note block, may be {@code null} to remove the note block
     */
    public void setNoteBlock(int tick, NoteBlock noteBlock) {
        noteBlocks.put(tick, noteBlock);
    }

    /**
     * Returns a sorted map containing all note blocks and their time in ticks.
     * Changes to the returned map reflect onto this layer and vice versa.
     * @return Map of all note blocks
     */
    public SortedMap<Integer, NoteBlock> getNotes() {
        return noteBlocks;
    }
}
