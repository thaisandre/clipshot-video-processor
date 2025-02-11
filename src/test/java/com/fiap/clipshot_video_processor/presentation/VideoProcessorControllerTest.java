package com.fiap.clipshot_video_processor.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.clipshot_video_processor.application.videoTask.retrieve.FindVideoTaskCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VideoProcessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnUnauthorizedInFindTaskByUserAndVideoId() throws Exception {
        String videoId= UUID.randomUUID().toString();
        FindVideoTaskCommand command = new FindVideoTaskCommand(1L, videoId);

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/1L/video/%s".formatted(videoId))
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void shouldReturnUnauthorizedInFindTasksByUserEndpoint() throws Exception {
        FindVideoTaskCommand command = new FindVideoTaskCommand(1L, UUID.randomUUID().toString());

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/1L/")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }
}