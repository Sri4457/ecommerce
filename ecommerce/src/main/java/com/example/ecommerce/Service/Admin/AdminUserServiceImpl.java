package com.example.ecommerce.Service.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Repository.UserRepo;
import com.example.ecommerce.Service.EmailService;

@Service
public class AdminUserServiceImpl implements AdminUserInterface{

	@Autowired
	UserRepo urepo;
	
	@Autowired
	EmailService es;
	
	@Override
	public boolean addUser(Users u) {
		boolean b=false;
		try {
			urepo.save(u);
			String message="Thank you for choosing our ecommerce website. Thanks for Registering";
			es.notifyUser(u.getEmail(), message);
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

}
