package com.example.ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.Model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{
	
	
	@Query(value="select * from cart where userid=?1",nativeQuery = true)
	List<Cart> findByUserid(Long userid);
	
	@Query(value="select * from cart where userid=?1 and productname=?2 and productcat=?3",nativeQuery = true)
	Cart findByUIdPNamePCat(long id,String name,String cat);
}
