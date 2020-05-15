package com.sakhri.exerciceService.service;

import java.util.List;

import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.enums.Muscle;

public interface ExerciceService {
	
	public List<ExerciceDto> getAllExercices();
		
	public ExerciceDto getExercice(String exercieID);
	
	public boolean verifyExercice(String exercieID);
		
	public List<ExerciceDto>  getExerciceByMuscle(Muscle muscle);
	
	public boolean createExercice(ExerciceDto dto);
	
	public boolean updateExercice(ExerciceDto dto);
	
	public boolean deleteExercice(String exercieID);
	
	public void deleteAllExercices();

}
