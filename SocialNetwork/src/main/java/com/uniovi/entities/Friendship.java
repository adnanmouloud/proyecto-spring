package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Friendship {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User friend1;
	
	@ManyToOne
	private User friend2;
	
	public Friendship(User friend1, User friend2) {
		super();
		this.friend1 = friend1;
		this.friend2 = friend2;
	}

	public Friendship() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getFriend1() {
		return friend1;
	}

	public void setFriend1(User friend1) {
		this.friend1 = friend1;
	}

	public User getFriend2() {
		return friend2;
	}

	public void setFriend2(User friend2) {
		this.friend2 = friend2;
	}
}
