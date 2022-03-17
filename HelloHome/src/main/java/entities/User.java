/**
 * 
 */
package entities;

import java.util.HashSet;
import java.util.Set;


public class User {
	private String userName;
	private Token token;
	public Set<String> roles= new HashSet<String>();
	public User(String name) {
		this.userName = name;
	}
	public void setUser(String name, Token token) {
		this.userName = name;
		this.token = token;
	}
	public void setToken(String name, Token token) {
		this.userName=name;
		this.token=token;	
	}
	public Token getUserToken() {
		return this.token;
	}
	public String getUserName() {
		return this.userName;
	}
	public boolean addRole(String role) {
		return roles.add(role);
	}
	public boolean removeRole(String role) {
		return roles.remove(role);
	}

}
