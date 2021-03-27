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
import com.backend.kanbanboard.services.KanbanService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class BoardControllerTests {

    @InjectMocks
    private Board board;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objmap;

    @MockBean
    private KanbanService kanbanService;

    @Test
    public void testGetAllBoards() throws Exception {
        mockMvc.perform(get("/boards")).andExpect(status().isOk());
    }

    @Test
    public void testGetBoardById() throws Exception {
        doReturn(board).when(kanbanService).getBoard(anyLong());

        mockMvc.perform(get("/boards/1")).andExpect(status().isOk());
    }

    @Test
    public void testCreateBoard() throws Exception {
        board.setId(1L);
        doReturn(board).when(kanbanService).createBoard(any(Board.class));

        mockMvc.perform(
                post("/boards").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(board)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateBoard() throws Exception {
        board.setId(1L);
        board.setName("board1");
        doReturn(board).when(kanbanService).updateBoard(any(Board.class), anyLong());

        mockMvc.perform(
                put("/boards/1").contentType(MediaType.APPLICATION_JSON).content(objmap.writeValueAsString(board)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("board1"));
    }

    @Test
    public void testDeleteBoard() throws Exception {
        mockMvc.perform(delete("/boards/1")).andExpect(status().isNoContent());
    }

}
