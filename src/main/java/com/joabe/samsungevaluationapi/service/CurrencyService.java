package com.joabe.samsungevaluationapi.service;

import org.springframework.http.ResponseEntity;

import com.joabe.samsungevaluationapi.dto.CurrencyDTO;

public interface CurrencyService {

	ResponseEntity<CurrencyDTO[]> getCurrency();

}
