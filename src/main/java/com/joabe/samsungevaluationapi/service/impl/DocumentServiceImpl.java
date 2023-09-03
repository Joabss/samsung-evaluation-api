package com.joabe.samsungevaluationapi.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.joabe.samsungevaluationapi.dto.DocumentDTO;
import com.joabe.samsungevaluationapi.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Override
	public ResponseEntity<DocumentDTO[]> getDocument() {
		String uri = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/docs";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<DocumentDTO[]> listDocument = restTemplate.getForEntity(uri, DocumentDTO[].class);

		return listDocument;
	}

}
