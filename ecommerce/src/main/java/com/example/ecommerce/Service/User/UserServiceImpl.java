package com.example.ecommerce.Service.User;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Dto.Response;
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
			try
			{
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
		user.setAddress(u.getAddress());
		
		//user.setOrders(user.getOrders()); 
		boolean b=false;
		String msg=null;
		try
		{
			msg="Your Pofile is updated successfully";
			urepo.save(user);
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
	public Users getById(long id) {
		
		return urepo.findById(id).get();
	}

	private boolean checkProducts(int count,int qty)
	{
		if(count>=qty)
			return true;
		else
			return false;
	}
	private void updateProducts(Products p,int qty) {
		p.setCount((p.getCount())-qty);
		prepo.save(p);
	}

	@Override
	public List<Orders> getOrderByUId(long id) {
		List<Orders> list=urepo.findById(id).get().getOrders();
		return list;
	}

	@Override
	public Response addToCart(Cart list) {
		try {
			Products p=prepo.getByNameAndcategory(list.getProductname(), list.getProductcat());
			Cart c=crepo.findByUIdPNamePCat(list.getUserid(), list.getProductname(), list.getProductcat());
			if(c==null)
			{
				if(checkProducts(p.getCount(),list.getQty()))
				{
					crepo.save(list);
					return new Response(false,"Cart added Successfully");
				}
				else {
					return new Response(true,"Stock is less");
				}
			}
			else {
				if(checkProducts(p.getCount(),list.getQty()+c.getQty()))
				{
					c.setQty(list.getQty()+c.getQty());
					crepo.save(c);
					return new Response(false,"Cart added Successfully");
				}
				else {
					return new Response(true,"Stock is less");
				}
			}
		}
		catch(Exception e)		{
			System.out.println(e);
		}
		return new Response(true,"Something went Wrong");
	}

	@Override
	public Response submitCart(List<Cart> list) {
		Users u=urepo.findById(list.get(0).getUserid()).get();
		
		try
		{
			if(!u.getUserstatus().equalsIgnoreCase("blocked")) 
			{
				String msg="";
				double price=0;
				for(int i=0;i<list.size();i++)
				{
					Cart c=list.get(i);
					Orders o=new Orders();
					o.setOrder_status("ordered");
					o.setQuantity(c.getQty());
					Products p=prepo.getByNameAndcategory(c.getProductname(), c.getProductcat());
					if(p!=null)
					{
						o.setPname(p.getName());
						o.setCategory(p.getCategory());
						o.setPcost(p.getPrice());
						o.setTime(java.sql.Date.valueOf(LocalDate.now()));
						o.setCost(c.getQty()*(p.getPrice()));
						List<Orders> l=u.getOrders();
						l.add(o);
						u.setOrders(l);
						if(checkProducts(p.getCount(),c.getQty()))
						{
							
							updateProducts(p,o.getQuantity());
							msg+="The Product Name "+o.getPname()+" in successfully ordered.\n";
							urepo.save(u);
							price+=o.getCost();
							
							
						}
						else
						{
							msg+="The Product with name "+o.getPname()+" is not ordered as the products are not avaliable to your provided Quantity";
							
						}
					}
					else {
						msg+="Product"+c.getProductname()+" is no more avaliable";
						
					}
					crepo.deleteById(c.getId());
					
				}
				if(msg.length()==0)
				{
					es.notifyUser(u.getEmail(), "Some Thing Went wrong while ordering please try again", "Notifying Mail");
					return new Response(true,"Some Thing Went wrong while ordering please try again");
				}
				else
				{
					msg+=" With the Total Cost is : "+price;
					es.notifyUser(u.getEmail(), msg, "Ordered Mail");
					return new Response(false,msg);
				}
			}
			else {
				Response response=new Response(true,"Still you are not released by Admin" );
				return response;
			}
		}
		catch(Exception e)
		{
			Response response=new Response(true,"Something Went wrong try again");
			System.out.println(e);
			return response;
		}
	}

	@Override
	public List<Cart> getCartByUId(long uid) {
		return crepo.findByUserid(uid);
	}

	@Override
	public Response deleteCartElement(long id) {
		try {
			crepo.deleteById(id);
			return new Response(false,"Deleted item from cart");
		}
		catch(Exception e)
		{
			return new Response(true,"Unable to delete item from cart try again!!!!!");
		}
	}

	@Override
	public Response updateCart(Cart c) {
		try {
			Cart c1=crepo.findById(c.getId()).get();
			Products p=prepo.getByNameAndcategory(c.getProductname(), c.getProductcat());
			if(checkProducts(p.getCount(),c.getQty()))
			{
				c1.setQty(c.getQty());
				crepo.save(c1);
				return new Response(false,"Cart Item Updated Successfully");
			}
			else {
				return new Response(true,"Stock is less");
			}
		}
		catch(Exception e)
		{
			return new Response(true,"Something Went Wrong");
		}
	}

	@Override
	public Cart getCartByCartId(long id) {
		return crepo.findById(id).get();
	}

	@Override
	public long getIdByUname(String uname) {
		return urepo.findByUsername(uname).getId();
	}

	
	

	
	

}
