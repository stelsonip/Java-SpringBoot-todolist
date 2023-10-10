package com.ivan.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    //defing a new method to find by username
    UserModel findByUsername(String username);
}
