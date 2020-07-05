package com.stc.phoenix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stc.phoenix.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
