package com.collins.taskmanager.repository;

import com.collins.taskmanager.model.Task;
import com.collins.taskmanager.model.TaskPriority;
import com.collins.taskmanager.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository abstraction for working with Task entities.
 * Extends the generic Repository by adding task-specific query methods.
 */
public interface TaskRepository extends Repository<Integer, Task>{
    List<Task> findByStatus(TaskStatus status);                    //Returns all tasks that currently have the given status.
    List<Task> findByPriority(TaskPriority priority);              //Returns all tasks that have the given priority level.
    List<Task> findByDueAfter(LocalDate date);                     //Returns all tasks with a due date strictly after the given date.
    List<Task> findByDueBefore(LocalDate date);                    //Returns all tasks with a due date strictly before the given date.
}
