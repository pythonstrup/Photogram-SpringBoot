package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

// 페이스북 로그인을 할 때, 페이스북 창이 뜬다. 페이스북 창을 통해 유저가 요청하면 페이스북 서버에서 응답을 날린다.
// 그 응답을 처리하는 것이 OAuth2DetailsService 클래스다.
// 회원정보를 받아 바로 처리.
@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{

	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// System.out.println("OAuth2 서비스");
		OAuth2User oauth2User = super.loadUser(userRequest);
		// System.out.println(oauth2User.getAttributes());
		
		Map<String, Object> userInfo = oauth2User.getAttributes();
		String username = "facebook_" + (String) userInfo.get("id");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { // 페이스북 최초 로그인
			User user = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
					
			return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());
			
		} else { // 페이스북으로 이미 회원가입 되어 있음.
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}
		
	}
}
