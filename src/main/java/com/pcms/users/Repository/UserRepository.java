package com.pcms.users.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pcms.users.Config.Model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	
	@Query(value = "Select * from user where userNameString =: username", nativeQuery = true)
	Users findByUsername(@Param("username") String username);

	@Query(value = "SELECT user_id FROM users WHERE mail_id = :username",nativeQuery = true)
	int findUserIdByMailId(@Param("username") String userName);

	@Query(value = "select * from users where status= :val", nativeQuery = true)
	public List<Users> getAllNewUsers(@Param ("val") String status);
	
	public Optional<Users> findByMailId(String emailString);
	
}

