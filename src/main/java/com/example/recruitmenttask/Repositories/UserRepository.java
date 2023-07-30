package com.example.recruitmenttask.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.recruitmenttask.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	@Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE users", nativeQuery = true)
    void truncateTable();
	Page<User> findByNameContainingOrSurnameContainingOrLoginContaining(String name, String surname, String login, Pageable pageable);
}