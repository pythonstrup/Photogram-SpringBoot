package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID(); // uuid: 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지 파일이름: " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신, I/O 가 일어날 때 예외가 발생할 수 있다. -> 컴파일 시에는 못잡아낸다. 오직 런타임에만 캐치할 수 있는 녀석들
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); 
		imageRepository.save(image);
		
//		System.out.println(imageEntity);
	}
	
	@Transactional(readOnly = true) // readOnly란? 영속성 컨텍스트 변경 감지를 해서 더티체킹 및 flush(반영) X - 성능 조금 좋아짐.
	public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
		Page<Image> images = imageRepository.mStroy(principalId, pageable);
		
		// 2(cos) 로그인
		// images에 좋아요 상태 담기
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like)->{
				if (like.getUser().getId() == principalId) { // 해당 이미지에 좋아효한 사람들을 찾아서 현재 로그인한 사람이 좋아요를 한 것인지 비교
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
}
