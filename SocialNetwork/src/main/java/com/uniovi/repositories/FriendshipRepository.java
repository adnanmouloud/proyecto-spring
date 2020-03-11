package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;


@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	@Query("SELECT p FROM Friendship p WHERE p.friend1 = ?1")
	List<Friendship> findAllFriendsOfUser(User currentUser);

	@Query("SELECT p.friend2 FROM Friendship p WHERE p.friend1 = ?1")
	Page<User> getFriendsOfUser(Pageable pageable, User user);
}
