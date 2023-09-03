package com.joabe.samsungevaluationapi.service;

import org.springframework.http.ResponseEntity;

import com.joabe.samsungevaluationapi.dto.DocumentDTO;

public interface DocumentService {

	ResponseEntity<DocumentDTO[]> getDocument();
}
