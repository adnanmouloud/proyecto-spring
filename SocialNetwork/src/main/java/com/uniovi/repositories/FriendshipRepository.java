package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Friendship;


@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

}
