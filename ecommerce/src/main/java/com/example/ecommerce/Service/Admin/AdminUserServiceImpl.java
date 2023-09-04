package com.example.ecommerce.Service.Admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.updateOrdersDto;
import com.example.ecommerce.Dto.DateDto;
import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Dto.ViewOrdersDto;
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
			String message="Thank you for choosing our ecommerce website. Thanks for Registering\n Your Username: "+u.getUsername()+" and Password : "+u.getPassword();
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
	public boolean deleteUser(long id) {
		boolean b=false;
		try {
			urepo.delete(urepo.findById(id).get());
			b=true;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return b;
	}
	
	@Override
	public boolean updateUser(long id,String msg) {
		boolean b=false;
		try {
		Users user=urepo.findById(id).get();
		if(msg.equalsIgnoreCase("release"))
		user.setUserstatus("released");
		else
			user.setUserstatus("blocked");
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
		return urepo.getAllUsersExcpetAdmin();
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
	public boolean updateOrders(long id, updateOrdersDto c) {
		Users u=urepo.findById(id).get();
		List<Orders> l=u.getOrders();
		boolean b=false;
		try 
		{
			String msg="";
			for(Orders o:l)
			{
				
				if(o.getId()==c.getId())
				{
					o.setOrder_status(c.getStatus());
					msg+="The Product Name "+o.getPname()+" in your order is "+o.getOrder_status()+".\n";
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
	public List<ViewOrdersDto> getOrdersCountByDayAndCat(DateDto d) {
		try {
			Date d1=Date.valueOf(d.getDateone());
			Date d2=Date.valueOf(d.getDatetwo());
			List<Orders> orders=orepo.getCountByDate(d1, d2,d.getCategory());
			List<ViewOrdersDto> result=new ArrayList<>();
			for(Orders o:orders)
			{
				ViewOrdersDto v=new ViewOrdersDto();
				v.setCategory(o.getCategory());
				v.setCost(o.getCost());
				v.setId(o.getId());
				v.setOrder_status(o.getOrder_status());
				v.setPcost(o.getPcost());
				v.setUid(orepo.getUseridForOrderId(o.getId()));
				v.setQuantity(o.getQuantity());
				v.setPname(o.getPname());
				v.setAddress(urepo.findById(orepo.getUseridForOrderId(o.getId())).get().getAddress());
				result.add(v);
			}
			return result;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}

	@Override
	public Response getOrdersCountByCategory(String cat) {
		int count=orepo.getCountByCategory(cat);
		if(count>0)
			return new Response(false,"The Number Orders for the "+cat+" Category are Zero");
		else {
			return new Response(false,"The Number Orders for the "+cat+" Category are : "+count);
		}
	}

	@Override
	public Response getOrderCountByCatAndName(String c, String n) {
		int count=orepo.getCountByCategoryAndName(c, n);
		if(count>0)
			return new Response(false,"The Number Orders for the particular Category and Product Name are Zero");
		else {
			return new Response(false,"The Number Orders for the "+c+" Category with the Product Name "+n+" : "+count);
		}
	}

	@Override
	public List<Orders> getOrdersByUid(long id) {
		return urepo.findById(id).get().getOrders();
	}

	@Override
	public List<ViewOrdersDto> getAllOrders() {
		try {
			
			List<Users> users=urepo.findAll();
			List<ViewOrdersDto> result=new ArrayList<>();
			for(Users user:users)
			{
				List<Orders> orders=user.getOrders();
				for(Orders o:orders)
				{
					ViewOrdersDto v=new ViewOrdersDto();
					v.setCategory(o.getCategory());
					v.setCost(o.getCost());
					v.setId(o.getId());
					v.setOrder_status(o.getOrder_status());
					v.setPcost(o.getPcost());
					v.setUid(orepo.getUseridForOrderId(o.getId()));
					v.setQuantity(o.getQuantity());
					v.setPname(o.getPname());
					v.setAddress(user.getAddress());
					result.add(v);
					
				}
			}
			return result;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}

	
	
}
