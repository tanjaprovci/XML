package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.Accommodation;
import com.project.model.Apartment;
import com.project.repository.ApartmentRepository;
import com.project.service.ApartmentService;


@Transactional
@Service
public class JpaApartmentService implements ApartmentService {

	private static ApartmentRepository repository;
	
	public JpaApartmentService(ApartmentRepository repository) {
		if(repository != null)
			JpaApartmentService.repository = repository;
	}
	
	@Override
	public Apartment findOne(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Apartment> findAll() {
		return repository.findAll();
	}

	@Override
	public Apartment save(Apartment apartment) {
		// TODO Auto-generated method stub
		return repository.save(apartment);
	}

	@Override
	public List<Apartment> save(List<Apartment> apartments) {
		// TODO Auto-generated method stub
		return repository.saveAll(apartments);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Apartment> findByAccommodationId(Accommodation accommodation) {
		// TODO Auto-generated method stub
		return repository.findByAccommodationId(accommodation);
	}

}
