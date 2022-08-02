package com.cognizant.pension.disbursement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cognizant.pension.disbursement.model.PensionerDetail;
import com.cognizant.pension.disbursement.model.ProcessPensionInput;
import com.cognizant.pension.disbursement.model.ProcessPensionResponse;

@Service
public class PensionDisbursementService {

	private static final Logger logger = LoggerFactory.getLogger(PensionDisbursementService.class);

	private int successCode = 10;
	private int failedCode = 21;

	// Returning success response in case of matching pension amount for given user.

	/**
	 * @param pensionerDetail
	 * @param processPensionInput
	 * @return
	 */
	public ProcessPensionResponse statusCode(PensionerDetail pensionerDetail, ProcessPensionInput processPensionInput) {

		logger.info("STARTED - statusCode");
		String bankType = pensionerDetail.getBankDetail().getBankType();
		Double bankServiceCharge = processPensionInput.getBankServiceCharge();
		if (inputBankCharge(bankType, bankServiceCharge)
				&& calculatePension(pensionerDetail, processPensionInput.getPensionAmount())) {
			logger.info("END - statusCode - success");
			return new ProcessPensionResponse(successCode);
		}

		logger.info("END - statusCode - fail");
		return new ProcessPensionResponse(failedCode);
	}

	// calculating pension amount for different types of pension

	/**
	 * @param pensionerDetail
	 * @param pensionInput
	 * @return
	 */
	public boolean calculatePension(PensionerDetail pensionerDetail, Double pensionInput) {
		logger.info("STARTED - calculatePension");
		Double pensionAmount = 0.0;
		if (pensionerDetail.getPensionType().equalsIgnoreCase("self")) {
			pensionAmount = 0.8 * pensionerDetail.getSalary() + pensionerDetail.getAllowance();
		} else if (pensionerDetail.getPensionType().equalsIgnoreCase("family")) {
			pensionAmount = 0.5 * pensionerDetail.getSalary() + pensionerDetail.getAllowance();
		}
		if (pensionerDetail.getBankDetail().getBankType().equalsIgnoreCase("private")) {
			pensionAmount = pensionAmount - 550;

		} else if (pensionerDetail.getBankDetail().getBankType().equalsIgnoreCase("public")) {
			pensionAmount = pensionAmount - 500;
		}
		logger.info("END - calculatePension");
		return pensionAmount.equals(pensionInput);
	}

	// checking bank charge for private and public banks

	/**
	 * @param bankType
	 * @param bankCharge
	 * @return
	 */
	public boolean inputBankCharge(String bankType, Double bankCharge) {

		logger.info("STARTED - inputBankCharge");
		if (bankType.equalsIgnoreCase("private")) {
			if (bankCharge.equals(550.0)) {
				logger.info("END - inputBankCharge - private - true");
				return true;
			} else {
				logger.info("END - inputBankCharge - private - false");
				return false;
			}
		}
		if (bankType.equalsIgnoreCase("public")) {
			if (bankCharge.equals(500.0)) {
				logger.info("END - inputBankCharge - public - true");
				return true;
			} else {
				logger.info("END - inputBankCharge - public - false");
				return false;
			}
		}
		logger.info("END - inputBankCharge - false");
		return false;
	}

}
