package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Accommodation;
import com.project.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
