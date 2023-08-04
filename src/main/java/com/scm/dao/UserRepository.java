package com.scm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scm.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>
{
		//	@Query("select u from User u where u.email = : email")
       //   public User getUserByUserName(@Param("email") String email);
			@Query("SELECT u FROM User u WHERE u.email = :email")
			public User getUserByUserName(@Param("email") String email);
}
