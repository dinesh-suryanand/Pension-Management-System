package com.cognizant.process.pension.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.process.pension.model.PensionDetail;

@Repository
public interface PensionDetailRepository extends JpaRepository<PensionDetail, Long> {

}
