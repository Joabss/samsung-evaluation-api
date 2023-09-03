package com.joabe.samsungevaluationapi.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.joabe.samsungevaluationapi.dto.CurrencyDTO;
import com.joabe.samsungevaluationapi.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Override
	public ResponseEntity<CurrencyDTO[]> getCurrency() {
		String uri = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/currency";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<CurrencyDTO[]> listCurrency = restTemplate.getForEntity(uri, CurrencyDTO[].class);

		return listCurrency;
	}
}
