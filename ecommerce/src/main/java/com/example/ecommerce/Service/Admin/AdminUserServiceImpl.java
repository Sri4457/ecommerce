package com.example.ecommerce.Service.Admin;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.updateOrdersDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Repository.OrdersRepo;
import com.example.ecommerce.Repository.ProductRepo;
import com.example.ecommerce.Repository.UserRepo;
import com.example.ecommerce.Service.EmailService;

@Service
public class AdminUserServiceImpl implements AdminUserInterface{

	@Autowired
	UserRepo urepo;
	
	@Autowired
	EmailService es;
	
	@Autowired
	ProductRepo prepo;
	
	@Autowired
	OrdersRepo orepo;

	
	@Override
	public boolean addUser(Users u) {
		boolean b=false;
		try {
			urepo.save(u);
			String message="Thank you for choosing our ecommerce website. Thanks for Registering";
			es.notifyUser(u.getEmail(), message,"Welcoming Mail");
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	@Override
	public boolean deleteUser(String uname) {
		boolean b=false;
		try {
			urepo.delete(urepo.findByUsername(uname));
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}
	
	@Override
	public boolean updateUser(String u) {
		boolean b=false;
		try {
		Users user=urepo.findByUsername(u);
		user.setUserstatus("released");
		urepo.save(user);
		b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	@Override
	public List<Users> viewBlockUsersList() {
		return urepo.findByUserstatus("unblocked");
	}

	@Override
	public List<Users> viewAllUsers() {
		return urepo.findAll();
	}

	@Override
	public List<Users> viewNewUsersList() {
		return urepo.findByUserstatus("blocked");
	}

	@Override
	public List<Users> getUsersOrdered() {
		
		return urepo.findAll();
	}

//	@Override
//	public boolean deleteOrders() {
//		List<Users> list=urepo.findAll();
//		boolean b=false;
//		try {
//			for(Users s:list)
//			{
//				List<Orders> l=s.getOrders();
//				for(Orders o:l)
//				{
//					if(o.getStatus().equalsIgnoreCase("delivered"))
//					{
//						l.remove(o);
//					}
//				}
//				s.setOrders(l);
//				urepo.save(s);
//				b=true;
//			}
//		}
//		catch(Exception e)
//		{
//			System.out.println(e);
//		}
//		return b;
//	}

	@Override
	public boolean updateOrders(String uname, List<updateOrdersDto> c) {
		Users u=urepo.findByUsername(uname);
		List<Orders> l=u.getOrders();
		HashMap<String,String> map=new HashMap<>();
		for(int i=0;i<c.size();i++)
			map.put(c.get(i).getPname()+c.get(i).getCategory(), c.get(i).getStatus());
		System.out.println(map);
		boolean b=false;
		try 
		{
			String msg="";
			for(Orders o:l)
			{
				
				if(map.containsKey((o.getP().getName()+o.getP().getCategory())))
				{
					o.setOrder_status(map.get((o.getP().getName()+o.getP().getCategory())));
					msg+="The Product Name "+o.getP().getName()+" in your order is "+o.getOrder_status()+".\n";
					b=true;
				}
			}
			u.setOrders(l);
			urepo.save(u);
			if(b)
			{
				es.notifyUser(u.getEmail(), msg, "Delivery Update Mail");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	@Override
	public Response getOrdersCountByDay(Date d1,Date d2) {
		Response response=null;
		try {
			int count=orepo.getCountByDate(d1, d2).size();
			response=new Response(false,"The Total orders between specific dates "+d1+" and "+d2+" are : "+count);
		}
		catch(Exception e)
		{
			System.out.println(e);
			response=new Response(true, "Something Went Wrong while fetching count of orders on specific date");
		}
		return response;
	}

	
	
}
