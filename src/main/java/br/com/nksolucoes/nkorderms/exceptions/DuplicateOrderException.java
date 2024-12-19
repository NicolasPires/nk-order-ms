package br.com.nksolucoes.nkorderms.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DuplicateOrderException extends RuntimeException {

  public DuplicateOrderException(String message) {
    super(message);
  }
}
