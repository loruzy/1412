package com.example.demo.services;

import com.example.demo.model.Task;
import com.example.demo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> getTasks(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
