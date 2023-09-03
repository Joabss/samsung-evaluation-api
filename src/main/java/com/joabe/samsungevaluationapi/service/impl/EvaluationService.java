package com.joabe.samsungevaluationapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.joabe.samsungevaluationapi.dto.CurrencyDTO;
import com.joabe.samsungevaluationapi.dto.DocumentDTO;
import com.joabe.samsungevaluationapi.dto.EvaluationDTO;
import com.joabe.samsungevaluationapi.dto.QuotationDTO;
import com.joabe.samsungevaluationapi.service.CurrencyService;
import com.joabe.samsungevaluationapi.service.DocumentService;
import com.joabe.samsungevaluationapi.service.QuotationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvaluationService {

	private final CurrencyService currencyService;
	private final DocumentService documentService;
	private final QuotationService quotationService;

	public List<EvaluationDTO> getEvaluation() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		List<EvaluationDTO> listEvaluation = new ArrayList<EvaluationDTO>();

		DocumentDTO[] documents = documentService.getDocument().getBody();
		CurrencyDTO[] currencys = currencyService.getCurrency().getBody();

		Map<String, String> CurrencyMap = new HashMap<String, String>();
		Arrays.stream(currencys).forEach(c -> {
			CurrencyMap.put(c.getCurrencyCode(), c.getCurrencyDesc());
		});

		Arrays.stream(documents).forEach(d -> {
			String documentDate = d.getDocumentDate();
			String currencyCode = d.getCurrencyCode();

			Map<String, BigDecimal> lastQuotation = getLastQuotation(formatter, documentDate, currencyCode);

			BigDecimal valueBRL = BigDecimal.ZERO;
			BigDecimal valuePEN = BigDecimal.ZERO;
			BigDecimal valueUSD = BigDecimal.ZERO;
			if(currencyCode.equals("BRL")) {
				valueBRL = d.getDocumentValue();
				valuePEN = d.getDocumentValue().multiply(lastQuotation.get("PEN"));
				valueUSD = d.getDocumentValue().multiply(lastQuotation.get("USD"));
			}
			if(currencyCode.equals("PEN")) {
				valuePEN = d.getDocumentValue();
				valueBRL = d.getDocumentValue().multiply(lastQuotation.get("BRL"));
				valueUSD = d.getDocumentValue().multiply(lastQuotation.get("USD"));

			}
			if(currencyCode.equals("USD")) {
				valueUSD = d.getDocumentValue();
				valueBRL = d.getDocumentValue().multiply(lastQuotation.get("BRL"));
				valuePEN = d.getDocumentValue().multiply(lastQuotation.get("PEN"));

			}
			valueBRL = valueBRL.setScale(2, RoundingMode.DOWN);
			valuePEN = valuePEN.setScale(2, RoundingMode.DOWN);
			valueUSD = valueUSD.setScale(2, RoundingMode.DOWN);
	        
			EvaluationDTO e = EvaluationDTO.builder()
								.documentNumber(d.getDocumentNumber())
								.documentDate(documentDate)
								.currencyCode(currencyCode)
								.currencyDesc(CurrencyMap.get(currencyCode))
								.documentValue(d.getDocumentValue())
								.valueUSD(valueUSD)
								.valuePEN(valuePEN)
								.valueBRL(valueBRL)
								.build();
			listEvaluation.add(e);
		});
		return listEvaluation;
	}

	private Map<String, BigDecimal> getLastQuotation(
			SimpleDateFormat formatter, 
			String documentDate,
			String currencyCode) {
		QuotationDTO[] quotations = quotationService.getQuotation().getBody();
		Date fDocumentDate = null;
		try {
			fDocumentDate = formatter.parse(documentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, BigDecimal> lastQuotation = new HashMap<String, BigDecimal>();
		Date lastDate = null;
		String lastCode1 = "";
		BigDecimal lastQuot1 = null;
		String lastCode2 = "";
		BigDecimal lastQuot2 = null;

		for (QuotationDTO q : quotations) {
			Date fDataHoraCotacao = null;
			try {
				fDataHoraCotacao = formatter.parse(q.getDataHoraCotacao());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			
			if (fDataHoraCotacao.equals(fDocumentDate) && currencyCode.equals(q.getFromCurrencyCode())) {
				//Se as datas forem iguais e o currencyCode forem iguais
				lastQuotation.put(q.getToCurrencyCode(), q.getCotacao());
				if(lastQuotation.size() == 2)
					break;
			} else if ((lastDate == null || !lastDate.after(fDataHoraCotacao)) && currencyCode.equals(q.getFromCurrencyCode())) {
				lastDate = fDataHoraCotacao;
				
				if(lastCode1.isEmpty() || !lastCode2.isEmpty()) {
					lastCode1 = q.getToCurrencyCode();
					lastQuot1 = q.getCotacao();
					lastCode2 = "";
				} else if(lastCode2.isEmpty()) {
					lastCode2 = q.getToCurrencyCode();
					lastQuot2 = q.getCotacao();
				}
			}
		}
		if(lastQuotation.isEmpty()) {
			lastQuotation.put(lastCode1, lastQuot1);
			lastQuotation.put(lastCode2, lastQuot2);
		}

		return lastQuotation;
	}
}
