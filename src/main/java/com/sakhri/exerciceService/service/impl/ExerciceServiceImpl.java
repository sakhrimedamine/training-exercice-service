package com.sakhri.exerciceService.service.impl;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.model.Exercice;
import com.sakhri.exerciceService.repository.ExerciceRepository;
import com.sakhri.exerciceService.service.ExerciceService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ExerciceServiceImpl implements ExerciceService {

	private static final String EXERCICE_NOT_FOUND = "Exercice not found";
	
	@Autowired
	private ExerciceRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<Exercice> getAllExercices() {
        return repo.findAll();
	}

	@Override
	public Exercice getExercice(Long id) {
		return repo.findById(id).orElseThrow(() -> 
						new IllegalArgumentException(EXERCICE_NOT_FOUND));
	}

	@Override
	public boolean createExercice(ExerciceDto dto) throws IOException {
		log.info("creating Exercice with {}", dto);
		Exercice exercice = modelMapper.map(dto, Exercice.class);
		exercice.setMuscle(dto.getMuscle());
		repo.save(exercice);
		return true;
	}

	@Override
	public boolean updateExercice(ExerciceDto dto) throws IOException{
		log.info("updating Exercice with {}", dto);
		repo.findById(dto.getId()).orElseThrow(
				() -> new IllegalArgumentException(EXERCICE_NOT_FOUND));
		Exercice exercice = modelMapper.map(dto, Exercice.class);
		exercice.setMuscle(dto.getMuscle());
		repo.save(exercice);
		return true;
	}

	@Override
	public boolean deleteExercice(Long id) {
		log.info("deleting Exercice with {}", id);
		Exercice exercice = repo.findById(id).orElseThrow(
				() -> new IllegalArgumentException(EXERCICE_NOT_FOUND));
		repo.delete(exercice);
		return true;
	}

	@Override
	public List<Exercice> getExerciceByMuscle(Muscle muscle) {
		return repo.findByMuscle(muscle);
	}

	@Override
	public boolean verifyExercice(Long id) {
		return repo.findById(id).isPresent() ? true : false;
	}

}
