package com.cognizant.process.pension.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.process.pension.model.PensionDetail;
import com.cognizant.process.pension.model.PensionerDetail;
import com.cognizant.process.pension.model.ProcessPensionResponse;
import com.cognizant.process.pension.repository.PensionDetailRepository;

@Service
public class ProcessPensionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessPensionService.class);
	private PensionDetailRepository pensionDetailRepository;

	@Autowired
	public ProcessPensionService(PensionDetailRepository pensionDetailRepository) {
		this.pensionDetailRepository = pensionDetailRepository;
	}


	/** Return the Pension Detail including the Pension Amount
	 * @param pensionerDetail
	 * @param pensionerInput
	 * @return pensionDetail
	 */
	public PensionDetail getPensionDetail(PensionerDetail pensionerDetail) {
		LOGGER.info("STARTED - getPensionDetail");
		double pensionAmount = 0;

		ProcessPensionResponse processPensionResponse = new ProcessPensionResponse();

		if (pensionerDetail.getPensionType().equalsIgnoreCase("self")) {
			pensionAmount = (0.8 * pensionerDetail.getSalary() + pensionerDetail.getAllowance());
		} else if (pensionerDetail.getPensionType().equalsIgnoreCase("family")) {
			pensionAmount = (0.5 * pensionerDetail.getSalary() + pensionerDetail.getAllowance());
		}

		if (pensionerDetail.getBankDetail().getBankType().equalsIgnoreCase("private")) {
			pensionAmount = pensionAmount - 550;

			processPensionResponse.setProcessPensionStatusCode(10);

		} else if (pensionerDetail.getBankDetail().getBankType().equalsIgnoreCase("public")) {
			pensionAmount = pensionAmount - 500;
			processPensionResponse.setProcessPensionStatusCode(10);

		}

		PensionDetail pensionDetail = new PensionDetail(pensionerDetail.getName(), pensionerDetail.getDateOfBirth(),
				pensionerDetail.getPanNumber(), pensionerDetail.getPensionType(), pensionAmount);

		LOGGER.info("END - getPensionDetail");
		return pensionDetail;

	}

	/** Save Pension Detail in the database
	 * @param pensionDetail
	 * @return
	 */
	public PensionDetail savePensionDetail(PensionDetail pensionDetail) {
		LOGGER.info("STARTED - savePensionDetail");
		LOGGER.info("END - savePensionDetail");
		return pensionDetailRepository.save(pensionDetail);

	}
}
