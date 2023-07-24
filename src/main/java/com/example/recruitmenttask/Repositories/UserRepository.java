package com.example.recruitmenttask.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recruitmenttask.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Page<User> findByNameOrSurnameOrLogin(String name, String surname, String login, Pageable pageable);
}