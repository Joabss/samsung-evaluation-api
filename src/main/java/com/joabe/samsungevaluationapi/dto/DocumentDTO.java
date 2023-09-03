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
public class DocumentDTO {

	private Long documentId;
	private String documentNumber;
	private String notaFiscal;
	private String documentDate;
	private BigDecimal documentValue;
	private String currencyCode;
}