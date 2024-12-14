package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.services.TaskService;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/tasks")
    public String getTasks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model
    ) {
        Page<Task> taskPage = taskService.getTasks(page, size);

        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());
        model.addAttribute("totalItems", taskPage.getTotalElements());

        return "tasks";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("taskId") Long taskId, @RequestParam("email") String email) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            String subject = "Task Details: " + task.getTitle();
            String text = "Task Description: " + task.getDescription() + "\nStatus: " + task.getStatus();
            emailService.sendEmail(email, subject, text);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/add")
    public String addTask(@RequestParam("title") String title, @RequestParam("description") String description,
                          @RequestParam("status") String status, Model model) {
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setStatus(Task.Status.valueOf(status));

        taskService.saveTask(newTask);

        return "redirect:/tasks";
    }
}
