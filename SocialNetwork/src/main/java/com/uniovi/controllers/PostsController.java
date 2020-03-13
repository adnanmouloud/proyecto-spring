package com.uniovi.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.NewPostFormValidator;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class PostsController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PostsService postsService;
	
	@Autowired
	private NewPostFormValidator newPostFormValidator;

	@RequestMapping(value = "/post/add")
	public String getMark(Model model) {
		model.addAttribute("post", new Post());
		return "post/add";
	}
	
	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setMark(@Validated Post post, BindingResult result) {
		newPostFormValidator.validate(post, result);
		if (result.hasErrors()) {
			return "post/add";
		}
		post.setUser((User) httpSession.getAttribute("currentlyUser"));
		postsService.addPost(post);
		return "redirect:/post/list";
	}
}
