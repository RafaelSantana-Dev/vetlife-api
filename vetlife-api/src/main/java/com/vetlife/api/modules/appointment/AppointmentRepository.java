package com.vetlife.api.modules.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByVetId(Long vetId, Pageable pageable);
    
    @Query("SELECT a FROM Appointment a WHERE a.dataHora BETWEEN :startDate AND :endDate ORDER BY a.dataHora ASC")
    List<Appointment> findByDataBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Appointment a WHERE a.vet.id = :vetId AND a.dataHora BETWEEN :startWindow AND :endWindow AND a.status != 'CANCELADA'")
    List<Appointment> findConflictingAppointments(@Param("vetId") Long vetId,
                                                   @Param("startWindow") LocalDateTime startWindow,
                                                   @Param("endWindow") LocalDateTime endWindow);
    
    @Query("SELECT a FROM Appointment a WHERE a.vet.id = :vetId AND a.dataHora BETWEEN :startOfDay AND :endOfDay AND a.status != 'CANCELADA' ORDER BY a.dataHora ASC")
    List<Appointment> findByVetIdAndDateRange(@Param("vetId") Long vetId,
                                               @Param("startOfDay") LocalDateTime startOfDay,
                                               @Param("endOfDay") LocalDateTime endOfDay);
}