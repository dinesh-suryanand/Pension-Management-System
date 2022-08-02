package com.cognizant.pension.disbursement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPensionInput {
	private String aadhaarNumber;
	private Double pensionAmount;
	private Double bankServiceCharge;
}
