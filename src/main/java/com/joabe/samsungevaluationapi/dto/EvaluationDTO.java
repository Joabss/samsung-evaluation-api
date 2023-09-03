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
public class EvaluationDTO {

	private String documentNumber;
	private String documentDate;
	private String currencyCode;
	private String currencyDesc;
	private BigDecimal documentValue;
	private BigDecimal valueUSD;
	private BigDecimal valuePEN;
	private BigDecimal valueBRL;
}
