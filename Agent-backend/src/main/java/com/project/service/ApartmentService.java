package com.project.service;

import java.util.List;

import com.project.model.Accommodation;
import com.project.model.Apartment;

public interface ApartmentService {

	Apartment findOne(Long id);
	List<Apartment> findAll();
	Apartment save(Apartment apartment);
	List<Apartment> save(List<Apartment> apartments);
	void delete(Long id);
	void delete(List<Long> ids);
	List<Apartment> findByAccommodationId(Accommodation accommodation);
}
