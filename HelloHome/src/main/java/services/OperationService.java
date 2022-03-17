package services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import java.lang.runtime.*;

import entities.Token;
import entities.User;

public class OperationService {
	//private static final Logger logger = LoggerFactory.getLogger(OperationService.class);
	// Used to store user and its password
	public Map<String, String> userInfo=new HashMap<String, String>();
	// Used to store role information
	public Set<String> roleInfo=new HashSet<String>();
	// Used to store all token information
	public Set<Token> tokenSet = new HashSet<Token>();
	// Used to store user and its roles
	public Map<String, Set<String>> userMap=new HashMap<String,Set<String>>();
	// Used to store user and its token
	public Map<String,Token> userToken = new HashMap<String,Token>();
	
	public void createUser(String username, String password) {
		if(userInfo.containsKey(username)) {
			throw new RuntimeException("Creation failed for user "+username+ " since user already exists");
		}
		else {
			userInfo.put(username, encrypt(password));
			Set<String> roleSet= new HashSet<>();
			userMap.put(username,roleSet);
		}
	}
	public void deleteUser(String username) {
		if(userInfo.containsKey(username)) {
			userInfo.remove(username);
		}
		else {
			throw new RuntimeException("Deletion failed for user "+username+ " since user is not exist");
		}
	}
	public void createRole(String roleName) {
		if(roleInfo.contains(roleName)) {
			throw new RuntimeException("Creation failed for role "+roleName+ " since role already exists");
		}
		else {
			roleInfo.add(roleName);
		}
	}
	public void deleteRole(String roleName) {
		if(roleInfo.contains(roleName)) {
			roleInfo.remove(roleName);
		}
		else {
			throw new RuntimeException("Deletion failed for role "+roleName+ " since role is not exist");
		}
	}
	public void addRole(String roleName, String userName) {
		if(userMap.containsKey(userName)) {
			if (!userMap.get(userName).contains(roleName)) {
				Set<String> roleSet = new HashSet<>(userMap.get(userName));
				roleSet.add(roleName);
				userMap.put(userName, roleSet);
			}
		}
	}
	
	public boolean checkRole(String userName, String roleName,Token token) {
		if(isValidUser(userName, token)) {
			if(userMap.get(userName).contains(roleName)) {
				return true;
			}
			else
				return false;
		}
		else {
			throw new RuntimeException("Invalid token "+token.getTokenString()+" for user "+userName);
		}
		
	}
	
	public boolean isValidUser(String userName,Token token) {
		if(!userToken.containsKey(userName))
			return false;
		if(userToken.get(userName).getTokenString().equals(token.getTokenString())) {
			if((userToken.get(userName).getTokenTime()<=token.getTokenTime()))
				return true;
			else
			{
				return false;
			}
		}
		else {
			return false;
		}
		
	}
	
	public Set<String> getAllRoles(String userName, Token token) {
		Set<String> res = new HashSet<String>();
		if(isValidUser(userName, token)) {
			res=userMap.get(userName);
			return res;
		}
		else {
			throw new RuntimeException("Invalid token "+token.getTokenString()+" for user "+userName);
		}		
	}

	public String encrypt(String str) {
		    return Base64.encodeBase64String(str.getBytes());
		}
	
	public void authenticate(String username, String password) {
		String s=encrypt(username+password);
		Token token = new Token(s);
		userToken.put(username, token);	
		tokenSet.add(token);
	}
	
	public void invalidate(Token token) 
	{
		token.setTokenTime(0);

	}
	public boolean isValid(Token token) {
		if(tokenSet.contains(token)) {
			if(token.getTokenTime()>2) {
				return true;
			}
			else {
				return false;
			}
				
		}
		else {
			return false;
		}
	}

}
