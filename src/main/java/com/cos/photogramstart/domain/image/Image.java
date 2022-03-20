package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	private String caption; //
	
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 - DB에 그 저장된 경로를 insert
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;  // 1명의 유저는 여러개의 이미지를 등록할 수 있다. (엔티티와 N:1관계)
	
	// 이미지 좋아요
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Likes> likes;
	
	@Transient  // DB에 칼럼이 만들어지지 않는다.
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	// 댓글 - 양방향 매핑
	@OrderBy("id DESC") // order by 걸기(내림차순으로)
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image") // 연관관계의 주인은 image, 디폴트는 lazy 전략
	private List<Comment> comments;
	
	private LocalDateTime createDate;
	
	@PrePersist // DB에 Insert가 진행되기 직전에 실행하는 어노테이션
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	// 무한참조 방지를 위해 user를 빼고 toString을 구성함.
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl + ", createDate="
//				+ createDate + "]";
//	}
}
