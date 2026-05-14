package com.vetlife.api.modules.appointment;

import com.vetlife.api.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentValidationService {

    private final AppointmentRepository repository;
    
    private static final int APPOINTMENT_DURATION_MINUTES = 30;

    public void validateAppointment(Long vetId, LocalDateTime dataHora, Long appointmentIdToExclude) {
        log.info("Validando agendamento para veterinário ID: {} em {}", vetId, dataHora);
        
        // Validar horário comercial
        validateBusinessHours(dataHora);
        
        // Validar conflito de horário
        validateTimeConflict(vetId, dataHora, appointmentIdToExclude);
        
        // Validar data futura
        validateFutureDate(dataHora);
        
        log.info("Agendamento validado com sucesso");
    }

    private void validateBusinessHours(LocalDateTime dataHora) {
        int hour = dataHora.getHour();
        int dayOfWeek = dataHora.getDayOfWeek().getValue();
        
        // Verificar se é fim de semana
        if (dayOfWeek == 6 || dayOfWeek == 7) {
            throw new BusinessException("Não é possível agendar consultas aos finais de semana");
        }
        
        // Verificar horário comercial (8h às 18h)
        if (hour < 8 || hour >= 18) {
            throw new BusinessException("Horário fora do expediente. Horário de atendimento: 8h às 18h");
        }
        
        // Verificar se é hora cheia ou meia hora
        int minute = dataHora.getMinute();
        if (minute != 0 && minute != 30) {
            throw new BusinessException("Consultas devem ser agendadas em horários de 30 em 30 minutos (ex: 08:00, 08:30, 09:00)");
        }
    }

    private void validateTimeConflict(Long vetId, LocalDateTime dataHora, Long appointmentIdToExclude) {
        LocalDateTime startWindow = dataHora.minusMinutes(APPOINTMENT_DURATION_MINUTES - 1);
        LocalDateTime endWindow = dataHora.plusMinutes(APPOINTMENT_DURATION_MINUTES - 1);
        
        List<Appointment> conflictingAppointments = repository.findConflictingAppointments(
                vetId, startWindow, endWindow);
        
        // Remover o próprio agendamento se estiver editando
        if (appointmentIdToExclude != null) {
            conflictingAppointments.removeIf(a -> a.getId().equals(appointmentIdToExclude));
        }
        
        if (!conflictingAppointments.isEmpty()) {
            Appointment conflict = conflictingAppointments.get(0);
            throw new BusinessException(String.format(
                    "Conflito de horário! O veterinário já possui uma consulta agendada às %s com o pet %s",
                    conflict.getDataHora().toLocalTime(),
                    conflict.getPet().getNome()
            ));
        }
    }

    private void validateFutureDate(LocalDateTime dataHora) {
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Não é possível agendar consultas no passado");
        }
        
        // Validar antecedência mínima de 1 hora
        if (dataHora.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BusinessException("Consultas devem ser agendadas com pelo menos 1 hora de antecedência");
        }
        
        // Validar antecedência máxima de 90 dias
        if (dataHora.isAfter(LocalDateTime.now().plusDays(90))) {
            throw new BusinessException("Não é possível agendar consultas com mais de 90 dias de antecedência");
        }
    }

    public List<LocalDateTime> getAvailableSlots(Long vetId, LocalDateTime date) {
        log.info("Buscando horários disponíveis para veterinário ID: {} no dia {}", vetId, date.toLocalDate());
        
        LocalDateTime startOfDay = date.toLocalDate().atTime(8, 0);
        LocalDateTime endOfDay = date.toLocalDate().atTime(18, 0);
        
        List<Appointment> existingAppointments = repository.findByVetIdAndDateRange(
                vetId, startOfDay, endOfDay);
        
        List<LocalDateTime> availableSlots = new java.util.ArrayList<>();
        LocalDateTime currentSlot = startOfDay;
        
        while (currentSlot.isBefore(endOfDay)) {
            LocalDateTime finalCurrentSlot = currentSlot;
            boolean isOccupied = existingAppointments.stream()
                    .anyMatch(a -> Math.abs(java.time.Duration.between(a.getDataHora(), finalCurrentSlot).toMinutes()) < APPOINTMENT_DURATION_MINUTES);
            
            if (!isOccupied && currentSlot.isAfter(LocalDateTime.now().plusHours(1))) {
                availableSlots.add(currentSlot);
            }
            
            currentSlot = currentSlot.plusMinutes(30);
        }
        
        log.info("Encontrados {} horários disponíveis", availableSlots.size());
        return availableSlots;
    }
}
