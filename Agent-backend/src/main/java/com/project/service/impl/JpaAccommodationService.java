package com.project.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.project.model.Accommodation;
import com.project.repository.AccommodationRepository;
import com.project.service.AccommodationService;

@Transactional
@Service
public class JpaAccommodationService implements AccommodationService {

	private static AccommodationRepository repository;
	
	public JpaAccommodationService(AccommodationRepository repository) {
		if(repository != null)
			JpaAccommodationService.repository = repository;
	}
	
	@Override
	public Accommodation findOne(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Accommodation> findAll() {
		return repository.findAll();
	}

	@Override
	public Accommodation save(Accommodation accommodation) {
		// TODO Auto-generated method stub
		return repository.save(accommodation);
	}

	@Override
	public List<Accommodation> save(List<Accommodation> accommodations) {
		// TODO Auto-generated method stub
		return repository.saveAll(accommodations);
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
}
