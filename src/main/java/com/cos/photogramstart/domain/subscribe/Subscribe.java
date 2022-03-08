package com.cos.photogramstart.domain.subscribe;

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

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity 
@Table( // 필요할 때마다 문서보고 적기
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames = {"fromUserId", "toUserId"}
				)
		}
)
public class Subscribe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@JoinColumn(name="fromUserId") // 이렇게 컬럼명 만들어라.
	@ManyToOne
	private User fromUser;
	
	@JoinColumn(name="toUserId") // 이렇게 컬럼명 만들어라.
	@ManyToOne
	private User toUser;
	
	private LocalDateTime createDate;
	
	@PrePersist // DB에 Insert가 진행되기 직전에 실행하는 어노테이션
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}