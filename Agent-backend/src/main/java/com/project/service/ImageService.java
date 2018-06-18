package com.project.service;

import java.util.List;

import com.project.model.Accommodation;
import com.project.model.Image;

public interface ImageService {
	
	Image findOne(Long id);
	List<Image> findAll();
	Image save(Image image);
	List<Image> save(List<Image> images);
	Image delete(Long id);
	void delete(List<Long> ids);

}
