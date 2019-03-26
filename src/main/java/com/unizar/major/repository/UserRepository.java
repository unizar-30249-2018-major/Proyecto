package com.unizar.major.repository;

import com.unizar.major.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByFirstName (String FirstName);
    List<User> findAll();
    List<User> findByRol (String rol);
    User findById(Long id);
}
