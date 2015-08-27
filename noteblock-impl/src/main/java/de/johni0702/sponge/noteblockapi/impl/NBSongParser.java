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

import com.google.common.collect.Iterators;
import com.google.common.io.LittleEndianDataInputStream;
import com.google.inject.Inject;
import de.johni0702.sponge.noteblockapi.SongParser;
import de.johni0702.sponge.noteblockapi.song.Instrument;
import de.johni0702.sponge.noteblockapi.song.Layer;
import de.johni0702.sponge.noteblockapi.song.NoteBlock;
import de.johni0702.sponge.noteblockapi.song.Song;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.data.type.NotePitch;
import org.spongepowered.api.data.type.NotePitches;

import javax.sound.midi.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

/**
 * Parser for songs from input streams.
 */
public class NBSongParser implements SongParser {

    private final GameRegistry gameRegistry;

    @Inject
    public NBSongParser(GameRegistry gameRegistry) {
        this.gameRegistry = gameRegistry;
    }

    @Override
    public Song parseMidi(File file, String name, String author) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            return parseMidi(in, name, author);
        }
    }

    @Override
    public Song parseMidi(InputStream inputStream, String name, String author) throws IOException {
        try {
            Sequence sequence = MidiSystem.getSequence(inputStream);
            double midiTickToMicro = (double) sequence.getMicrosecondLength() / sequence.getTickLength();
            List<Layer> layers = new ArrayList<>();
            for (Track track : sequence.getTracks()) {
                Instrument[] instrument = new Instrument[16];
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if (message instanceof ShortMessage) {
                        ShortMessage m = (ShortMessage) message;
                        if (m.getCommand() == ShortMessage.NOTE_ON) {
                            int tick = (int) (midiTickToMicro * event.getTick() / 50_000);
                            double volume = m.getData2() / 127d;
                            NotePitch pitch = notePitchForId(gameRegistry, (m.getData1() - 6) % 24);
                            Layer layer = null;
                            for (Layer l : layers) {
                                if (l.getVolume() == volume && l.getNoteBlock(tick) == null) {
                                    layer = l;
                                }
                            }
                            if (layer == null) {
                                layers.add(layer = new Layer("Layer" + layers.size(), volume));
                            }
                            layer.setNoteBlock(tick, new NoteBlock(instrument[m.getChannel()], pitch));
                        } else if (m.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                            instrument[m.getChannel()] = programToInstrument(m.getData1(), m.getChannel());
                        }
                    }
                }
            }
            int length = 0;
            for (Layer layer : layers) {
                SortedMap<Integer, NoteBlock> notes = layer.getNotes();
                length = Math.max(length, notes.isEmpty() ? 0 : notes.lastKey());
            }
            return new Song(length, name, author, author, "Imported MIDI file", 1);
        } catch (InvalidMidiDataException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Song parseNBS(File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            return parseNBS(in);
        }
    }

    @Override
    public Song parseNBS(InputStream inputStream) throws IOException {

        DataInput in = new LittleEndianDataInputStream(inputStream);

        // Header
        int length = in.readShort();
        int height = in.readShort();
        String name = readString(in);
        String author = readString(in);
        String originalAuthor = readString(in);
        String description = readString(in);
        double tempo = in.readShort() / 100d;
        in.skipBytes(23); // Skip unnecessary info
        in.skipBytes(in.readInt());

        // Note blocks
        Layer[] layers = new Layer[height];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer();
        }

        int jump;
        int tick = -1;
        while ((jump = in.readShort()) != 0) {
            tick += jump;
            int layer = -1;
            while ((jump = in.readShort()) != 0) {
                layer += jump;

                Instrument instrument = Instrument.forId(in.readByte());
                NotePitch pitch = notePitchForId(gameRegistry,(in.readByte() - 33) % 24);

                layers[layer].setNoteBlock(tick, new NoteBlock(instrument, pitch));
            }
        }

        // Layer info
        for (Layer layer : layers) {
            layer.setName(readString(in));
            layer.setVolume(in.readByte() / 100d);
        }

        Song song = new Song(length, name, author, originalAuthor, description, tempo);
        song.getLayers().addAll(Arrays.asList(layers));
        return song;
    }

    private NotePitch notePitchForId(GameRegistry gameRegistry, int id) {
        return Iterators.get(gameRegistry.getAllOf(NotePitch.class).iterator(), id, NotePitches.F_SHARP0);
    }

    private String readString(DataInput in) throws IOException {
        byte[] buf = new byte[in.readInt()];
        in.readFully(buf);
        return new String(buf);
    }

    /**
     * Returns the instrument for the specified program and channel in a midi file.
     * Values according to <a href="http://en.wikipedia.org/wiki/General_MIDI">Wikipedia</a>.
     * @param program The program
     * @param channel The channel
     * @return The instrument
     */
    private Instrument programToInstrument(int program, int channel) {
        if (channel == 9) { // Note that java counts channels from 0 while wikipedia starts at 1
            return Instrument.BASS_DRUM;
        }

        if (program >= 24 && program <= 39 || program >= 43 && program <= 46) {
            return Instrument.BASS;
        }

        if (program >= 113 && program <= 119) {
            return Instrument.BASS_DRUM;
        }

        if (program >= 120 && program <= 127) {
            return Instrument.SNARE_DRUM;
        }

        return Instrument.PIANO;
    }
}
