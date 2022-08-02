package com.cognizant.pensioner.detail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.pensioner.detail.model.PensionerDetail;

@Repository
public interface PensionerDetailRepository extends JpaRepository<PensionerDetail, Long> {
	
	PensionerDetail findByAadhaarNumber(String aadharNumber);
}
