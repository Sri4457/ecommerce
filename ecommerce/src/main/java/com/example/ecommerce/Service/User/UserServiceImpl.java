package com.example.ecommerce.Service.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.Response;
import com.example.ecommerce.Dto.UserOrderDto;
import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Model.Orders;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Repository.CartRepository;
import com.example.ecommerce.Repository.ProductRepo;
import com.example.ecommerce.Repository.UserRepo;
import com.example.ecommerce.Service.EmailService;

@Service
public class UserServiceImpl implements UserInterface {

	@Autowired
	UserRepo urepo;
	
	@Autowired
	ProductRepo prepo;
	
	@Autowired
	EmailService es;
	
	@Autowired
	CartRepository crepo;
	
	@Override
	public boolean register(Users u) {
		boolean b=false;
		try
		{
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
	public Response forgetPassword(String username) {
		Response response=null;
		try
		{
			Users u=urepo.findByUsername(username);
			String msg="The Password you created for your account is : "+u.getPassword();
			try{
				es.notifyUser(u.getEmail(),msg , "Forget Password");
				response=new Response(false,"Password Sent to Email");
			}
			catch(Exception e)
			{
				response=new Response(true,"Something Went wrong while sendign the password");
			}
		}
		catch(Exception e)
		{
			response=new Response(true,"Some thing went wrong while fetching your details");
		}
		return response;
	}

	@Override
	public List<Products> viewAllProducts() {
		return prepo.findAll();
		
	}

	@Override
	public Products viewProductByName(String name) {
		// TODO Auto-generated method stub
		return prepo.findByName(name);
	}

	@Override
	public boolean UpdateProfile(Users u) {
		Users user=urepo.findByUsername(u.getUsername());
		user.setEmail(u.getEmail());
		user.setPassword(u.getPassword());
		boolean b=false;
		String msg=null;
		try
		{
			msg="Your Pofile is updated successfully";
			urepo.save(u);
			b=true;
		}
		catch(Exception e)
		{
			msg="Something Went Wrong";
			System.out.println(e);
		}
		es.notifyUser(u.getEmail(), msg, "Profile Update Mail");
		return b;
	}

	@Override
	public Users getByUname(String uname) {
		
		return urepo.findByUsername(uname);
	}

	private boolean checkProducts(Products pdto)
	{
		Products p=prepo.findByName(pdto.getName());
		if(p.getCount()>=pdto.getCount())
			return true;
		else
			return false;
	}
	private void updateProducts(Products list) {
		Products p=prepo.findByName(list.getName());
		p.setCount((p.getCount())-list.getCount());
		prepo.save(p);
	}

	@Override
	public List<UserOrderDto> getOrderByUname(String uname) {
		List<Orders> list=urepo.findByUsername(uname).getOrders();
		List<UserOrderDto> o=new ArrayList<>();
		for(int i=0;i<list.size();i++)
		{
			UserOrderDto obj=new UserOrderDto();
			obj.setId(list.get(i).getId());
			obj.setPname(list.get(i).getPname());
			obj.setQty(list.get(i).getQuantity());
			obj.setTotalcost(list.get(i).getCost());
			obj.setPcost(list.get(i).getPcost());
			obj.setStatus(list.get(i).getOrder_status());
			obj.setUname(uname);
			o.add(obj);
		}
		return o;
	}

	@Override
	public Response addToCart(Cart list) {
		try {
			crepo.save(list);
			return new Response(false,"Added to cart");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return new Response(true,"Somwthing went Wrong");
	}

	@Override
	public List<Response> submitCart(List<Long> list) {
		Users u=urepo.findById(crepo.findById(list.get(0)).get().getUserid()).get();
		List<Response> res=new ArrayList<>();
		try
		{
			if(!u.getUserstatus().equalsIgnoreCase("blocked")) 
			{
				String msg="";
				double price=0;
				for(int i=0;i<list.size();i++)
				{
					Cart c=crepo.findById(list.get(i)).get();
					Orders o=new Orders();
					o.setOrder_status("ordered");
					o.setQuantity(c.getQty());
					o.setPname(prepo.findById(c.getProductid()).get().getName());
					o.setCategory(prepo.findById(c.getProductid()).get().getCategory());
					o.setPcost(prepo.findById(c.getProductid()).get().getPrice());
					o.setTime(java.sql.Date.valueOf(LocalDate.now()));
					o.setCost(c.getQty()*(prepo.findById(c.getProductid())).get().getPrice());
					List<Orders> l=u.getOrders();
					l.add(o);
					u.setOrders(l);
					if(checkProducts(prepo.findById(c.getProductid()).get()))
					{
						Response r=new Response(false,"The Product with name "+o.getPname()+" is ordered");
						updateProducts(prepo.findById(c.getProductid()).get());
						msg+="The Product Name "+o.getPname()+" in successfully ordered.\n";
						urepo.save(u);
						price+=o.getCost();
						res.add(r);
						crepo.deleteById(list.get(i));
					}
					else
					{
						Response r=new Response(true,"The Product with name "+o.getPname()+" is not ordered as the products are not avaliable to your provided Quantity");
						res.add(r);
					}
				}
				if(msg.length()==0)
					es.notifyUser(u.getEmail(), "Some Thing Went wrong while ordering please try again", "Notifying Mail");
				else
				{
					msg+=" With the Total Cost is : "+price;
					es.notifyUser(u.getEmail(), msg, "Ordered Mail");
				}
			}
			else {
				Response response=new Response(true,"Still you are not released by Admin" );
				res.add(response);
			}
		}
		catch(Exception e)
		{
			Response response=new Response(true,"Something Went wrong try again");
			res.add(response);
			System.out.println(e);
		}
		return res;
	}

	

	
	

}
