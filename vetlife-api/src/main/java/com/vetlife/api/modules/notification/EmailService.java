package com.vetlife.api.modules.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username:noreply@vetlife.com}")
    private String fromEmail;
    
    @Value("${app.email.enabled:false}")
    private boolean emailEnabled;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    @Async
    public void sendAppointmentConfirmation(String toEmail, String clientName, String petName, 
                                           LocalDateTime appointmentDate, String vetName) {
        if (!emailEnabled) {
            log.info("Email desabilitado. Simulando envio para: {}", toEmail);
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Confirmação de Consulta - VetLife");
            message.setText(buildAppointmentConfirmationBody(clientName, petName, appointmentDate, vetName));
            
            mailSender.send(message);
            log.info("Email de confirmação enviado para: {}", toEmail);
        } catch (Exception e) {
            log.error("Erro ao enviar email de confirmação", e);
        }
    }

    @Async
    public void sendAppointmentReminder(String toEmail, String clientName, String petName, 
                                       LocalDateTime appointmentDate, String vetName) {
        if (!emailEnabled) {
            log.info("Email desabilitado. Simulando envio de lembrete para: {}", toEmail);
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Lembrete de Consulta - VetLife");
            message.setText(buildAppointmentReminderBody(clientName, petName, appointmentDate, vetName));
            
            mailSender.send(message);
            log.info("Email de lembrete enviado para: {}", toEmail);
        } catch (Exception e) {
            log.error("Erro ao enviar email de lembrete", e);
        }
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String clientName) {
        if (!emailEnabled) {
            log.info("Email desabilitado. Simulando envio de boas-vindas para: {}", toEmail);
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Bem-vindo à VetLife!");
            message.setText(buildWelcomeBody(clientName));
            
            mailSender.send(message);
            log.info("Email de boas-vindas enviado para: {}", toEmail);
        } catch (Exception e) {
            log.error("Erro ao enviar email de boas-vindas", e);
        }
    }

    private String buildAppointmentConfirmationBody(String clientName, String petName, 
                                                   LocalDateTime appointmentDate, String vetName) {
        return String.format("""
            Olá, %s!
            
            Sua consulta foi confirmada com sucesso!
            
            Detalhes da Consulta:
            - Pet: %s
            - Data e Hora: %s
            - Veterinário(a): %s
            
            Por favor, chegue com 10 minutos de antecedência.
            
            Em caso de cancelamento ou reagendamento, entre em contato conosco.
            
            Atenciosamente,
            Equipe VetLife
            """, 
            clientName, 
            petName, 
            appointmentDate.format(DATE_FORMATTER), 
            vetName
        );
    }

    private String buildAppointmentReminderBody(String clientName, String petName, 
                                               LocalDateTime appointmentDate, String vetName) {
        return String.format("""
            Olá, %s!
            
            Este é um lembrete da sua consulta agendada.
            
            Detalhes da Consulta:
            - Pet: %s
            - Data e Hora: %s
            - Veterinário(a): %s
            
            Não esqueça de trazer a carteira de vacinação do seu pet!
            
            Atenciosamente,
            Equipe VetLife
            """, 
            clientName, 
            petName, 
            appointmentDate.format(DATE_FORMATTER), 
            vetName
        );
    }

    private String buildWelcomeBody(String clientName) {
        return String.format("""
            Olá, %s!
            
            Seja bem-vindo(a) à VetLife!
            
            Estamos muito felizes em tê-lo(a) como nosso cliente.
            
            Nossa clínica oferece:
            - Consultas veterinárias
            - Cirurgias
            - Vacinação
            - Exames laboratoriais
            - E muito mais!
            
            Para agendar sua primeira consulta, entre em contato conosco.
            
            Atenciosamente,
            Equipe VetLife
            """, 
            clientName
        );
    }
}
