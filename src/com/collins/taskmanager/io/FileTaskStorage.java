package com.collins.taskmanager.io;

import com.collins.taskmanager.model.Task;

import java.io.IOException;
import java.util.List;

public class FileTaskStorage implements TaskStorage {

    private final String file = "fileStorage.txt";

    @Override
    public void saveAll(List<Task> tasks) throws IOException {
    }

    @Override
    public List<Task> loadAll() throws IOException {
        return null;
    }
}
