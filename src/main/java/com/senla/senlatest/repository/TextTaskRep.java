package com.senla.senlatest.repository;

import com.senla.senlatest.entity.TaskStat;
import com.senla.senlatest.entity.TextTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TextTaskRep extends JpaRepository<TextTask, UUID>
{
    List<TextTask> findTextTaskByStatus(TaskStat stat);
}
