package com.coursenet.springbasic.repository;

import com.coursenet.springbasic.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByUserName(String name);

}

