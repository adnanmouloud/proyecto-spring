package com.uniovi.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class FriendshipService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;

	public void createFriendship(User friend1, Long idNewFriend) {
		User friend2 = usersRepository.findById(idNewFriend).get();
		Friendship friendship = new Friendship(friend1, friend2);
		
		friendshipRepository.save(friendship);
	}

	public void createFriendship(Long idNewFriend, User friend1) {
		User friend2 = usersRepository.findById(idNewFriend).get();
		Friendship friendship = new Friendship(friend2, friend1);
		
		friendshipRepository.save(friendship);
	}

	public Object getFriendsOfCurrentUser(User currentUser) {
		List<User> usersList = new LinkedList<User>();

		for (Friendship i : friendshipRepository.findAllFriendsOfUser(currentUser))
			usersList.add(i.getFriend2());

		return usersList;
	}
}
