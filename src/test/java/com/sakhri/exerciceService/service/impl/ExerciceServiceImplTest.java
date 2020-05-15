package com.sakhri.exerciceService.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
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

import com.sakhri.exerciceService.dto.ExerciceDto;
import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.model.Exercice;
import com.sakhri.exerciceService.repository.ExerciceRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ExerciceServiceImplTest {

	
	@Mock
	private ExerciceRepository repo;
	
	@Spy
	private ModelMapper modelMapper;

	@InjectMocks
	private ExerciceServiceImpl exerciceService;
	
	
	final String exerciceId1 = UUID.randomUUID().toString();
	final String exerciceId2 = UUID.randomUUID().toString();

	final Exercice ex1 = Exercice.builder()
			.name("ex1")
			.description("desc1")
			.muscle(Muscle.BICEPS)
			.exerciceId(exerciceId1)
			.build();
	
	final Exercice ex2 = Exercice.builder()
			.name("ex2")
			.description("desc2")
			.muscle(Muscle.TRICEPS)
			.exerciceId(exerciceId2)
			.build();
	
	final List<Exercice> exercices = Arrays.asList(ex1,ex2);
	
	@Test
	void whenGetAllExercicesThenReturnAllExercicesDTO() {
		
		// Given
			
		when(repo.findAll()).thenReturn(exercices);
		
		// When
		
		final List<ExerciceDto> allExercices = exerciceService.getAllExercices();
		
		// Then
		
		assertEquals(allExercices.size(), 2);
		assertEquals(allExercices.get(0).getExerciceId(), exerciceId1);
		assertEquals(allExercices.get(1).getExerciceId(), exerciceId2);	
	}
	
	@Test
	void whenGetExerciceWithValidIDThenReturnCorrespondingExerciceDTO() {
		
		// Given
		
		when(repo.findByExerciceId(exerciceId1)).thenReturn(ex1);
		
		// When
		
		final ExerciceDto exercice = exerciceService.getExercice(exerciceId1);
		
		// Then
		
		assertNotNull(exercice);
		assertEquals(ex1.getName(), exercice.getName());
		assertEquals(ex1.getDescription(), exercice.getDescription());
		assertEquals(ex1.getMuscle(), exercice.getMuscle());

	}
	
	@Test
	void whenGetExerciceWithInValidIDThenThrowIllegalArgumentException() {
		
		// Given
		
		final String exerciceId3 = UUID.randomUUID().toString();
		when(repo.findByExerciceId(exerciceId1)).thenReturn(null);
		
		
		try {
		
		// When

			exerciceService.getExercice(exerciceId3);
			
		} catch (IllegalArgumentException e) {
		
		// Then
			assertEquals(ExerciceServiceImpl.EXERCICE_NOT_FOUND,e.getMessage() );
		}
	}
	
	@Test
	void whenUpdateExistingExerciceThenReturnTrue() {
		
		// Given
		
		ExerciceDto dto = ExerciceDto.builder()
				.exerciceId(exerciceId1)
				.build();
		when(repo.findByExerciceId(exerciceId1)).thenReturn(ex1);
		
		// When
	
		final boolean isUpdated = exerciceService.updateExercice(dto);
		
		// Then
	
		assertTrue(isUpdated);
	}
	
	@Test
	void whenUpdateNonExistingExerciceThenThenThrowIllegalArgumentException() {
		
		// Given
		
		
			final String exerciceId3 = UUID.randomUUID().toString();
			ExerciceDto dto = ExerciceDto.builder()
					.exerciceId(exerciceId3)
					.build();
			when(repo.findByExerciceId(exerciceId3)).thenReturn(null);
			
			try {
				
			// When

				exerciceService.updateExercice(dto);
					
			} catch (IllegalArgumentException e) {
				
			// Then
				assertEquals(ExerciceServiceImpl.EXERCICE_NOT_FOUND,e.getMessage() );
			}
		
	}
	
	@Test
	void whenDeleteExistingExerciceThenReturnTrue() {
		
		// Given
		
		when(repo.findByExerciceId(exerciceId1)).thenReturn(ex1);
		
		// When
	
		final boolean isDeleted = exerciceService.deleteExercice(exerciceId1);
		
		// Then
	
		assertTrue(isDeleted);
	}
	
	@Test
	void whenDeleteeNonExistingExerciceThenThenThrowIllegalArgumentException() {
		
		// Given
		
		
			final String exerciceId3 = UUID.randomUUID().toString();
			when(repo.findByExerciceId(exerciceId3)).thenReturn(null);
			
			try {
				
			// When

				exerciceService.deleteExercice(exerciceId1);
					
			} catch (IllegalArgumentException e) {
				
			// Then
				assertEquals(ExerciceServiceImpl.EXERCICE_NOT_FOUND,e.getMessage() );
			}
		
	}
	
	
	@Test
	void whenGetExerciceByMuscleThenReturnCorrespondingExercicesDTO() {
		
		// Given
		when(repo.findByMuscle(Muscle.BICEPS)).thenReturn(Arrays.asList(ex1));
		when(repo.findByMuscle(Muscle.TRICEPS)).thenReturn(Arrays.asList(ex2));
		when(repo.findByMuscle(Muscle.CHEAST)).thenReturn(Collections.emptyList());

		
		// When
		final List<ExerciceDto> exerciceByBicepsMuscle = exerciceService.getExerciceByMuscle(Muscle.BICEPS);
		final List<ExerciceDto> exerciceByTricepsMuscle = exerciceService.getExerciceByMuscle(Muscle.TRICEPS);
		final List<ExerciceDto> exerciceByCheastMuscle = exerciceService.getExerciceByMuscle(Muscle.CHEAST);

		// Then
		assertEquals(exerciceByBicepsMuscle.size(), 1);
		assertEquals(exerciceByBicepsMuscle.get(0).getExerciceId(), exerciceId1);

		assertEquals(exerciceByTricepsMuscle.size(), 1);
		assertEquals(exerciceByTricepsMuscle.get(0).getExerciceId(), exerciceId2);

		assertEquals(exerciceByCheastMuscle.size(), 0);
	}
	
	@Test
	void whenVerifyExistingExerciceThenReturnTrue() {
		
		// Given
		
		when(repo.findByExerciceId(exerciceId1)).thenReturn(ex1);
		
		// When
	
		final boolean isPresent = exerciceService.verifyExercice(exerciceId1);
		
		// Then
	
		assertTrue(isPresent);
	}
	@Test
	void whenVerifyNonExistingExerciceThenReturnFalse() {
		
		// Given
		
		when(repo.findByExerciceId(exerciceId1)).thenReturn(null);
		
		// When
	
		final boolean isPresent = exerciceService.verifyExercice(exerciceId1);
		
		// Then
	
		assertFalse(isPresent);
	}

}
