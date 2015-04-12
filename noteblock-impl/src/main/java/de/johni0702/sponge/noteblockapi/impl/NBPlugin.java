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

import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import de.johni0702.sponge.noteblockapi.SongParser;
import de.johni0702.sponge.noteblockapi.SongPlayers;
import de.johni0702.sponge.noteblockapi.fade.LinearFade;
import de.johni0702.sponge.noteblockapi.song.Song;
import de.johni0702.sponge.noteblockapi.songplayer.SongPlayer;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.service.ServiceManager;

import java.io.File;
import java.io.IOException;

@Plugin(id = "de.johni0702.noteblockapi",
        name = "NoteBlockAPI",
        version = "1.0")
public class NBPlugin {

    @Inject private Logger logger;
    @Inject private Game game;

    @Inject private NBSongParser songParser;
    @Inject private NBSongPlayers songPlayers;

    @Subscribe
    public void onInitialization(InitializationEvent event) {
        System.out.println("INIT");
        try {
            event.getGame().getServiceManager().setProvider(this, SongParser.class, songParser);
        } catch (ProviderExistsException ignored) {}
        try {
            event.getGame().getServiceManager().setProvider(this, SongPlayers.class, songPlayers);
        } catch (ProviderExistsException ignored) {}
    }



    @Subscribe
    public void test(PlayerInteractEvent event) throws IOException {
        System.out.println("INTERACT: " + event);
        test("test.nbs", event.getPlayer());
    }
    @Subscribe
    public void test(PlayerChatEvent event) throws IOException {
        System.out.println("CHAT: " + event);
        test("test.nbs", event.getPlayer());
    }

    public void test(String file, Player player) throws IOException {
        ServiceManager serviceManager = game.getServiceManager();

        // Parse song
        SongParser songParser = serviceManager.provide(SongParser.class).get();
        Song song = songParser.parseNBS(new File(file));

        // Play song
        SongPlayers songPlayers = serviceManager.provide(SongPlayers.class).get();
        SongPlayer songPlayer = songPlayers.createSongPlayer(song);
        songPlayer.getPlayBackMethods().add(songPlayers.createSoundEffectPlayBack(game.getRegistry(), Vector3d.ZERO));
        songPlayer.addListener(player);
        songPlayer.setFadeIn(new LinearFade());
        songPlayer.setFadeInDuration(10 * 20);
        songPlayer.setFadeOut(new LinearFade());
        songPlayer.setFadeOutDuration(10 * 20);
        songPlayer.start();
    }
}
