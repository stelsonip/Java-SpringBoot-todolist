package com.ivan.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

// defining getters and setters for all the atributes using lombok lib
@Data
// declaring table using the lombok lib
@Entity(name = "tb_users")
public class UserModel {

    @Id
    // letting jpa generate UUID automatically
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    // making sure there are no igual usernames (CONSTRAINT)
    @Column(unique = true)
    private String username;
    private String name;
    private String password;


    // defining when the database was created
    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
