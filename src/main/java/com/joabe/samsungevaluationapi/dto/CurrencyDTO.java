package com.joabe.samsungevaluationapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

	private Long currencyId;
	private String currencyCode;
	private String currencyDesc;
}