package com.example.recruitmenttask.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recruitmenttask.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

}