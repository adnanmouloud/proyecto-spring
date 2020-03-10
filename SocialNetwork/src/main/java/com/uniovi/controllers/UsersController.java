package com.uniovi.controllers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.InvitationFriendshipService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private InvitationFriendshipService invitationFriendshipService;
	
	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {

		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByNameOrSurnameorEmail(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}

		User currentlyUser = (User) httpSession.getAttribute("currentlyUser");

		// usuarios que me han enviado una solicitud de amistad
		Set<User> usersReceivedRequestFriendship = invitationFriendshipService.getUserSendedRequest(currentlyUser,
				users.getContent());

		// usuarios a los que he enviado una solicitud de amistad
		Set<User> usersRequestedFriendship = usersService.getUsersReceivedFriendshipFromCurrentlyUser(currentlyUser,
				users.getContent());

		// amigos
		// Set<User> friendsInPage =
		// invitationFriendshipService.getFriends(currentlyUser, users.getContent()) ;

		Set<User> todasPendientes = new HashSet<User>();

		usersReceivedRequestFriendship.forEach(todasPendientes::add);
		usersRequestedFriendship.forEach(todasPendientes::add);

		// model.addAttribute("friendList", friendsInPage);
		model.addAttribute("pendientes", todasPendientes);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);

		model.addAttribute("invitationsList", invitationFriendshipService.getUsersWhoReceivedFriendshipInvitationFromCurrentUser(currentlyUser));
		model.addAttribute("friendsList", friendshipService.getFriendsOfCurrentUser(currentlyUser));

		return "user/list";

	}

	@RequestMapping("/user/add")
	public String getUser(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());
		// model.addAttribute("usersList", usersService.getUsers());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		User user = usersService.getUser(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
//		user.setId(id);
//		usersService.addUser(user);
//		return "redirect:/user/details/" + id;

		User antiguo = usersService.getUser(id);
		// antiguo.setDni(user.dni);
		antiguo.setName(user.getName());
		antiguo.setLastName(user.getLastName());
		usersService.addUser(antiguo);
		return "redirect:/user/details/" + id;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		httpSession.setAttribute("currentlyUser", activeUser);

		if (activeUser.getRole().equals(rolesService.getRoles()[1]))
			return "redirect:/user/list";
		
		return "home";
	}

	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable) {
		model.addAttribute("usersList", usersService.getUsers(pageable));
		model.addAttribute("invitationsList",
				invitationFriendshipService.getUsersWhoReceivedFriendshipInvitationFromCurrentUser((User) httpSession.getAttribute("currentlyUser")));
		return "user/list :: tableUsers";
	}
}
