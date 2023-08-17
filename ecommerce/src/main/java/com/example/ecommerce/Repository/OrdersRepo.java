package com.example.ecommerce.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.Model.Orders;

public interface OrdersRepo extends JpaRepository<Orders,Long>{
	
	@Query(value="select * from orders where time between ?1 and ?2 ",nativeQuery=true)
	List<Orders> getCountByDate(Date d1,Date d2);

	@Query(value="select count(id) from orders group by category=?1",nativeQuery=true)
	int getCountByCategory(String category);
	
	@Query(value="select count(id) from orders group by category=?1 and pname=?2",nativeQuery=true)
	int getCountByCategoryAndName(String category,String name);
}
