package com.vetlife.api.modules.client;

import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> hasNome(String nome) {
        return (root, query, cb) -> 
            nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Client> hasEmail(String email) {
        return (root, query, cb) -> 
            email == null ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<Client> isAtivo(Boolean ativo) {
        return (root, query, cb) -> 
            ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }
}
