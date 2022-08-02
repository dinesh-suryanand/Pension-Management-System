package com.cognizant.process.pension.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PensionDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Date dateOfBirth;
	private String panNumber;
	private String pensiontype;
	private Double pensionAmount;
	
	public PensionDetail(String name, Date dateOfBirth, String panNumber, String pensiontype, Double pensionAmount) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.panNumber = panNumber;
		this.pensiontype = pensiontype;
		this.pensionAmount = pensionAmount;
	}
}
