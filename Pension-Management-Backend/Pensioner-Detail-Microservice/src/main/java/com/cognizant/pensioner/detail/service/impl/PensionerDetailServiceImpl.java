package com.cognizant.pensioner.detail.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.pensioner.detail.exception.PensionerDetailNotFoundException;
import com.cognizant.pensioner.detail.exception.ResourceNotFoundException;
import com.cognizant.pensioner.detail.model.BankDetail;
import com.cognizant.pensioner.detail.model.PensionerDetail;
import com.cognizant.pensioner.detail.repository.PensionerDetailRepository;
import com.cognizant.pensioner.detail.service.PensionerDetailService;
import com.cognizant.pensioner.detail.utility.DateUtility;

@Service
public class PensionerDetailServiceImpl implements PensionerDetailService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private PensionerDetailRepository pensionerDetailRepository;

	@Autowired
	public PensionerDetailServiceImpl(PensionerDetailRepository pensionerDetailRepository) {
		this.pensionerDetailRepository = pensionerDetailRepository;
	}

	/**
	 * load data into database from .csv file
	 */
	@Override
	@PostConstruct
	public void savePensionersData() throws ResourceNotFoundException {
		logger.info("STARTED - savePensionerData");
		List<PensionerDetail> pensionerDetailList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/pensionerDetails.csv"))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				PensionerDetail pensionerDetail = new PensionerDetail();
				pensionerDetail.setAadhaarNumber(data[0]);
				pensionerDetail.setName(data[1]);
				pensionerDetail.setDateOfBirth(DateUtility.getSqlDate(DateUtility.getUtilDate(data[2])));
				pensionerDetail.setPanNumber(data[3]);
				pensionerDetail.setSalary(Double.parseDouble(data[4]));
				pensionerDetail.setAllowance(Double.parseDouble(data[5]));
				pensionerDetail.setPensionType(data[6]);
				pensionerDetail.setBankDetail(new BankDetail(data[7], data[8], data[9]));

				pensionerDetailList.add(pensionerDetail);
			}

		} catch (IOException e) {
			logger.error("EXCEPTION - savePensionerData");
			throw new ResourceNotFoundException("pensioner detail not added");
		}
		pensionerDetailRepository.saveAll(pensionerDetailList);
		logger.info("END - savePensionerData");
	}

	/**
	 * @return Pensioner Detail by Aadhaar Number
	 */
	@Override
	public PensionerDetail findByAadhaarNumber(String aadhaarNumber) throws PensionerDetailNotFoundException {
		PensionerDetail pensioner = pensionerDetailRepository.findByAadhaarNumber(aadhaarNumber);
		if (pensioner == null) {
			throw new PensionerDetailNotFoundException("No pensioner found with Aadhaar Number "+aadhaarNumber);
		}
		return pensioner;
	}

	/**
	 * @return All Pensioner Details
	 */
	@Override
	public List<PensionerDetail> getAllPensionerDetails() {
		return pensionerDetailRepository.findAll();
	}

}
