package com.example.ecommerce.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Products;

public interface OrdersRepo extends JpaRepository<Orders,Long>{
	
	@Query(value="select * from orders where time between ?1 and ?2 ",nativeQuery=true)
	List<Products> getCountByDate(Date d1,Date d2);

}
