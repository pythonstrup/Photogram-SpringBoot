package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentDto {

	// @NotBlank - 빈값이거나 null 체크,  빈공백까지 체크
	// @NotEmpty - 빈값이거나 null 체크
	// @NotNull - null인지 체크
	
	@NotBlank 
	private String content;
	
	// int형은 notnull 만 가능
	@NotNull
	private int imageId;
	
	// toEntity가 필요없다.
}
