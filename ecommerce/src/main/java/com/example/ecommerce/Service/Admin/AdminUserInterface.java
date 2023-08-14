package com.example.ecommerce.Service.Admin;

import java.sql.Date;
import java.util.List;

import com.example.ecommerce.Dto.updateOrdersDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Users;

public interface AdminUserInterface {


	public boolean addUser(Users u);
	public boolean deleteUser(String uname);
	public List<Users> viewBlockUsersList();
	public List<Users> viewAllUsers();
	public List<Users> viewNewUsersList();
	public List<Users> getUsersOrdered();
//	public boolean deleteOrders();
	public boolean updateOrders(String uname,List<updateOrdersDto> c);
	public boolean updateUser(String u);
	public Response getOrdersCountByDay(Date d1,Date d2);
}
