package com.vetlife.api.modules.medical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.pet.id = :petId ORDER BY mr.createdAt DESC")
    Page<MedicalRecord> findByPetId(@Param("petId") Long petId, Pageable pageable);
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.pet.id = :petId ORDER BY mr.createdAt DESC")
    List<MedicalRecord> findAllByPetId(@Param("petId") Long petId);
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.vet.id = :vetId ORDER BY mr.createdAt DESC")
    Page<MedicalRecord> findByVetId(@Param("vetId") Long vetId, Pageable pageable);
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.type = :type ORDER BY mr.createdAt DESC")
    Page<MedicalRecord> findByType(@Param("type") RecordType type, Pageable pageable);
}
