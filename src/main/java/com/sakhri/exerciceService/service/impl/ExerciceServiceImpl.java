package com.sakhri.exerciceService.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

	public static final String EXERCICE_NOT_FOUND = "Exercice not found";
	
	@Autowired
	private ExerciceRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<ExerciceDto> getAllExercices() {

		log.info("Getting all exercice");
		
		List<Exercice> exercies = repo.findAll();
		
		log.info("Retrived exercices {}", exercies);

		final List<ExerciceDto> result = exercies.stream()
												.map(ex -> modelMapper.map(ex, ExerciceDto.class))
												.collect(Collectors.toList());
        return result;
	}

	@Override
	public ExerciceDto getExercice(String exercieID) {
		
		log.info("Getting exercices by exercieID {}", exercieID);

		final Exercice findByExerciceID = repo.findByExerciceId(exercieID);
		
		log.info("Retrived exercice  {}", findByExerciceID);
		
		Optional.ofNullable(findByExerciceID).orElseThrow(
				() -> new IllegalArgumentException(EXERCICE_NOT_FOUND));
		
		final ExerciceDto dto = modelMapper.map(findByExerciceID, ExerciceDto.class);
		
		return dto;
	}

	@Override
	public boolean createExercice(ExerciceDto dto) {
		
		log.info("creating Exercice with {}", dto);
		
		dto.setExerciceId(UUID.randomUUID().toString());

		Exercice exercice = modelMapper.map(dto, Exercice.class);
		
		exercice.setMuscle(dto.getMuscle());
		
		log.info("Saving Exercice to DB {}", exercice);
		
		repo.save(exercice);
		
		log.info("Exercice saved succesfully");
		
		return true;
	}

	@Override
	public boolean updateExercice(ExerciceDto dto){
		
		log.info("updating Exercice with {}", dto);
		
		final Exercice exercice = repo.findByExerciceId(dto.getExerciceId());
		
		log.info("Retrived exercice  {}", exercice);

		Optional.ofNullable(exercice).orElseThrow(
				() -> new IllegalArgumentException(EXERCICE_NOT_FOUND));
		
		exercice.setDescription(dto.getDescription());
		exercice.setName(dto.getName());
		exercice.setMuscle(dto.getMuscle());
		
		log.info("Updating Exercice to DB {}", exercice);

		repo.save(exercice);
		
		log.info("Exercice updated succesfully");
		
		return true;
	}

	@Override
	public boolean deleteExercice(String exerciceID) {
		
		log.info("deleting Exercice with {}", exerciceID);
				
		final Exercice findByExerciceID = repo.findByExerciceId(exerciceID);
		
		log.info("Retrived exercice  {}", findByExerciceID);
		
		Optional.ofNullable(findByExerciceID).orElseThrow(
				() -> new IllegalArgumentException(EXERCICE_NOT_FOUND));
		
		log.info("Deleting Exercice from DB {}");
		
		repo.delete(findByExerciceID);
		
		log.info("Exercice deleted succesfully");
		
		return true;
	}

	@Override
	public List<ExerciceDto> getExerciceByMuscle(Muscle muscle) {
		
		log.info("Getting Exercices by muscle {}", muscle);
		
		List<Exercice> exercies = repo.findByMuscle(muscle);
		
		final List<ExerciceDto> result = exercies.stream()
												.map(ex -> modelMapper.map(ex, ExerciceDto.class))
												.collect(Collectors.toList());
		
		log.info("Retrived exercices {}", exercies);

        return result;
        
	}

	@Override
	public boolean verifyExercice(String exercieID) {
		
		log.info("verifying Exercice by exercieID {}", exercieID);
		
		final Exercice findByExerciceID = repo.findByExerciceId(exercieID);
		
		log.info("Retrived exercice {}", findByExerciceID);

		return findByExerciceID != null ? true : false;
	}

	@Override
	public void deleteAllExercices() {
		
		log.info("Deleting all exercices");

		repo.deleteAll();
	}

}
