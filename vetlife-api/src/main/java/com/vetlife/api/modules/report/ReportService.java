package com.vetlife.api.modules.report;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.vetlife.api.modules.appointment.Appointment;
import com.vetlife.api.modules.appointment.AppointmentRepository;
import com.vetlife.api.modules.finance.FinancialRecord;
import com.vetlife.api.modules.finance.FinancialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final FinancialRepository financialRepository;
    private final AppointmentRepository appointmentRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generateFinancialReport(LocalDate startDate, LocalDate endDate) {
        log.info("Gerando relatório financeiro de {} a {}", startDate, endDate);
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título
            Paragraph title = new Paragraph("RELATÓRIO FINANCEIRO")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Período
            Paragraph period = new Paragraph(String.format("Período: %s a %s", 
                    startDate.format(DATE_FORMATTER), 
                    endDate.format(DATE_FORMATTER)))
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(period);

            // Buscar dados
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(23, 59, 59);
            List<FinancialRecord> records = financialRepository.findByDataBetween(start, end);

            // Calcular totais
            BigDecimal totalEntradas = records.stream()
                    .filter(r -> "ENTRADA".equals(r.getTipo()))
                    .map(FinancialRecord::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalSaidas = records.stream()
                    .filter(r -> "SAIDA".equals(r.getTipo()))
                    .map(FinancialRecord::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saldo = totalEntradas.subtract(totalSaidas);

            // Resumo
            Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                    .useAllAvailableWidth()
                    .setMarginBottom(20);

            summaryTable.addCell(createHeaderCell("Total de Entradas"));
            summaryTable.addCell(createValueCell(String.format("R$ %.2f", totalEntradas)));
            summaryTable.addCell(createHeaderCell("Total de Saídas"));
            summaryTable.addCell(createValueCell(String.format("R$ %.2f", totalSaidas)));
            summaryTable.addCell(createHeaderCell("Saldo"));
            summaryTable.addCell(createValueCell(String.format("R$ %.2f", saldo))
                    .setBackgroundColor(saldo.compareTo(BigDecimal.ZERO) >= 0 ? 
                            ColorConstants.LIGHT_GRAY : ColorConstants.PINK));

            document.add(summaryTable);

            // Detalhamento
            Paragraph detailTitle = new Paragraph("Detalhamento")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(10)
                    .setMarginBottom(10);
            document.add(detailTitle);

            Table detailTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1}))
                    .useAllAvailableWidth();

            detailTable.addHeaderCell(createHeaderCell("Descrição"));
            detailTable.addHeaderCell(createHeaderCell("Data"));
            detailTable.addHeaderCell(createHeaderCell("Tipo"));
            detailTable.addHeaderCell(createHeaderCell("Valor"));

            for (FinancialRecord record : records) {
                detailTable.addCell(new Cell().add(new Paragraph(record.getDescricao())));
                detailTable.addCell(new Cell().add(new Paragraph(
                        record.getDataLancamento().format(DATETIME_FORMATTER))));
                detailTable.addCell(new Cell().add(new Paragraph(record.getTipo())));
                detailTable.addCell(new Cell().add(new Paragraph(
                        String.format("R$ %.2f", record.getValor()))));
            }

            document.add(detailTable);

            // Rodapé
            Paragraph footer = new Paragraph(String.format("Gerado em: %s", 
                    LocalDateTime.now().format(DATETIME_FORMATTER)))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(20);
            document.add(footer);

            document.close();
            log.info("Relatório financeiro gerado com sucesso");
            return baos.toByteArray();
            
        } catch (Exception e) {
            log.error("Erro ao gerar relatório financeiro", e);
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public byte[] generateAppointmentsReport(LocalDate startDate, LocalDate endDate) {
        log.info("Gerando relatório de consultas de {} a {}", startDate, endDate);
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título
            Paragraph title = new Paragraph("RELATÓRIO DE CONSULTAS")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Período
            Paragraph period = new Paragraph(String.format("Período: %s a %s", 
                    startDate.format(DATE_FORMATTER), 
                    endDate.format(DATE_FORMATTER)))
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(period);

            // Buscar dados
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(23, 59, 59);
            List<Appointment> appointments = appointmentRepository.findByDataBetween(start, end);

            // Resumo
            Paragraph summary = new Paragraph(String.format("Total de consultas: %d", appointments.size()))
                    .setFontSize(14)
                    .setBold()
                    .setMarginBottom(20);
            document.add(summary);

            // Tabela de consultas
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}))
                    .useAllAvailableWidth();

            table.addHeaderCell(createHeaderCell("Data/Hora"));
            table.addHeaderCell(createHeaderCell("Pet"));
            table.addHeaderCell(createHeaderCell("Tutor"));
            table.addHeaderCell(createHeaderCell("Veterinário"));

            for (Appointment appointment : appointments) {
                table.addCell(new Cell().add(new Paragraph(
                        appointment.getDataHora().format(DATETIME_FORMATTER))));
                table.addCell(new Cell().add(new Paragraph(appointment.getPet().getNome())));
                table.addCell(new Cell().add(new Paragraph(appointment.getPet().getClient().getNome())));
                table.addCell(new Cell().add(new Paragraph(appointment.getVet().getNome())));
            }

            document.add(table);

            // Rodapé
            Paragraph footer = new Paragraph(String.format("Gerado em: %s", 
                    LocalDateTime.now().format(DATETIME_FORMATTER)))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(20);
            document.add(footer);

            document.close();
            log.info("Relatório de consultas gerado com sucesso");
            return baos.toByteArray();
            
        } catch (Exception e) {
            log.error("Erro ao gerar relatório de consultas", e);
            throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private Cell createHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Cell createValueCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setTextAlignment(TextAlignment.CENTER);
    }
}
