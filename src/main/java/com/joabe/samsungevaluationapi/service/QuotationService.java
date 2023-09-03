package com.joabe.samsungevaluationapi.service;

import org.springframework.http.ResponseEntity;

import com.joabe.samsungevaluationapi.dto.QuotationDTO;

public interface QuotationService {

	ResponseEntity<QuotationDTO[]> getQuotation();
}
