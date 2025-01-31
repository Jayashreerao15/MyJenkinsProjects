package com.itc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itc.model.MyUsers;

@Repository
public interface MyUserRepo extends JpaRepository<MyUsers, Integer> 
{
	Optional<MyUsers> findByUserName(String username);

	//Optional<MyUsers> findByEmail(String email);
	
}
