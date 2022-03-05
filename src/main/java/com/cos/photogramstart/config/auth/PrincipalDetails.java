package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	// 권한을 가져오는 함수
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> {return user.getRole();});
		
		return collector;
	}

	// 패스워드를 가져옴
	@Override
	public String getPassword() {
		
		return user.getPassword();
	}
	// 유저네임을 가져옴
	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

	// 계정이 만료되었는지 여부
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	// 계정이 잠겨있는지 여부
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	// 비밀번호가 만료되었는지 여부
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	// 계정 활성화 여부
	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
