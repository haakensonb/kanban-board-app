package com.backend.kanbanboard;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.repositories.CardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class CardControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objmap;

    @MockBean
    private CardRepository mockRepo;

    @Test
    public void testGetAllCards() throws Exception {
        mockMvc.perform(get("/cards")).andExpect(status().isOk());
    }

    @Test
    public void testGetCardById() throws Exception {
        Card card = new Card("title", "description");
        Mockito.doReturn(Optional.of(card))
        .when(mockRepo)
        .findById(1L);
        
        mockMvc.perform(get("/cards/1")).andExpect(status().isOk());
    }

    @Test
    public void testCreateCard() throws Exception {
        mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(new Card("Test Card 3", "blah"))))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateCardIdPropertyIsReadOnly() throws Exception {
        Card card1 = new Card("Test", "");
        card1.setId(1L);

        mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(card1)))
            .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void testUpdateCard() throws Exception {
        Card card = new Card("Updated Test Card", "blash");
        Mockito.doReturn(Optional.of(card))
        .when(mockRepo)
        .findById(1L);

        Mockito.when(mockRepo.save(any(Card.class))).thenReturn(card);
        
        mockMvc.perform(put("/cards/1").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(card)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Test Card"))
                .andExpect(jsonPath("$.description").value("blash"));
    }

    @Test
    public void testDeleteCard() throws Exception {
        mockMvc.perform(delete("/cards/1")).andExpect(status().isOk());
    }
}
