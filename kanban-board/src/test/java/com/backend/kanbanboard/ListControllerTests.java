package com.backend.kanbanboard;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.repositories.ListRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
public class ListControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objmap;

    @MockBean
    private ListRepository mockRepo;

    @Test
    public void testGetAllLists() throws Exception {
        mockMvc.perform(get("/lists")).andExpect(status().isOk());
    }

    @Test
    public void testGetListById() throws Exception {
        ListModel list = new ListModel("list1");
        Mockito.doReturn(Optional.of(list)).when(mockRepo).findById(1L);

        mockMvc.perform(get("/lists/1")).andExpect(status().isOk());
    }

    @Test
    public void testCreateList() throws Exception {
        ListModel list = new ListModel(1L, "list1");
        mockMvc.perform(post("/lists").contentType(MediaType.APPLICATION_JSON)
                .content(objmap.writeValueAsString(list)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateListIdPropertyIsReadOnly() throws Exception {
        ListModel list = new ListModel(1L, "list1");

        mockMvc.perform(
                post("/lists").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(list)))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void testUpdateList() throws Exception {
        ListModel list = new ListModel(1L, "list1");

        Mockito.when(mockRepo.save(any(ListModel.class))).thenReturn(list);

        mockMvc.perform(
                put("/lists/1").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(list)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("list1"));
    }

    @Test
    public void testDeleteCard() throws Exception {
        mockMvc.perform(delete("/lists/1")).andExpect(status().isOk());
    }
}
