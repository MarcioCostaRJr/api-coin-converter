package br.com.conversormoeda.apicoinconverter.security;

/**
 * Representation for to present exception of business
 *
 * @author mcrj
 */
public abstract class BusinessException extends RuntimeException {

    public BusinessException(final String message) { super(message); }

}
