package com.senla.senlatest.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TextTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String startText;

    @Column(columnDefinition = "TEXT")
    private String finalText;

    @Column(length = 2, nullable = false)
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStat status;

    @Column(columnDefinition = "TEXT")
    private String errMsg;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getStartText() { return startText; }
    public void setStartText(String startText) { this.startText = startText; }

    public String getFinalText() { return finalText; }
    public void setFinalText(String finalText) { this.finalText = finalText; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public TaskStat getStatus() { return status; }
    public void setStatus(TaskStat status) { this.status = status; }

    public String getErrMsg() { return errMsg; }
    public void setErrMsg(String errMsg) { this.errMsg = errMsg; }
}