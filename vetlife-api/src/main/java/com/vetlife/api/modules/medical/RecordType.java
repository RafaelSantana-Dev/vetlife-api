package com.vetlife.api.modules.medical;

public enum RecordType {
    CONSULTA("Consulta de Rotina"),
    EMERGENCIA("Atendimento de Emergência"),
    CIRURGIA("Procedimento Cirúrgico"),
    VACINA("Vacinação"),
    EXAME("Exame Laboratorial"),
    RETORNO("Consulta de Retorno"),
    OUTRO("Outro");
    
    private final String description;
    
    RecordType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
