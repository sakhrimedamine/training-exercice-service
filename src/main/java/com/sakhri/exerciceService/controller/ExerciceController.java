package com.sakhri.exerciceService.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sakhri.exerciceService.dto.ApiResponseDto;
import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.model.Exercice;
import com.sakhri.exerciceService.service.ExerciceService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController()
@RequestMapping("exercices")
public class ExerciceController {
	
	@Autowired
	private ExerciceService exerciceService; 
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<ApiResponseDto> creatExercice(ExerciceDto dto,
							WebRequest request) throws IOException{
		exerciceService.createExercice(dto);
		return ResponseEntity.ok(ApiResponseDto.builder()
				.message("Exercice successfully created")
				.timestamp(LocalDateTime.now())
				.details(request.getDescription(false))
				.build());
	}
	
	@PutMapping
	public ResponseEntity<ApiResponseDto> updateExercice(ExerciceDto dto, 
						WebRequest request) throws IOException {
		exerciceService.updateExercice(dto);
		return ResponseEntity.ok(ApiResponseDto.builder()
				.message("Exercice successfully updated")
				.timestamp(LocalDateTime.now())
				.details(request.getDescription(false))
				.build());
	}
	
	@GetMapping
	public ResponseEntity<List<ExerciceDto>> getAllExercice() {
       
		return ResponseEntity.ok(
				exerciceService.getAllExercices()
								.stream()
								.map(ex -> modelMapper.map(ex, ExerciceDto.class))
								.collect(Collectors.toList()));
	}
	
	@GetMapping(params = {"muscle"})
	public ResponseEntity<List<Exercice>> getExercicesByMuscle(@RequestParam(name= "muscle") Muscle muscle) {
        log.info("Getting Exercices By Muscle {}", muscle);
		return ResponseEntity.ok(exerciceService.getExerciceByMuscle(muscle));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Exercice> getExercice(@PathVariable Long id) {
        log.info("Getting Exercices By id {}", id);
        return ResponseEntity.ok(exerciceService.getExercice(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDto> deleteExercice(@PathVariable Long id, WebRequest request) {
        log.info("Deleting Exercices By id {}", id);
        exerciceService.deleteExercice(id);
    	return ResponseEntity.ok(ApiResponseDto.builder()
				.message("Exercice successfully deleted")
				.timestamp(LocalDateTime.now())
				.details(request.getDescription(false))
				.build());
	}
	
	@GetMapping("verify/{id}")
	public ResponseEntity<Boolean> verifyExercice(@PathVariable Long id) {
        log.info("Verifying Exercices By id {}", id);
        return ResponseEntity.ok(exerciceService.verifyExercice(id));
	}
	
}
