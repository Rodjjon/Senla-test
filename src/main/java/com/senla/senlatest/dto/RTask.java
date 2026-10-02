package com.senla.senlatest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senla.senlatest.entity.TaskStat;
import java.util.UUID;

public record RTask (UUID id, TaskStat status, String finalText, String errorMsg)
{}
