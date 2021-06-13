package br.com.conversormoeda.apicoinconverter.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Representation for to present error of bad request
 *
 * @author mcrj
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BusinessException {

    public BadRequestException(final String message){ super(message); }

}
