package com.example.ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.Model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users,Long>{

	
	Users findByUsername(String username);
	
	List<Users> findByUserstatus(String status);
	
}
