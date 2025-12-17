package com.collins.taskmanager.model;

/**
 * Represents the current progress state of a task.
 */
public enum TaskStatus {
    TODO,           //Task has been created but work has not started yet.
    IN_PROGRESS,    //Task is currently being worked on.
    DONE            //Task has been completed.
}
