package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void getFilmsStatusOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Film(0L,"название","Описание", LocalDate.of(1899,05,8), 50,new HashSet<>()))))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createFilmStatus200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Film(0L,"название","Описание", LocalDate.of(1899,05,8), 50,new HashSet<>()))))
                .andExpect(status().isOk());
    }

    @Test
    public void createFilmStatus400() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Film(0L,"","Описание", LocalDate.of(1893,05,8), 50,new HashSet<>()))))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void createFilmStatus1() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/films")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(new Film(0L,"Один дома","Описание", LocalDate.of(1893,05,8), 50))))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void createFilmStatus2() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Film(0L,"Один дома","Описание", LocalDate.of(1896,05,8), -50,new HashSet<>()))))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void createFilmStatus3() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/films")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(new Film(0L,"Один дома","ООООООООООООООООООООООООООООООООООООООООООООООООО" +
//                "ООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООО" +
//                "ОООООООООООООООООООППППППИИИИИИИИИИИИИИСССССССССААНИЕ", LocalDate.of(1893,05,8), 50))))
//                .andExpect(status().isBadRequest());
//    }
}