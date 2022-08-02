package com.cognizant.process.pension.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPensionInput {
	private String aadhaarNumber;
	private Double pensionAmount;
	private Double bankServiceCharge;
}
