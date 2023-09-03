package com.joabe.samsungevaluationapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joabe.samsungevaluationapi.dto.CurrencyDTO;
import com.joabe.samsungevaluationapi.dto.DocumentDTO;
import com.joabe.samsungevaluationapi.dto.EvaluationDTO;
import com.joabe.samsungevaluationapi.dto.FilterDTO;
import com.joabe.samsungevaluationapi.dto.QuotationDTO;
import com.joabe.samsungevaluationapi.service.CurrencyService;
import com.joabe.samsungevaluationapi.service.DocumentService;
import com.joabe.samsungevaluationapi.service.QuotationService;
import com.joabe.samsungevaluationapi.service.impl.EvaluationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class EvaluationController {

	private final CurrencyService currencyService;
	private final DocumentService documentService;
	private final QuotationService quotationService;
	private final EvaluationService evaluationService;

	@GetMapping("/currencys")
	public ResponseEntity<Object> getCurrency() {

		ResponseEntity<CurrencyDTO[]> listCurrency = currencyService.getCurrency();

		if (listCurrency.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(listCurrency.getBody());
		} else {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta de Moedas.");
		}
	}

	@GetMapping("/documents")
	public ResponseEntity<Object> getDocument() {

		ResponseEntity<DocumentDTO[]> listDocument = documentService.getDocument();

		if (listDocument.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(listDocument.getBody());
		} else {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta de Documentos.");
		}
	}

	@GetMapping("/quotations")
	public ResponseEntity<Object> getQuotation() {

		ResponseEntity<QuotationDTO[]> listQuotation = quotationService.getQuotation();

		if (listQuotation.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(listQuotation.getBody());
		} else {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta de Cotações.");
		}
	}

	@GetMapping("/evaluations")
	public ResponseEntity<Object> getEvaluation(
			@RequestParam(value = "documentNumber", required = false) String documentNumber,
			@RequestParam(value = "currencyCode", required = false) String currencyCode,
			@RequestParam(value = "documentDateStart", required = false) String documentDateStart,
			@RequestParam(value = "documentDateEnd", required = false) String documentDateEnd
	) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setDocumentNumber(documentNumber);
		filterDTO.setCurrencyCode(currencyCode);
		filterDTO.setDocumentDateStart(documentDateStart);
		filterDTO.setDocumentDateEnd(documentDateEnd);

		List<EvaluationDTO> listEvaluationDTO = evaluationService.getEvaluation();

		List<EvaluationDTO> filtrado = listEvaluationDTO
				.stream()
				.filter(e -> {
					boolean result = true;
					if(documentNumber != null) {
						result &= e.getDocumentNumber().equals(documentNumber);
					}
					if(currencyCode  != null) {
						result &= e.getCurrencyCode().equals(currencyCode);
					}
					if(documentDateStart  != null && documentDateStart != null) {
						try {
							result &= formatter.parse(e.getDocumentDate()).compareTo(formatter.parse(documentDateStart)) >= 0;
							result &= formatter.parse(e.getDocumentDate()).compareTo(formatter.parse(documentDateEnd)) <= 0;
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}

					return result;
				})
				.collect(Collectors.toList());

		return ResponseEntity.ok(filtrado);
	}
}