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
	
	
	@Query(value="select * from products where category=?1 and  name like ?2 ",nativeQuery=true)
	List<Products> getByNameByCategory(String category,String name);
	
	@Query(value="select distinct(category) from products ",nativeQuery = true)
	List<String> getAllCategories();
	
	List<Products> findByCategory(String category);

	@Query(value="select * from products p where category= :category order by CASE WHEN :sortField = 'name' THEN p.name END asc, CASE WHEN :sortField = 'count' THEN p.count END asc, CASE WHEN :sortField = 'price' THEN p.price END asc,CASE WHEN :sortField = 'date' THEN p.date END asc ",nativeQuery=true)
	List<Products> findProductsByCatByAscOrder(String category, String sortField);
	
	@Query(value="select * from products p where category= :category order by CASE WHEN :sortField = 'name' THEN p.name END desc, CASE WHEN :sortField = 'count' THEN p.count END desc, CASE WHEN :sortField = 'price' THEN p.price END desc,CASE WHEN :sortField = 'date' THEN p.date END desc ",nativeQuery=true)
	List<Products> findProductsByCatByDescOrder(String category, String sortField);
	
	
}
