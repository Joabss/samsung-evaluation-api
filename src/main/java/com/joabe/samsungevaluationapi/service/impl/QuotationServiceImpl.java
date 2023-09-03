package com.joabe.samsungevaluationapi.service.impl;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.joabe.samsungevaluationapi.dto.QuotationDTO;
import com.joabe.samsungevaluationapi.service.QuotationService;

@Service
public class QuotationServiceImpl implements QuotationService {

	@Override
	public ResponseEntity<QuotationDTO[]> getQuotation() {
		String uri = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/quotation";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<QuotationDTO[]> listQuotation = restTemplate.getForEntity(uri, QuotationDTO[].class);

		return listQuotation;
	}
}
