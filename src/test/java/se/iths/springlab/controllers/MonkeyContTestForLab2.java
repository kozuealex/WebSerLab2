package se.iths.springlab.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.iths.springlab.dtos.MonkeyDto;
import se.iths.springlab.services.Service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MonkeyContTestForLab2 {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service testService;

    @Before
    public void init() {
        MonkeyDto monkeyDto = new MonkeyDto(1, "TestMonkey", "TestType", 1, "M");
        when(testService.getOne(1)).thenReturn(Optional.of(monkeyDto));
    }

    @Test
    public void callingURLAnimalsReturnsAllMonkeysAsJson() throws Exception {
        when(testService.getAllMonkeys())
                .thenReturn(List.of(new MonkeyDto(2, "TestMonkey2", "TestType2", 2, "M"),
                                    new MonkeyDto(3, "TestMonkey3", "TestType3", 3, "F")));
        var result = mockMvc.perform(get("/animals")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("TestMonkey2")))
                .andExpect(jsonPath("$[1].id", Matchers.is(3)))
                .andExpect(jsonPath("$[1].name", Matchers.is("TestMonkey3")))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        verify(testService, times(1)).getAllMonkeys();
    }

    @Test
    public void callingGetOneWithIdReturnsOneMonkey() throws Exception {
        mockMvc.perform(get("/animals/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("TestMonkey")))
                .andExpect(jsonPath("$.type", Matchers.is("TestType")))
                .andExpect(jsonPath("$.weight", Matchers.is(1)))
                .andExpect(jsonPath("$.gender", Matchers.is("M")));
        verify(testService, times(1)).getOne(1);
    }

    @Test
    public void callingGetOneWithWrongIdReturns404() throws Exception {
        mockMvc.perform(get("/animals/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void callingPostWithMonkeyShouldSaveAndReturnNewMonkeyWithId() throws Exception {
        var monkeyDto = new MonkeyDto(4, "TestMonkey4", "TestType4", 4, "F");
        when(testService.createMonkey(any(MonkeyDto.class))).thenReturn(monkeyDto);
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(monkeyDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(4)))
                .andExpect(jsonPath("$.name", Matchers.is("TestMonkey4")))
                .andExpect(jsonPath("$.type", Matchers.is("TestType4")))
                .andExpect(jsonPath("$.weight", Matchers.is(4)))
                .andExpect(jsonPath("$.gender", Matchers.is("F")))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(201);
        verify(testService, times(1)).createMonkey(any(MonkeyDto.class));
    }

    @Test
    public void callingDeleteWithIdRemovesMonkey() throws Exception {
        doNothing().when(testService).delete(1);
        mockMvc.perform(delete("/animals/1"))
                .andExpect(status().isOk());
        verify(testService, times(1)).delete(1);
    }

    @Test
    public void callingReplacesWithIdReturnsOK() throws Exception {
        var newMonkeyDto = new MonkeyDto(1, "TestMonkey5", "TestType5", 5, "M");
        mockMvc.perform(put("/animals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(newMonkeyDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void callingUpdateWithIdReturnsOK() throws Exception {
        String patchText = "{\"name\":\"ChangeMonkey\"}";
        mockMvc.perform(patch("/animals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchText)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void callingGetMonkeyByNameReturnsOK() throws Exception {
        String name = "TestMonkey";
        mockMvc.perform(get("/animals/search")
                .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());
    }
}
