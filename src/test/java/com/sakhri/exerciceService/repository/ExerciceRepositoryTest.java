package com.sakhri.exerciceService.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.model.Exercice;

@ImportAutoConfiguration(RefreshAutoConfiguration.class) 
@RunWith(SpringRunner.class)
@DataJpaTest
class ExerciceRepositoryTest {

	
	@Autowired
	private ExerciceRepository exerciceRepository;
	
	final String exerciceId1 = UUID.randomUUID().toString();
	final String exerciceId2 = UUID.randomUUID().toString();

	
	@BeforeEach
	public  void initDB() {

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
		
		exerciceRepository.save(ex1);
		exerciceRepository.save(ex2);

	}
	
	@AfterEach
	public void cleanDB() {
		exerciceRepository.deleteAll();
	}
	
	
	@Test
	void whenFindByMuscleThenReturnCorrespondingExercices() {
		// Given

		//When
		final List<Exercice> findByMuscle = exerciceRepository.findByMuscle(Muscle.BICEPS);
		final List<Exercice> findByMuscle2 = exerciceRepository.findByMuscle(Muscle.TRICEPS);
		final List<Exercice> findByMuscle3 = exerciceRepository.findByMuscle(Muscle.CHEAST);

		// Then
		assertEquals(1, findByMuscle.size());
		assertEquals(1, findByMuscle2.size());
		assertEquals(0, findByMuscle3.size());

	}
	
	@Test
	void whenFindByExerciceIDThenReturnCorrespondingExerciceOrNull() {
		// Given
		final String exerciceId3 = UUID.randomUUID().toString();

		//When
		final Exercice findByExerciceId1 = exerciceRepository.findByExerciceId(exerciceId1);
		final Exercice findByExerciceId2 = exerciceRepository.findByExerciceId(exerciceId2);
		final Exercice findByExerciceId3 = exerciceRepository.findByExerciceId(exerciceId3);

		// Then
		
		assertNotNull(findByExerciceId1);
		assertNotNull(findByExerciceId2);
		assertNull(findByExerciceId3);
	}

}
