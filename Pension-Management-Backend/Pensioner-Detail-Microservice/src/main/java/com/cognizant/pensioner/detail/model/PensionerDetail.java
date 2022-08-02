package com.cognizant.pensioner.detail.model;

import java.sql.Date;

import javax.persistence.Embedded;
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
@AllArgsConstructor
@NoArgsConstructor
public class PensionerDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String aadhaarNumber;
	private String name;
	private Date dateOfBirth;
	private String panNumber;
	private Double salary;
	private Double allowance;
	private String pensionType;
	@Embedded
	private BankDetail bankDetail;
	
}
