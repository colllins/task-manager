package com.collins.taskmanager.repository;

import com.collins.taskmanager.model.Task;
import com.collins.taskmanager.model.TaskPriority;
import com.collins.taskmanager.model.TaskStatus;

import java.time.LocalDate;
import java.util.*;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Integer, Task> storage = new HashMap<>();
    private int idCounter = 1;

    /**
     * Saves the given task in memory.
     * If the task has no id yet (0), a new id is assigned.
     * Existing tasks with an id are updated in place.
     */
    @Override
    public Task save(Task entity) {
        if(entity.getId()==0){
            entity.setId(idCounter);
            idCounter++;
        }
        storage.put(entity.getId(),entity);

        return entity;
    }

    /**
     * Looks up a task by its id.
     *
     * @param id the identifier of the task
     * @return the task if it exists, or null if not found
     */
    @Override
    public Task findById(Integer id) {
           return storage.get(id);
    }

    /**
     * Returns a list containing all tasks currently stored in memory.
     *
     * @return list of all tasks
     */
    @Override
    public List<Task> findAll() {
//        List<Task> taskList = new ArrayList<>();
//        storage.values().forEach(task -> taskList.add(task));

        return new ArrayList<>(storage.values());
    }

    /**
     * Removes the task with the given id from storage, if it exists.
     *
     * @param id identifier of the task to delete
     */
    @Override
    public void deleteById(Integer id) {
        storage.remove(id);
    }

    /**
     * Removes all tasks from the in-memory storage.
     * After this call, the repository will be empty.
     */
    @Override
    public void deleteAll() {
        storage.clear();
    }

    /**
     * Finds all tasks that currently have the given status.
     *
     * @param status the status to filter by
     * @return list of tasks matching that status
     */
    @Override
    public List<Task> findByStatus(TaskStatus status) {
        List<Task> taskList = new ArrayList<>();
        storage.values().forEach(task -> {
            if(task.getStatus().equals(status)){
                taskList.add(task);
            }
        });
        return taskList;
    }

    /**
     * Finds all tasks that have the given priority level.
     *
     * @param priority the priority to filter by
     * @return list of tasks with the specified priority
     */
    @Override
    public List<Task> findByPriority(TaskPriority priority) {
        List<Task> taskList = new ArrayList<>();
        storage.values().forEach(task -> {
            if(task.getPriority().equals(priority)){
                taskList.add(task);
            }
        });
        return taskList;
    }

    /**
     * Finds all tasks with a due date strictly after the given date.
     *
     * @param date the lower bound date (exclusive)
     * @return list of tasks due after the given date
     */
    @Override
    public List<Task> findByDueAfter(LocalDate date) {
        List<Task> taskList = new ArrayList<>();
        storage.values().forEach(task -> {
            if(task.getDueDate().isAfter(date)){
                taskList.add(task);
            }
        });
        return taskList;
    }

    /**
     * Finds all tasks with a due date strictly before the given date.
     *
     * @param date the upper bound date (exclusive)
     * @return list of tasks due before the given date
     */
    @Override
    public List<Task> findByDueBefore(LocalDate date) {
        List<Task> taskList = new ArrayList<>();
        storage.values().forEach(task -> {
            if(task.getDueDate().isBefore(date)){
                taskList.add(task);
            }
        });
        return taskList;
    }
}
