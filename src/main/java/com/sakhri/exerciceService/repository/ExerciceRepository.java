package com.sakhri.exerciceService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakhri.exerciceService.enums.Muscle;
import com.sakhri.exerciceService.model.Exercice;

@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long>{

	public List<Exercice> findByMuscle(Muscle muscle);
	public Exercice findByExerciceId(String exerciceID);

}
