package com.cognizant.pensioner.detail.service;

import java.util.List;

import com.cognizant.pensioner.detail.exception.PensionerDetailNotFoundException;
import com.cognizant.pensioner.detail.exception.ResourceNotFoundException;
import com.cognizant.pensioner.detail.model.PensionerDetail;

public interface PensionerDetailService {
	
	void savePensionersData() throws ResourceNotFoundException;
	
	PensionerDetail findByAadhaarNumber(String aadhaarNumber) throws PensionerDetailNotFoundException;
	
	List<PensionerDetail> getAllPensionerDetails();
}
