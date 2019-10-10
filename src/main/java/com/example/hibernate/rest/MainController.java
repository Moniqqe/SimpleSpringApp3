package com.example.hibernate.rest;

import com.example.hibernate.dao.SongDAO;
import com.example.hibernate.dao.ArtistDAO;
import com.example.hibernate.entity.Artist;
import com.example.hibernate.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
public class MainController {

    private final SongDAO songDAO;

    private final ArtistDAO artistDAO;

    @Autowired
    public MainController(SongDAO songDAO, ArtistDAO artistDAO) {
        this.songDAO = songDAO;
        this.artistDAO = artistDAO;
    }

    @RequestMapping(value = "/song/add", method = RequestMethod.POST)
    public void addSong(@RequestParam(value = "name") String name, @RequestParam(value = "genre") String genre, @RequestParam(value = "description") String description) {
        songDAO.create(new Song(name, genre, description));
    }

    @RequestMapping(value = "/song/{name}", method = RequestMethod.GET)
    public Song getSong(@PathVariable(value = "name") String name) {
        try {
            return songDAO.getSongByName(name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @RequestMapping(value = "/song/{name}/artist", method = RequestMethod.GET)
    public List getSongArtist(@PathVariable(value = "name") String name) {
        return songDAO.getArtistBySong(name);
    }

    @RequestMapping(value = "/song/{name}", method = RequestMethod.DELETE)
    public void removeSong(@PathVariable(value = "name") String name) {
        try {
            Song song = songDAO.getSongByName(name);
            songDAO.delete(song);
        } catch (NoResultException e) {

        }

    }

    @RequestMapping(value = "/song/all", method = RequestMethod.DELETE)
    public void removeAllSongs() {
        List<Song> list = songDAO.findAll();
        for (Song s : list) {
            songDAO.delete(s);
        }
    }

    @RequestMapping(value = "/artist/add", method = RequestMethod.POST)
    public void addArtist(@RequestParam(value = "name") String name) {
        artistDAO.create(new Artist(name));

    }

    @RequestMapping(value = "/artist/{name}", method = RequestMethod.DELETE)
    public void removeArtist(@RequestParam(value = "name") String name) {
        Artist artist = artistDAO.getArtistByName(name);
        artistDAO.delete(artist);
    }

    @RequestMapping(value = "/artist/all", method = RequestMethod.DELETE)
    public void removeAllArtist() {
        List<Artist> artistList = artistDAO.findAll();
        for (Artist a : artistList) {
            artistDAO.delete(a);
        }
    }

    @RequestMapping(value = "/artist/{artistName}/add/{songName}", method = RequestMethod.POST)
    public void addSongToArtist(@PathVariable(value = "artistName") String artistName, @PathVariable(value = "songName") String songName) {
        Artist a = artistDAO.getArtistByName(artistName);
        Song s = songDAO.getSongByName(songName);
        a.getUserList().add(s);
        artistDAO.update(a);
    }

    @RequestMapping(value = "/artist/{artistName}/remove/{songName}", method = RequestMethod.POST)
    public void removeSongFromArtist(@PathVariable(value = "artistName") String artistName, @PathVariable(value = "songName") String songName) {
        Artist a = artistDAO.getArtistByName(artistName);
        Song s = songDAO.getSongByName(songName);
        a.getUserList().remove(s);
        artistDAO.update(a);
    }




}
