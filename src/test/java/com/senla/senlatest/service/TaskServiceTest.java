package com.senla.senlatest.service;

import com.senla.senlatest.dto.CTask;
import com.senla.senlatest.entity.TaskStat;
import com.senla.senlatest.entity.TextTask;
import com.senla.senlatest.repository.TextTaskRep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TextTaskRep repository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Успешное создание задачи с валидными данными")
    void createTask_ValidInput_ShouldSaveAndReturnId() {
        CTask req = new CTask("Привет мир", "RU");
        UUID expectedId = UUID.randomUUID();

        when(repository.save(any(TextTask.class))).thenAnswer(invocation -> {
            TextTask task = invocation.getArgument(0);
            task.setId(expectedId);
            return task;
        });

        UUID resultId = taskService.createTask(req);

        assertNotNull(resultId);
        assertEquals(expectedId, resultId);
        verify(repository, times(1)).save(any(TextTask.class));
    }

    @Test
    @DisplayName("Выброс исключения при некорректном языке")
    void createTask_InvalidLanguage_ShouldThrowException() {
        CTask req = new CTask("Hello world", "FR");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(req)
        );

        assertEquals("Доступно только два языка: EN и RU", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Выброс исключения при слишком коротком тексте")
    void createTask_ShortText_ShouldThrowException() {
        CTask req = new CTask("аб", "RU");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(req)
        );

        assertEquals("Текст должен быть из 3 и более символов", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Выброс исключения, если текст содержит только цифры и спецсимволы")
    void createTask_NoLetters_ShouldThrowException() {
        CTask req = new CTask("12345!@#", "RU");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(req)
        );

        assertEquals("Текст должен содержать хотя бы одну букву алфавита", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Получение задачи по идентификатору, если задача существует")
    void getTask_WhenExists_ShouldReturnTask() {
        UUID id = UUID.randomUUID();
        TextTask mockTask = new TextTask();
        mockTask.setId(id);
        mockTask.setStatus(TaskStat.NEW);

        when(repository.findById(id)).thenReturn(Optional.of(mockTask));

        TextTask result = taskService.getTask(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository, times(1)).findById(id);
    }
}