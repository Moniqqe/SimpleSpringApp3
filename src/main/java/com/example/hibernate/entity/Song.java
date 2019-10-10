package com.example.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="song_id")
    private int song_id;

    @Column(name="name", unique = true)
    private String name;

    @Column(name="genre")
    private String genre;

    @Column(name="description")
    private String description;

    @ManyToMany(cascade = {
            CascadeType.ALL,
            CascadeType.MERGE
    })
    @JoinTable(name = "songs_artists",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artistList;

    public Song() {
        this.artistList = new ArrayList<>();
    }

    public Song(String name, String genre, String description) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.artistList = new ArrayList<>();
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    @Override
    public String toString() {
        return "Song{" +
                "user_id=" + song_id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", artistList=" + artistList +
                '}';
    }
}
