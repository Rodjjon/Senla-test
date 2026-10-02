package com.senla.senlatest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.senlatest.dto.CTask;
import com.senla.senlatest.entity.TaskStat;
import com.senla.senlatest.entity.TextTask;
import com.senla.senlatest.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControl.class)
class TaskControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /tasks — успешное создание задачи возвращает HTTP 201 CREATED и ID")
    void createTask_ShouldReturn201AndId() throws Exception {
        CTask req = new CTask("Привед медвед", "RU");
        UUID generatedId = UUID.randomUUID();

        when(taskService.createTask(any(CTask.class))).thenReturn(generatedId);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(generatedId.toString()));
    }

    @Test
    @DisplayName("GET /tasks/{id} — успешное получение завершенной задачи со статусом DONE")
    void getTask_WhenDone_ShouldReturnTaskResponse() throws Exception {
        UUID id = UUID.randomUUID();
        TextTask task = new TextTask();
        task.setId(id);
        task.setStatus(TaskStat.DONE);
        task.setFinalText("Привет медвед");

        when(taskService.getTask(id)).thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.status").value("DONE"))
                .andExpect(jsonPath("$.finalText").value("Привет медвед"));
    }
}