package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 마리아DB에서 like가 키워드이기 때문에 like로 테이블을 만들 수가 없다.
// 그래서 likes로 클래스 네임을 정함.
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
@Table( // 필요할 때마다 문서보고 적기
		uniqueConstraints = {
				@UniqueConstraint(
						name="likes_uk",
						columnNames = {"imageId", "userId"}
				)
		}
)
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	// 무한 참조됨 (Likes -> Image -> Likes)
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image; // 1(image) 대 N(likes): 이미지 클래스에서 json ignore를 걸어놨음.
	
	// 무한 참조됨 (Likes -> User -> images(List) -> Likes): @JsonIgnoreProperties를 사용하자
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user; // 1(user) 대 N(likes)
	
	private LocalDateTime createDate; 
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
