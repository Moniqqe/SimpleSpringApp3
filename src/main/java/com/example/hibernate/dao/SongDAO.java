package com.example.hibernate.dao;

import com.example.hibernate.entity.Artist;
import com.example.hibernate.entity.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongDAO extends AbstractDAO {

    public SongDAO() {
        setClazz(Song.class);
    }

    public Song getSongByName(String name) {
        return (Song) entityManager.createQuery("SElECT s FROM Song s WHERE s.name=:songName")
                .setParameter("songName", name)
                .getSingleResult();
    }

    public Song getSongByGenre(String genre) {
        return (Song) entityManager.createQuery("SElECT s FROM Song s WHERE s.genre=:songGenre")
                .setParameter("songGenre", genre)
                .getSingleResult();
    }

    public List<Artist> getArtistBySong(String name) {
        Song song = getSongByName(name);
        return song.getArtistList();
    }


}
