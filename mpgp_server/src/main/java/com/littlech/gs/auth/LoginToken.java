/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.auth;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginToken extends User {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Time interval in milliseconds to expire login tokens
	 */
	private static final long EXPIRE_TIME = 93312000000L; // 3y // 1500000; // 15'
	/*
			1000 // 1''
			* 60 // 1'
			* 60 // 1h
			* 24 // 1d
			* 360 // 1y
			* 3 // 3y
			; 
	*/
	
	private long id = -1; // place holder
	private Date createDate;
	private String avatar;

	public LoginToken(String username, String password, Collection<? extends GrantedAuthority> authorities, Date _createDate, String _avatar) {
		super(username, password, true, true, true, true, authorities);
		setCreateDate(_createDate);
		setAvatar(_avatar);
	}
	
	

	public String getAvatar() {
		return avatar;
	}



	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public long getId() {
		if (id < 0) {
			throw new IllegalStateException("Id must be set!");
		}
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (rhs == null || !(rhs instanceof LoginToken)) {
			throw new IllegalArgumentException("Argument is " + rhs);
		}
		LoginToken comp = (LoginToken)rhs;
		return this.getId() == comp.getId();
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		boolean ret = !this.isExpired() && super.isCredentialsNonExpired();
		return ret;
	}

	private boolean isExpired() {
		Date curTime = new Date();
		long diff = curTime.getTime() - getCreateDate().getTime();
		if (diff > LoginToken.EXPIRE_TIME) {
			return true;
		}
		return false;
	}

}
