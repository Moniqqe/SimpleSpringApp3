package com.example.hibernate.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
public class Artist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="artist_id")
    private int artist_id;

    @Column(name="name", unique = true)
    private String name;

    @ManyToMany(cascade = {
            CascadeType.ALL,
            CascadeType.MERGE
    })
    @JoinTable(name = "songs_artists",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songList;

    public Artist() {
        this.songList = new ArrayList<>();
    }

    public Artist(String name) {
        this.name = name;
        this.songList = new ArrayList<>();
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getUserList() {
        return songList;
    }

    public void setUserList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artist_id=" + artist_id +
                ", name='" + name + '\'' +
                '}';
    }
}
