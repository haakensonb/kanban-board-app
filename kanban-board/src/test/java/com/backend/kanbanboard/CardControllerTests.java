package com.backend.kanbanboard;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.kanbanboard.models.Board;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.services.KanbanService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
@ActiveProfiles("test")
public class CardControllerTests {
    @InjectMocks
    Card card;

    @InjectMocks
    ListModel list;

    @InjectMocks
    Board board;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objmap;

    @MockBean
    private KanbanService kanbanService;

    @BeforeEach
    public void init(){
        // Card and List Id can't be null because CardModelAssembler needs them.
        card.setId(1L);
        list.setId(1L);
        card.setList(list);
    }

    @Test
    public void testGetAllCards() throws Exception {
        mockMvc.perform(get("/cards")).andExpect(status().isOk());
    }

    @Test
    public void testGetCardById() throws Exception {
        doReturn(card).when(kanbanService).getCard(1L);

        mockMvc.perform(get("/cards/1")).andExpect(status().isOk());
    }

    @Test
    public void testCreateCard() throws Exception {
        doReturn(card).when(kanbanService).createCard(any(Card.class));

        mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(card)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCard() throws Exception {
        card.setTitle("Updated Test Card");
        card.setDescription("blash");
        when(kanbanService.updateCard(any(Card.class), anyLong())).thenReturn(card);

        mockMvc.perform(
                put("/cards/1").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(card)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Updated Test Card"))
                .andExpect(jsonPath("$.description").value("blash"));
    }

    @Test
    public void testDeleteCard() throws Exception {
        mockMvc.perform(delete("/cards/1")).andExpect(status().isNoContent());
    }
}
