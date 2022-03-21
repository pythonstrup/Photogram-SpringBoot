package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
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

	@Override
	public Map<String, Object> getAttributes() {
		
		return attributes; // {id=153707332428972, name=내이름, email=내아이디@gmail.com}
	}

	@Override
	public String getName() {
		
		return (String) attributes.get("name");
	}

}
