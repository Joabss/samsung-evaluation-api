package com.joabe.samsungevaluationapi.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDTO {

	private String fromCurrencyCode;
	private String toCurrencyCode;
	private BigDecimal cotacao;
	private String dataHoraCotacao;
}
