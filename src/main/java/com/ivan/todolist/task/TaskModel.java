package com.ivan.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

// creating tasks table

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private String startAt;
    private String endAt;
    private String priority;

    // filter to check if user is valid
    // for user to create a task he needs to send an authentication
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
