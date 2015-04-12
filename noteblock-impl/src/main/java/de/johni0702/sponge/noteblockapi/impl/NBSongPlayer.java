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

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.inject.Inject;
import de.johni0702.sponge.noteblockapi.event.PlayerStartListenSongEvent;
import de.johni0702.sponge.noteblockapi.event.PlayerStopListenSongEvent;
import de.johni0702.sponge.noteblockapi.event.SongPlayerEndEvent;
import de.johni0702.sponge.noteblockapi.event.SongPlayerStartEvent;
import de.johni0702.sponge.noteblockapi.fade.Fade;
import de.johni0702.sponge.noteblockapi.playback.PlayBackMethod;
import de.johni0702.sponge.noteblockapi.song.Layer;
import de.johni0702.sponge.noteblockapi.song.NoteBlock;
import de.johni0702.sponge.noteblockapi.song.Song;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import de.johni0702.sponge.noteblockapi.songplayer.SongProvider;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.event.EventManager;
import org.spongepowered.api.service.scheduler.SynchronousScheduler;
import org.spongepowered.api.service.scheduler.Task;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of SongPlayer.
 */
public class NBSongPlayer implements SongPlayer, Runnable {

    /**
     * Current time or -1 if inactive.
     */
    private int tick = -1;

    /**
     * Passed time. Necessary for songs with a tempo != 20tps
     */

    /**
     * Current song.
     */
    private Song song;

    /**
     * The song provider.
     */
    private SongProvider songProvider;

    /**
     * List of playback methods.
     */
    private List<PlayBackMethod> playBacks = new CopyOnWriteArrayList<>();

    /**
     * Current volume (0 being silent, 1 being full)
     */
    private double volume;

    /**
     * List of listeners.
     */
    private List<Player> listeners = new CopyOnWriteArrayList<>();


    /**
     * Map of player volumes.
     */
    private Map<Player, Double> playerVolume = new MapMaker().weakKeys().concurrencyLevel(1).makeMap();

    /**
     * Fade operations.
     */
    private Fade fadeIn, fadeOut;

    /**
     * Duration for fade operations.
     */
    private int fadeInDuration, fadeOutDuration;

    /**
     * The task for this song player.
     */
    private Task task;

    private final PluginContainer plugin;
    private final EventManager eventBus;
    private final SynchronousScheduler syncScheduler;

    @Inject
    public NBSongPlayer(Game game, PluginContainer plugin) {
        this.syncScheduler = game.getSyncScheduler();
        this.eventBus = game.getEventManager();
        this.plugin = plugin;

        eventBus.register(plugin, this);
    }

    @Override
    public Song getSong() {
        return song;
    }

    @Override
    public void setSong(Song song) {
        checkNotNull(song, "song");
        if (isPlaying() && !stop()) {
            return; // Failed to stop current song
        }
        this.song = song;
    }

    @Override
    public SongProvider getSongProvider() {
        return songProvider;
    }

    @Override
    public void setSongProvider(SongProvider songProvider) {
        this.songProvider = songProvider;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public void setVolume(double volume) {
        Preconditions.checkArgument(volume >= 0 && volume <= 1, "volume must be between 0 and 1 (inclusive)");
        this.volume = volume;
    }

    @Override
    public double getVolume(Player player) {
        Double volume = playerVolume.get(player);
        return volume == null ? 1 : 0;
    }

    @Override
    public void setVolume(Player player, double volume) {
        playerVolume.put(player, volume);
    }

    @Override
    public boolean isPlaying() {
        return task != null;
    }

    @Override
    public int currentTick() {
        return tick;
    }

    @Override
    public List<PlayBackMethod> getPlayBackMethods() {
        return playBacks;
    }

    @Override
    public Fade getFadeIn() {
        return fadeIn;
    }

    @Override
    public Fade getFadeOut() {
        return fadeOut;
    }

    @Override
    public int getFadeInDuration() {
        return fadeInDuration;
    }

    @Override
    public int getFadeOutDuration() {
        return fadeOutDuration;
    }

    @Override
    public void setFadeIn(Fade fade) {
        this.fadeIn = fade;
    }

    @Override
    public void setFadeOut(Fade fade) {
        this.fadeOut = fade;
    }

    @Override
    public void setFadeInDuration(int duration) {
        this.fadeInDuration = duration;
    }

    @Override
    public void setFadeOutDuration(int duration) {
        this.fadeOutDuration = duration;
    }

    @Override
    public List<Player> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    @Override
    public void addListener(Player player) {
        if (isPlaying()) {
            if (eventBus.post(new PlayerStartListenSongEvent(this, Arrays.asList(player)))) {
                return;
            }
        }
        listeners.add(player);
    }

    @Override
    public void removeListener(Player player) {
        if (isPlaying()) {
            if (eventBus.post(new PlayerStopListenSongEvent(this, Arrays.asList(player), true))) {
                return;
            }
        }
        listeners.remove(player);
    }

    @Override
    public boolean start() {
        Preconditions.checkState(!isPlaying(), "Already playing");
        Preconditions.checkState(song != null || songProvider != null, "Either song or song provider must be set");
        if (song == null) {
            song = checkNotNull(songProvider.getNextSong(this), "No song set and song provider returned null");
        }
        song.updateLength();
        tick = 0;
        if (eventBus.post(new SongPlayerStartEvent(this, listeners))) {
            return false;
        }
        task = syncScheduler.runRepeatingTask(plugin, this, 1).get();
        return true;
    }

    @Override
    public boolean stop() {
        Preconditions.checkState(isPlaying(), "Not playing");
        if (eventBus.post(new SongPlayerEndEvent(this, listeners, true))) {
            return false;
        }
        task.cancel();
        tick = -1;
        return true;
    }

    @Override
    public void run() {
        double volume = this.volume;
        if (fadeIn != null && tick < fadeInDuration) {
            volume *= fadeIn.getVolume(fadeInDuration, tick, true);
        }
        if (fadeOut != null && tick >= song.getLength() - fadeOutDuration) {
            volume *= fadeOut.getVolume(fadeOutDuration, tick - (song.getLength() - fadeOutDuration), false);
        }

        for (Player player : listeners) {
            double playerVolume = volume * getVolume(player);
            for (Layer layer : song.getLayers()) {
                NoteBlock noteBlock = layer.getNoteBlock(tick);
                if (noteBlock != null) {
                    double noteVolume = playerVolume * layer.getVolume();
                    for (PlayBackMethod playBack : playBacks) {
                        playBack.play(this, player, noteBlock, noteVolume);
                    }
                }
            }
        }

        tick++;
        if (tick > song.getLength()) {
            eventBus.post(new SongPlayerEndEvent(this, listeners, false));
            song = null;

            if (songProvider != null) {
                song = songProvider.getNextSong(this);
            }

            if (song != null) {
                song.updateLength();
                tick = 0;
                if (!eventBus.post(new SongPlayerStartEvent(this, listeners))) {
                    return;
                }
            }
            tick = -1;
            task.cancel();
        }
    }

    @Subscribe
    public void onPlayerLeft(PlayerQuitEvent event) {
        if (listeners.contains(event.getPlayer())) {
            listeners.remove(event.getPlayer());
        }
    }
}
