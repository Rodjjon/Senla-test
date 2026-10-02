package com.senla.senlatest.controller;

import com.senla.senlatest.dto.CTask;
import com.senla.senlatest.dto.RTask;
import com.senla.senlatest.entity.TextTask;
import com.senla.senlatest.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskControl {

    private final TaskService taskService;

    public TaskControl(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createTask(@RequestBody CTask req) {
        UUID id = taskService.createTask(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RTask> getTask(@PathVariable UUID id) {
        TextTask task = taskService.getTask(id);

        if (task == null) {
            throw new RuntimeException("Task with id " + id + " not found");
        }

        RTask response = switch (task.getStatus()) {
            case DONE -> new RTask(task.getId(), task.getStatus(), task.getFinalText(), null);
            case ERROR -> new RTask(task.getId(), task.getStatus(), null, task.getErrMsg());
            default -> new RTask(task.getId(), task.getStatus(), null, null);
        };

        return ResponseEntity.ok(response);
    }
}