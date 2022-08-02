package com.cognizant.pensioner.detail.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDetail {
	private String bankName;
	private String accountNumber;
	private String bankType;
}
