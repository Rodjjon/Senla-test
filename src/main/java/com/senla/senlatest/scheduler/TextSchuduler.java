package com.senla.senlatest.scheduler;

import com.senla.senlatest.entity.TaskStat;
import com.senla.senlatest.entity.TextTask;
import com.senla.senlatest.repository.TextTaskRep;
import com.senla.senlatest.service.YandexService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TextSchuduler
{
    private final TextTaskRep rep;
    private final YandexService yandexService;

    public TextSchuduler(TextTaskRep rep, YandexService yandexService)
    {
        this.rep = rep;
        this.yandexService = yandexService;
    }

    @Scheduled(fixedDelay = 5000)
    public void processNewTask()
    {
        List<TextTask> newTasks = rep.findTextTaskByStatus(TaskStat.NEW);

        for (TextTask task : newTasks)
        {
            task.setStatus(TaskStat.IN_PROGRESS);
            rep.save(task);

            try
            {
                String res = yandexService.correctText(task.getStartText(), task.getLanguage());
                task.setFinalText(res);
                task.setStatus(TaskStat.DONE);
            } catch (Exception err)
            {
                task.setStatus(TaskStat.ERROR);
                task.setErrMsg("Ошибка Яндекса: " +  err.getMessage());
            }
            rep.save(task);
        }
    }

}
