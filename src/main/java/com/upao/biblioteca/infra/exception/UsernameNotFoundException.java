package com.upao.biblioteca.infra.exception;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un nombre de usuario.
 * Esta clase extiende de {@link RuntimeException}, lo que la hace una excepción no comprobada.
 */

public class UsernameNotFoundException extends RuntimeException {

    /**
     * Constructor para crear una excepción con un mensaje específico.
     *
     * @param message El mensaje detallando la razón de la excepción.
     */

    public UsernameNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor para crear una excepción con un mensaje y una causa subyacente.
     *
     * @param message El mensaje detallando la razón de la excepción.
     * @param cause   La causa original de esta excepción, típicamente otra excepción.
     */

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}