package ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiExceptionDTO {
    private String message;
    private HttpStatus httpStatus;
}
