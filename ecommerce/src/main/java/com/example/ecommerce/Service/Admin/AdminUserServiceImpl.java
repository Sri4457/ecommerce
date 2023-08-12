package com.example.ecommerce.Service.Admin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.CommonDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Users;
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
	
	@Override
	public Response login(Users u) {
		Response res=null;
		try 
		{
			Users user=urepo.findByUsername(u.getUsername());
			if(user==null)
			{
				res=new Response(true,"User not exit with the Username you provided");
			}
			else
			{
				String pass=user.getPassword();
				if(pass.equals(u.getPassword()))
				{
					res=new Response(false,"Login Successfull");
				}
				else
				{
					res=new Response(true,"Password you provided is wrong");
				}
			}
		}
		catch(Exception e)
		{
			res=new Response(true,"Sonething Went Wrong");
			System.out.println(e);
		}
		return res;
	}

	
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
		user.setStatus("released");
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
		return urepo.findByStatus("unblocked");
	}

	@Override
	public List<Users> viewAllUsers() {
		return urepo.findAll();
	}

	@Override
	public List<Users> viewNewUsersList() {
		return urepo.findByStatus("blocked");
	}

	@Override
	public List<Users> getUsersOrdered() {
		
		return null;
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
	public boolean updateOrders(String uname, List<CommonDto> c) {
		Users u=urepo.findByUsername(uname);
		List<Orders> l=u.getOrders();
		HashMap<String,String> map=new HashMap<>();
		for(int i=0;i<c.size();i++)
			map.put(c.get(i).getPname(), c.get(i).getStatus());
		System.out.println(map);
		boolean b=false;
		try 
		{
			String msg="";
			for(Orders o:l)
			{
				
				if(map.containsKey(prepo.findById(o.getProduct_id()).get().getName()))
				{
					o.setOrder_status(map.get(prepo.findById(o.getProduct_id()).get().getName()));
					msg+="The Product Name "+prepo.findById(o.getProduct_id()).get().getName()+" in your order is "+o.getOrder_status()+".\n";
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

	
	
}
