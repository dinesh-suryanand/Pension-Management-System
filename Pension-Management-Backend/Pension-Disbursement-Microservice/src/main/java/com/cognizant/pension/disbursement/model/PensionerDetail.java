package com.cognizant.pension.disbursement.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
