package com.example.ecommerce.Service.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public boolean updateOrders(String uname, List<String> pname) {
		Users u=urepo.findByUsername(uname);
		List<Orders> l=u.getOrders();
		boolean b=false;
		try 
		{
			String msg="";
			for(Orders o:l)
			{
				
				if(pname.contains(prepo.findById(o.getProduct_id()).get().getName()))
				{
					o.setStatus("delivered");
					msg+="The Product Name "+prepo.findById(o.getProduct_id()).get().getName()+" in your order is delivered.\n";
					b=true;
				}
			}
			u.setOrders(l);
			urepo.save(u);
			if(b)
			{
				es.notifyUser(u.getEmail(), msg, "Delivery Mail");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}

	
}
