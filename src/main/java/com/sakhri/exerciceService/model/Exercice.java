package com.sakhri.exerciceService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sakhri.exerciceService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Exercice {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String exerciceId;

	@Column(nullable=false, length=50)
	private String name;
	
	private String description;
	
	@Column(nullable=false, length=50)
	private Muscle muscle;
	
}
