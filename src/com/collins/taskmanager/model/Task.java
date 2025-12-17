package com.collins.taskmanager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a single to-do item in the task manager,
 * including its title, description, status, priority and timestamps.
 */
public class Task {
    private int id;                     // Unique identifier assigned by the repository.
    private String title;               // Short name of the task, shown in lists.
    private String description;         // Optional longer description with more details.
    private TaskStatus status;          // Current workflow state (TODO, IN_PROGRESS, DONE).
    private TaskPriority priority;      // Importance level of the task.
    private LocalDate dueDate;          // Optional calendar date when the task should be finished.
    private LocalDateTime createdAt;    // When the task was first created.
    private LocalDateTime updatedAt;    // When the task was last modified.

    public Task(String title, String description, TaskStatus status, TaskPriority priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updatedAt = LocalDateTime.now();
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        updatedAt = LocalDateTime.now();
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
        updatedAt = LocalDateTime.now();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "New Task Created: " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
