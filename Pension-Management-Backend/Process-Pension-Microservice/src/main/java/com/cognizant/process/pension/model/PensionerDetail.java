package com.cognizant.process.pension.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PensionerDetail {
	private String aadhaarNumber;
	private String name;
	private Date dateOfBirth;
	private String panNumber;
	private Double salary;
	private Double allowance;
	private String pensionType;
	private BankDetail bankDetail;
}
