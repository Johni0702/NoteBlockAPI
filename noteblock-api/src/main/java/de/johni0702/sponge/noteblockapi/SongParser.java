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

import de.johni0702.sponge.noteblockapi.song.Song;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Parses a song from an input stream.
 */
public interface SongParser {

    /**
     * Read a song from the specified file (in .nbs format).
     * @param file The input file
     * @return The song
     * @throws IOException if an I/O error occurs
     */
    Song parseNBS(File file) throws IOException;

    /**
     * Read a song from the specified input stream (in .nbs format).
     * @param inputStream The input stream
     * @return The song
     * @throws IOException if an I/O error occurs
     */
    Song parseNBS(InputStream inputStream) throws IOException;

    /**
     * Read a song from the specified file (in .mid format).
     * @param file The input file
     * @param name Name of the song
     * @param author Author of the song
     * @return The song
     * @throws IOException if an I/O error occurs
     */
    Song parseMidi(File file, String name, String author) throws IOException;

    /**
     * Read a song from the specified input stream (in .mid format).
     * @param inputStream The input stream
     * @param name Name of the song
     * @param author Author of the song
     * @return The song
     * @throws IOException if an I/O error occurs
     */
    Song parseMidi(InputStream inputStream, String name, String author) throws IOException;

}
