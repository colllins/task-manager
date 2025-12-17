package com.collins.taskmanager.io;

import com.collins.taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

public interface TaskStorage {

    void saveAll(List<Task> tasks) throws IOException;

    List<Task> loadAll() throws IOException;
}
