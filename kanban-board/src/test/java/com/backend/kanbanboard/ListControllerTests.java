package com.backend.kanbanboard;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.kanbanboard.models.Board;
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
public class ListControllerTests {

    @InjectMocks
    private ListModel list;

    @InjectMocks
    private Board board;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objmap;

    @MockBean
    private KanbanService kanbanService;

    @BeforeEach
    void init(){
        // List and Board Ids can't be null because ListModelAssembler needs them.
        list.setId(1L);
        board.setId(1L);
        list.setBoard(board);
    }

    @Test
    public void testGetAllLists() throws Exception {
        mockMvc.perform(get("/lists")).andExpect(status().isOk());
    }

    @Test
    public void testGetListById() throws Exception {
        doReturn(list).when(kanbanService).getList(anyLong());

        mockMvc.perform(get("/lists/1")).andExpect(status().isOk());
    }

    @Test
    public void testCreateList() throws Exception {
        doReturn(list).when(kanbanService).createList(any(ListModel.class));

        mockMvc.perform(post("/lists").contentType(MediaType.APPLICATION_JSON)
                .content(objmap.writeValueAsString(list)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateList() throws Exception {
        list.setName("list1");
        doReturn(list).when(kanbanService).updateList(any(ListModel.class), anyLong());

        mockMvc.perform(
                put("/lists/1").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(list)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("list1"));
    }

    @Test
    public void testDeleteList() throws Exception {
        mockMvc.perform(delete("/lists/1")).andExpect(status().isNoContent());
    }

}
