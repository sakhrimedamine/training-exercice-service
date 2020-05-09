package com.sakhri.exerciceService.service;

import java.io.IOException;
import java.util.List;

import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.model.Exercice;

public interface ExerciceService {
	
	public List<Exercice> getAllExercices();
		
	public Exercice getExercice(Long id);
	
	public boolean verifyExercice(Long id);
		
	public List<Exercice>  getExerciceByMuscle(Muscle muscle);
	
	public boolean createExercice(ExerciceDto dto) throws IOException;
	
	public boolean updateExercice(ExerciceDto dto) throws IOException;
	
	public boolean deleteExercice(Long id);

}
