package com.sakhri.exerciceService.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.sakhri.exerciceService.dto.CreateExerciceRequest;
import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.dto.UpdateExerciceRequest;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.service.ExerciceService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ExerciceControllerTest {

	@Mock
	private ExerciceService exerciceService; 
	
	@Spy
	private ModelMapper modelMapper;

	@InjectMocks
	private ExerciceController controller;

	final String exerciceId1 = UUID.randomUUID().toString();
	final String exerciceId2 = UUID.randomUUID().toString();

	final ExerciceDto ex1 = ExerciceDto.builder()
			.name("ex1")
			.description("desc1")
			.muscle(Muscle.BICEPS)
			.exerciceId(exerciceId1)
			.build();
	
	final ExerciceDto ex2 = ExerciceDto.builder()
			.name("ex2")
			.description("desc2")
			.muscle(Muscle.TRICEPS)
			.exerciceId(exerciceId2)
			.build();
	
	final List<ExerciceDto> exercices = Arrays.asList(ex1,ex2);

	@Test
	void whenCreateExerciceThenReturnTrue() {
		
		// Given
		
		CreateExerciceRequest ex = CreateExerciceRequest.builder()
										.name("ex1")
										.description("desc1")
										.muscle(Muscle.BICEPS)
										.build();
		when(exerciceService.createExercice(any(ExerciceDto.class))).thenReturn(true);
		// When
		
		final ResponseEntity<Boolean> response = controller.creatExercice(ex);
				
		// Then
		assertTrue(response.getBody());
	}
	
	@Test
	void whenUpdateExerciceThenReturnTrue() {
		
		// Given
		
		UpdateExerciceRequest ex = UpdateExerciceRequest.builder()
										.exerciceId("exerciceId1")
										.name("ex1")
										.description("desc1")
										.muscle(Muscle.BICEPS)
										.build();
		when(exerciceService.updateExercice(any(ExerciceDto.class))).thenReturn(true);
		// When
		
		final ResponseEntity<Boolean> response = controller.updateExercice(ex);
				
		// Then
		assertTrue(response.getBody());
	}
	
	@Test
	void whenGetAllExerciceReturnListOfExerciceDTO() {
		
		// Given
		
		when(exerciceService.getAllExercices()).thenReturn(exercices);
		
		// When
		
		 final ResponseEntity<List<ExerciceDto>> response = controller.getAllExercice();
				
		// Then
		assertEquals(2, response.getBody().size());
	}

}
