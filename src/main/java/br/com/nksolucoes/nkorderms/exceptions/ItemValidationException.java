package br.com.nksolucoes.nkorderms.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemValidationException extends RuntimeException {


	public ItemValidationException(String message) {
		super(message);
	}

}
