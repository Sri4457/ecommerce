package com.example.ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.Model.Products;

public interface ProductRepo extends JpaRepository<Products,Long>{

	
	Products findByName(String name);

	List<Products> findByCount(int count);
	
}
