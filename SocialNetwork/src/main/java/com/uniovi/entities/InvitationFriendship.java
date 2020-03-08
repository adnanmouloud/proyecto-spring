package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class InvitationFriendship {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User emisor;
	
	@ManyToOne
	private User receptor;
	
	private boolean aceptada;
	
	
	
	public InvitationFriendship(User emisor, User receptor) {
		super();
		this.emisor = emisor;
		this.receptor = receptor;
		this.aceptada = false;
	}

	public InvitationFriendship() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getEmisor() {
		return emisor;
	}

	public void setEmisor(User emisor) {
		this.emisor = emisor;
	}

	public User getReceptor() {
		return receptor;
	}

	public void setReceptor(User receptor) {
		this.receptor = receptor;
	}

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}
	
	
	
	
}
