package com.example.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.Model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{
	

}
