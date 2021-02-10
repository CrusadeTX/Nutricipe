package com.project.nutricipe.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;



public class UserPrincipal implements UserDetails {
	private UserBean user;
	private Set<GrantedAuthority> authorities;
	
	public UserPrincipal(UserBean user, Set<RoleBean> roles) {
		this.user=user;
		authorities = new HashSet<GrantedAuthority>();
		insertRoles(roles);
		
	}
	private void insertRoles(Set<RoleBean> roles) {
		for(RoleBean role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		if(authorities.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
	public int getLoggedInUserId() {
		return user.getId();
	}
	public UserBean getLoggedInUser() {
		return user;
	}

} 


