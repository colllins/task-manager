package com.collins.taskmanager.io;

import com.collins.taskmanager.model.Task;
import com.collins.taskmanager.model.TaskPriority;
import com.collins.taskmanager.model.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileTaskStorage implements TaskStorage {

    private final String file = "fileStorage.txt";

    /**
     * Serializes all tasks to a text file.
     * <p>
     * Each task is written as a single line in the format:
     * id;title;description;status;priority;dueDate;createdAt;updatedAt
     * Existing file contents are fully replaced.
     *
     * @param tasks list of tasks to write
     * @throws IOException if an error occurs while writing the file
     */
    @Override
    public void saveAll(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task task:tasks) {
            String line = task.getId()+";"+task.getTitle()+";"+task.getDescription()+";"+task.getStatus()+";"+task.getPriority()+";"+task.getDueDate()+";"+task.getCreatedAt()+";"+task.getUpdatedAt();
            lines.add(line);
        }
        Files.write(Path.of(file),lines);

    }

    /**
     * Loads all tasks from the backing text file.
     * <p>
     * If the file does not exist, an empty list is returned.
     * Each line is expected in the format:
     * id;title;description;status;priority;dueDate;createdAt;updatedAt
     *
     * @return list of tasks reconstructed from the file
     * @throws IOException if an error occurs while reading the file
     */
    @Override
    public List<Task> loadAll() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if(!Files.exists(Path.of(file))){
            return tasks;
        }
        List<String> lines = Files.readAllLines(Path.of(file));
        for (String line:lines) {
            String[] taskLine = line.split(";");
            Task task = new Task();
            task.setId(Integer.parseInt(taskLine[0]));
            task.setTitle(taskLine[1]);
            task.setDescription(taskLine[2]);
            task.setStatus(TaskStatus.valueOf(taskLine[3]));
            task.setPriority(TaskPriority.valueOf(taskLine[4]));
            task.setDueDate(LocalDate.parse(taskLine[5]));
            task.setCreatedAt(LocalDateTime.parse(taskLine[6]));
            task.setUpdatedAt(LocalDateTime.parse(taskLine[7]));

            tasks.add(task);
        }
        return tasks;
    }
}
