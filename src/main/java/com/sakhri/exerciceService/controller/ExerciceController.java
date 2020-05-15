package com.sakhri.exerciceService.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sakhri.exerciceService.dto.CreateExerciceRequest;
import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.dto.UpdateExerciceRequest;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.service.ExerciceService;

import lombok.extern.log4j.Log4j2;


@RefreshScope
@Log4j2
@RestController()
@RequestMapping("exercices")
public class ExerciceController {
	
	@Autowired
	private ExerciceService exerciceService; 
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Value("${gateway.ip.adress}")
	private String gatewayIpAdress;
	
	@GetMapping("/check")
	public ResponseEntity<String> check() {
       
		return ResponseEntity.ok(gatewayIpAdress);
	}
	
	@PostMapping
	public ResponseEntity<Boolean> creatExercice(@Valid  @RequestBody(required = true)  
				CreateExerciceRequest exercice) {
		
		log.info("Creating new Exercice with data {}", exercice);
		
		ExerciceDto dto = modelMapper.map(exercice, ExerciceDto.class);

		boolean isCreated = exerciceService.createExercice(dto);
		
		log.info("Exercice created correctly : {} ", isCreated);

		return ResponseEntity.status(HttpStatus.CREATED).body(isCreated);
	}
	
	@PutMapping
	public ResponseEntity<Boolean> updateExercice(@Valid  @RequestBody(required = true)
				UpdateExerciceRequest exercice) {
		
		log.info("Updating Exercice with data {}", exercice);

		ExerciceDto dto = modelMapper.map(exercice, ExerciceDto.class);

		boolean isUpdated = exerciceService.updateExercice(dto);
		
		log.info("Exercice updated correctly : {} ", isUpdated);

		return ResponseEntity.status(HttpStatus.OK).body(isUpdated);
	}
	
	@GetMapping
	public ResponseEntity<List<ExerciceDto>> getAllExercice() {
		
		log.info("Getting all exercices");

		final List<ExerciceDto> allExercices = exerciceService.getAllExercices();
		
		log.info("All Exercices retreived {}", allExercices);

		return ResponseEntity.ok(allExercices);
	}
	
	@GetMapping(params = {"muscle"})
	public ResponseEntity<List<ExerciceDto>> getExercicesByMuscle(@RequestParam(
			name= "muscle") Muscle muscle) {
        
		log.info("Getting Exercices By Muscle {}", muscle);
        
		final List<ExerciceDto> exerciceByMuscle = exerciceService.getExerciceByMuscle(muscle);
		
		log.info("Exercices By muscle retreived {}", exerciceByMuscle);

		return ResponseEntity.ok(exerciceByMuscle);
	}
	
	@GetMapping("/{exerciceId}")
	public ResponseEntity<ExerciceDto> getExercice(@PathVariable String exerciceId) {
		
        log.info("Getting Exercices By exerciceId {}", exerciceId);
        
        return ResponseEntity.ok(exerciceService.getExercice(exerciceId));
	}
	
	@DeleteMapping("/{exerciceId}")
	public ResponseEntity<Boolean> deleteExercice(@PathVariable String exerciceId) {
		
        log.info("Deleting Exercices By exerciceId {}", exerciceId);
        
        final boolean isDeleted = exerciceService.deleteExercice(exerciceId);
        
		log.info("Exercice deleted correctly : {} ", isDeleted);
        
		return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
	}
	
	@GetMapping("verify/{exerciceId}")
	public ResponseEntity<Boolean> verifyExercice(@PathVariable String exerciceId) {
		
        log.info("Verifying Exercices By exerciceId {}", exerciceId);
        
        final boolean isPresent = exerciceService.verifyExercice(exerciceId);
        
		log.info("Exercice is Verifyed : {} ", isPresent);
        
		return ResponseEntity.ok(isPresent);
	}
	
}
