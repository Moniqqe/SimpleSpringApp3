package com.example.hibernate.dao;

import com.example.hibernate.entity.Song;
import com.example.hibernate.entity.Artist;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistDAO extends AbstractDAO {

    public ArtistDAO() {
        setClazz(Artist.class);
    }

    public Artist getArtistByName(String name) {
        return (Artist) entityManager.createQuery("SElECT a FROM Artist a WHERE a.name=:artistName")
                .setParameter("artistName", name)
                .getSingleResult();
    }

    public List<Song> getSongsfromArtist(String name) {
        Artist artist = getArtistByName(name);
        return artist.getUserList();
    }
}
