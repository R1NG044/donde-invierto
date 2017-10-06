package edu.utn.frba.dds.grupo5.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="di_user")
@Entity
@NamedQuery(name="search_usuario",query="select u from Usuario u where u.username = :username and u.password = :password")
public class Usuario extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4499280436232143886L;
	
	private String username;
	private String password;
	
	@Column(name="us_name",length=150,nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="us_password",length=150,nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
