package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC 등록
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	// 1. 패스워드는 알아서 체킹해주기 때문에 신경쓸 필요가 없다.
	// 2. 리턴이 잘 되면 자동으로 UserDetails 타입을 세션으로 생성해준다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		} else {
			return new PrincipalDetails(userEntity);
		}
		
	}

}
