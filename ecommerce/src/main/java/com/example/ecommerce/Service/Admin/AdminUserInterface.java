package com.example.ecommerce.Service.Admin;

import java.util.List;

import com.example.ecommerce.Dto.updateOrdersDto;
import com.example.ecommerce.Dto.DateDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Dto.ViewOrdersDto;
import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Users;

public interface AdminUserInterface {


	public boolean addUser(Users u);
	public boolean deleteUser(long uname);
	public List<Users> viewBlockUsersList();
	public List<Users> viewAllUsers();
	public List<Users> viewNewUsersList();
	public List<Orders> getOrdersByUid(long id);
	public List<Users> getUsersOrdered();
//	public boolean deleteOrders();
	public boolean updateOrders(long id,updateOrdersDto c);
	public boolean updateUser(long u,String msg);
	public List<ViewOrdersDto> getAllOrders();
	public List<ViewOrdersDto> getOrdersCountByDayAndCat(DateDto d);
	public Response getOrdersCountByCategory(String cat);
	public Response getOrderCountByCatAndName(String c,String n);
}
