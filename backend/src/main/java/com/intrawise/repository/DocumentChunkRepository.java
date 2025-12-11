package com.intrawise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intrawise.entities.DocumentChunk;

@Repository
public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

//	@Query(value = """
//		    SELECT chunk_text,
//		        embedding,
//		        role_allowed,
//		        chunk_index,
//		        document_id,
//		        (embedding <=> CAST(:queryEmbedding AS vector)) AS similarity_score
//		    FROM document_chunks
//		    WHERE role_allowed = :userRole
//		      AND (embedding <=> CAST(:queryEmbedding AS vector)) < 0.40
//		    ORDER BY embedding <=> CAST(:queryEmbedding AS vector)
//		    LIMIT :limit
//		    """,
//		    nativeQuery = true)
	
	@Query(value = "SELECT chunk_text, embedding, role_allowed, chunk_index, document_id, " +
            "(embedding <=> CAST(:queryEmbedding AS vector)) AS similarity_score " +
            "FROM document_chunks " +
//            "WHERE role_allowed = :userRole " +
            "WHERE (embedding <=> CAST(:queryEmbedding AS vector)) < 0.40 " +
            "ORDER BY embedding <=> CAST(:queryEmbedding AS vector) " +
            "LIMIT :limit",
    nativeQuery = true)

    List<Object[]> searchSimilarChunksRaw(
//            @Param("userRole") String userRole,
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("limit") int limit);
}
