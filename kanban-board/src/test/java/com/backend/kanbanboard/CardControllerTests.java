package com.backend.kanbanboard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.kanbanboard.repositories.CardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    // @Test
    // public void testGetCardById() throws Exception {
    //     ListModel list = new ListModel("list1");
    //     Card card = new Card("title", "description", list);
    //     Mockito.doReturn(Optional.of(card)).when(mockRepo).findById(1L);

    //     mockMvc.perform(get("/cards/1")).andExpect(status().isOk());
    // }

    // @Test
    // public void testCreateCard() throws Exception {
    //     ListModel list = new ListModel("list1");
    //     mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON)
    //             .content(objmap.writeValueAsString(new Card("Test Card 3", "blah", list))))
    //             .andExpect(status().isCreated());
    // }

    // @Test
    // public void testCreateCardIdPropertyIsReadOnly() throws Exception {
    //     ListModel list = new ListModel("list1");
    //     Card card1 = new Card("Test", "", list);
    //     card1.setId(1L);

    //     mockMvc.perform(
    //             post("/cards").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(card1)))
    //             .andExpect(jsonPath("$.id").doesNotExist());
    // }

    // @Test
    // public void testUpdateCard() throws Exception {
    //     ListModel list = new ListModel("list1");
    //     Card card = new Card("Updated Test Card", "blash", list);
    //     Mockito.doReturn(Optional.of(card)).when(mockRepo).findById(1L);

    //     Mockito.when(mockRepo.save(any(Card.class))).thenReturn(card);

    //     mockMvc.perform(
    //             put("/cards/1").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(card)))
    //             .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Updated Test Card"))
    //             .andExpect(jsonPath("$.description").value("blash"));
    // }

    @Test
    public void testDeleteCard() throws Exception {
        mockMvc.perform(delete("/cards/1")).andExpect(status().isOk());
    }
}
