package com.collins.taskmanager.service;

import com.collins.taskmanager.model.Task;
import com.collins.taskmanager.model.TaskPriority;
import com.collins.taskmanager.model.TaskStatus;
import com.collins.taskmanager.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class TaskService {
    private final TaskRepository taskRepo;

    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    /**
     * Creates a new task by saving it to the repository.
     * Returns the saved task, including any generated id.
     */
    public Task createTask(Task task){

        return taskRepo.save(task);
    }

    /**
     * Returns all tasks currently stored by dueDate in the repository.
     */
    public List<Task> getAllTasks(){

//        List<Task> taskList = taskRepo.findAll();
//         List<Task> sortedByDueDate = taskList.stream()
//                 .sorted(Comparator.comparing(Task::getDueDate))
//                 .toList();

        return taskRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .toList();
    }

    /**
     * Retrieves a task by its id.
     * Rejects non-positive ids and ids that do not exist in the repository.
     *
     * @param id the identifier of the task
     * @return the matching Task
     * @throws RuntimeException if the id is invalid or no task is found
     */
    public Task getTaskById(int id){
        if(id<=0){
            throw new RuntimeException("Invalid ID");
        }

        Task task = taskRepo.findById(id);
        if(task == null){
            throw new RuntimeException("Task not found for id: "+id);
        }
        return task;
    }

    /**
     * Deletes the task with the given id, if it exists.
     * Relies on getTaskById to validate the id and existence.
     */
    public void deleteTask(int id){
        getTaskById(id);
        taskRepo.deleteById(id);
    }

    /**
     * Updates the status of a task with the given id.
     * <p>
     * Validates that the task exists, sets the new status,
     * saves it back to the repository and returns the updated task.
     *
     * @param id        identifier of the task to update
     * @param newStatus new status to apply
     * @return the updated Task
     * @throws RuntimeException if the id is invalid or the task does not exist
     */
    public Task updateStatus(int id, TaskStatus newStatus){
        Task task = getTaskById(id);
        task.setStatus(newStatus);
        return taskRepo.save(task);
    }

    /**
     * Returns all tasks that match the given status.
     */
    public List<Task> filterByStatus(TaskStatus status){
        return taskRepo.findByStatus(status);
    }

    /**
     * Returns all tasks that match the given priority level.
     */
    public List<Task> filterByPriority(TaskPriority priority){
        return taskRepo.findByPriority(priority);
    }

    /**
     * Returns all tasks with a due date strictly before the given date.
     */
    public List<Task> filterByDueDateBefore(LocalDate date){
        return taskRepo.findByDueBefore(date);
    }

    /**
     * Returns all tasks with a due date strictly after the given date.
     */
    public List<Task> filterByDueDateAfter(LocalDate date){
        return taskRepo.findByDueAfter(date);
    }
}
