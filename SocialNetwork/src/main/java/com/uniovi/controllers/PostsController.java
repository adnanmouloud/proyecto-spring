package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.NewPostFormValidator;

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

	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping("/post/list")
	public String getList(Model model, Principal principal) {
		User currentUser = usersService.getUserByEmail(principal.getName());
		List<Post> listaPosts = postsService.findPostsForUser(currentUser);
		model.addAttribute("postsList", listaPosts);
		return "post/list";
	}

	@RequestMapping("/post/listFriend/{id}")
	public String getListFriend(@PathVariable Long id, Model model, Principal principal) {
		List<User> friendsOfCurrentUser = friendshipService
				.getFriendsOfCurrentUser((User) httpSession.getAttribute("currentlyUser"));
		boolean forbidden = true;
		for (User f : friendsOfCurrentUser)
			if (f.getId() == id)
				forbidden = false;
		if (forbidden)
			return "forbidden";

		User friend = usersService.getUser(id);
		List<Post> listaPosts = postsService.findPostsForUser(friend);
		model.addAttribute("friend", friend);
		model.addAttribute("postsList", listaPosts);
		return "post/listFriend";
	}

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
