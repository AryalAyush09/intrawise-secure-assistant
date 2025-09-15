package com.intrawise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intrawise.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
	
}
