package com.intrawise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intrawise.entities.RagQueryLog;

@Repository
public interface RagQueryLogRepository extends JpaRepository<RagQueryLog, Long> {
}
