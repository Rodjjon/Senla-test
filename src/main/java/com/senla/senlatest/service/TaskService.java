package com.senla.senlatest.service;

import com.senla.senlatest.dto.CTask;
import com.senla.senlatest.entity.TaskStat;
import com.senla.senlatest.entity.TextTask;
import com.senla.senlatest.repository.TextTaskRep;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {
    private final TextTaskRep repository;

    public TaskService(TextTaskRep repository) {
        this.repository = repository;
    }

    public UUID createTask(CTask req) {
        if (req.language() == null || (!req.language().equals("EN") && !req.language().equals("RU"))) {
            throw new IllegalArgumentException("Доступно только два языка: EN и RU");
        }
        if (req.text() == null || req.text().trim().length() < 3) {
            throw new IllegalArgumentException("Текст должен быть из 3 и более символов");
        }
        if (!req.text().matches(".*[a-zA-Zа-яА-ЯёЁ].*")) {
            throw new IllegalArgumentException("Текст должен содержать хотя бы одну букву алфавита");
        }

        TextTask task = new TextTask();
        task.setStartText(req.text());
        task.setLanguage(req.language());
        task.setStatus(TaskStat.NEW);

        TextTask saved = repository.save(task);
        return saved.getId();
    }

    public TextTask getTask(UUID id) {
        return repository.findById(id).orElse(null);
    }
}