package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.BedType;

@Repository
public interface BedTypeRepository extends JpaRepository<BedType, Long>{

}
