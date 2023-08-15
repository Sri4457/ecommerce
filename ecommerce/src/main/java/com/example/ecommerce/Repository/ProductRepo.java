package com.example.ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.Model.Products;

public interface ProductRepo extends JpaRepository<Products,Long>{

	
	Products findByName(String name);

	List<Products> findByCount(int count);
	
	@Query(value="select * from products where name=?1 and category=?2",nativeQuery=true)
	Products getByNameAndcategory(String name,String category);
	
	@Query(value="select * from products where name like ?1 ",nativeQuery=true)
	List<Products> getByName(String name);
}
