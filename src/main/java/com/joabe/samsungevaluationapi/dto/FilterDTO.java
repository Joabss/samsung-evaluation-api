package com.joabe.samsungevaluationapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

	private String documentNumber;
	private String currencyCode;
	private String documentDateStart;
	private String documentDateEnd;


}
