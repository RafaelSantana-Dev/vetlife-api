package com.vetlife.api.modules.vet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
    boolean existsByCrmv(String crmv);
    boolean existsByEmail(String email);
    Page<Veterinarian> findAll(Pageable pageable);
}