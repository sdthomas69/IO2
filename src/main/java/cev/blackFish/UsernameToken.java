package cev.blackFish;

import org.apache.shiro.authc.AuthenticationToken;

public class UsernameToken implements AuthenticationToken {

	private String username;

	public UsernameToken(String username) {
		this.username = username;
	}

	public String getPrincipal() {
		return username;
	}

	public String getCredentials() {
		return username;
	}
}
