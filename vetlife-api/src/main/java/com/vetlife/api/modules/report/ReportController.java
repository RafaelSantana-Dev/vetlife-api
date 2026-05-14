package com.vetlife.api.modules.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Geração de relatórios em PDF")
@SecurityRequirement(name = "bearer-key")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/financial")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @Operation(summary = "Relatório financeiro", description = "Gera relatório financeiro em PDF por período")
    public ResponseEntity<Resource> generateFinancialReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        byte[] pdfBytes = reportService.generateFinancialReport(startDate, endDate);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        
        String filename = String.format("relatorio_financeiro_%s_a_%s.pdf", startDate, endDate);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @GetMapping("/appointments")
    @PreAuthorize("hasAnyRole('ADMIN', 'VET', 'RECEPTIONIST')")
    @Operation(summary = "Relatório de consultas", description = "Gera relatório de consultas em PDF por período")
    public ResponseEntity<Resource> generateAppointmentsReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        byte[] pdfBytes = reportService.generateAppointmentsReport(startDate, endDate);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        
        String filename = String.format("relatorio_consultas_%s_a_%s.pdf", startDate, endDate);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
