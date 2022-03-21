package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Java Persistence API (자바로 데이터를 영구적으로 저장할 수 있는 API를 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // DB에 테이블을 생성
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
	private int id;
	
	@Column(length=100, unique=true) // OAuth2 로그인을 위해 칼럼 늘리기
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String name;
	
	private String website;
	
	private String bio;  // 자기소개
	
	@Column(nullable=false)
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String profileImageUrl;
	
	private String role; // 권한 
	
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마라.
	// User를 Select할 때 해당 User id로 등록된 image들을 다 가져와.
	// Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마. - 대신 getImages() 함수가 호출되면 가지고 와.
	// Eager = User를 Select할 때 해당 User id로 등록된 image들을 모두 가져와.
	// Default는 Lazy이다.
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) 
	@JsonIgnoreProperties({"user"})  // Json으로 파싱할 때 무한 참조 방지
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	@PrePersist // DB에 Insert가 진행되기 직전에 실행하는 어노테이션
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate=" + createDate + "]";
	}
	
	
}
