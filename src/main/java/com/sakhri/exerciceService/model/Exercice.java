package com.sakhri.exerciceService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sakhri.exerciceService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Exercice {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long id;
	
	private String name;
	
	private String description;
	
	private Muscle muscle;
	
	private String link;
}
