package com.collins.taskmanager.io;

import com.collins.taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

/**
 * Abstraction for saving and loading tasks to and from some
 * persistent storage (for example, a text file).
 */
public interface TaskStorage {

    /**
     * Writes all given tasks to storage, replacing any existing content.
     *
     * @param tasks list of tasks to persist
     * @throws IOException if an I/O error occurs while writing
     */
    void saveAll(List<Task> tasks) throws IOException;

    /**
     * Loads all tasks from storage.
     *
     * @return list of tasks read from storage, or an empty list if none exist
     * @throws IOException if an I/O error occurs while reading
     */
    List<Task> loadAll() throws IOException;
}
