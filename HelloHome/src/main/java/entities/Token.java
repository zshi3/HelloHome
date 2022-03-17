package entities;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Token {
	private String token;
	private int expirationDate=2;
	public Token(String token) {
		this.token=token;
	}

	public void setTokenString(String token) {
		 this.token=token;
	}
	public void setTokenTime(int time) {
		this.expirationDate=time;
	}
	public String getTokenString() {
		return this.token;
	}
	public int getTokenTime() {
		return this.expirationDate;
	}
	
	

}
