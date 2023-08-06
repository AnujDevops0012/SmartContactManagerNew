package com.scm.Controller;

import java.security.Principal;

import com.scm.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.dao.UserRepository;
import com.scm.entities.User;

@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserRepository userRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{

		String userName=principal.getName();
		System.out.println("USERNAME IS : "+userName);
		User user=userRepository.getUserByUserName(userName);
		System.out.println("USER :"+user);
		model.addAttribute("user",user);
	}
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{

		return "normal/user_dashboard";
	}

	// Open add form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
}
