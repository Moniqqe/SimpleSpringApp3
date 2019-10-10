package com.example.hibernate.rest;

import com.example.hibernate.entity.Song;
import com.example.hibernate.entity.Artist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.text.MessageFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    private Song song;

    private Artist artist;

    @Before
    public void setUp() throws Exception {
        song = new Song("adle", "jazz", "test");

    }

    @After
    public void tearDown() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/song/all"));
        mvc.perform(MockMvcRequestBuilders.delete("/artist/all"));
    }

    @Test
    public void addSong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/song/add")
                .param("name", song.getName())
                .param("genre", song.getGenre())
                .param("description", song.getDescription())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addSongDuplicate() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/song/add")
                .param("name", song.getName())
                .param("genre", song.getGenre())
                .param("description", song.getDescription())
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request);
    }

    @Test
    public void addSongMissingData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/song/add")
                .param("name", song.getName())
                .param("description", song.getDescription())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void getSong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/song/adle")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addSongToArtist() throws Exception {
        Artist a = new Artist("aTest");

        mvc.perform(MockMvcRequestBuilders.post("/song/add")
                .param("name", song.getName())
                .param("genre", song.getGenre())
                .param("description", song.getDescription())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.post("/artist/add")
                .param("name", a.getName())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.post(MessageFormat.format("/artist/{0}/add/{1}", a.getName(), song.getName()))
                .param("name", a.getName())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}