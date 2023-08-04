package com.scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.dao.UserRepository;
import com.scm.entities.User;
import com.scm.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController
{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
    public String home(Model model)
    {
		model.addAttribute("title","Home - Smart Contact Manager ");
   	 return "home";
    }
	
	@RequestMapping("/about")
    public String about(Model model)
    {
		model.addAttribute("title","About - Smart Contact Manager ");
   	 return "about";
    }
	//handler for login
	@GetMapping("/signin")
    public String custoLogin(Model model)
    {
	  model.addAttribute("title","Login - Smart Contact Manager ");
   	 return "login";
    }
	
	@RequestMapping("/signup")
    public String signup(Model model)
    {
		model.addAttribute("title","Registration - Smart Contact Manager ");
		model.addAttribute("user",new User());
   	 return "signup"; 
    }
	//handler for register
	@RequestMapping( value= "/do_register",method= RequestMethod.POST)
	public String registeruser( @Valid  @ModelAttribute("user") User user,BindingResult result1  ,@RequestParam(value="agreement",defaultValue = "false") boolean agreement,Model model,HttpSession session)
	{
		
		try {
			
			if(!agreement)
			{
				System.out.println("You Have Not Agreed Terms & Conditions");
				 throw new  Exception("You Have Not Agreed Terms & Conditions");
			}
			
			if(result1.hasErrors())
			{
				System.out.println("ERROR"+result1.toString());
				model.addAttribute("user",user);
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setActive(true);
			System.out.println("Agreement"+agreement);
			System.out.println("User "+user);
			
		    User result	= this.userRepository.save(user);
		    model.addAttribute("user",new User());
			session.setAttribute("message", new Message("Successfully Register !!","alert-success"));
			return "signup";
		//	model.addAttribute("user",new Message("Successfully Registered !!","alert-success"));
		//	return "signup";
			
		} catch (Exception e) 
		{	
			e.printStackTrace();
		//	model.addAttribute("user",user);
			session.setAttribute("message",new Message("Something went Wrong !!"+ e.getMessage(),"alert-danger"));
		return "signup";
		}
	}
	
}
