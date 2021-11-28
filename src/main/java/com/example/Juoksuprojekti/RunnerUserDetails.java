package com.example.Juoksuprojekti;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Juoksuprojekti.domain.User;

public class RunnerUserDetails implements UserDetails {
	private User user;

	public RunnerUserDetails(User user) {
		this.user = user;
	}
//
//	 @Override
//	    public Collection<? extends GrantedAuthority> getAuthorities() {
//	        Set<Role> roles = user.getRoles();
//	        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//	         
//	        for (Role role : roles) {
//	            authorities.add(new SimpleGrantedAuthority(role.getName()));
//	        }
//	        return authorities;
//	    }
//	 
//	     
//	    public boolean hasRole(String roleName) {
//	        return this.user.hasRole(roleName);
//	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
