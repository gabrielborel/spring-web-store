package borelgabriel.com.br.springwebstore;

import borelgabriel.com.br.springwebstore.exceptions.ResourceAlreadyExistsException;
import borelgabriel.com.br.springwebstore.exceptions.ResourceNotFoundException;
import borelgabriel.com.br.springwebstore.model.dto.ErrorObjectDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();
        StringBuilder message = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError objectError : list) {
                message.append(objectError.getDefaultMessage()).append("\n");
            }
        } else {
            message.append(ex.getMessage());
        }

        errorObjectDTO.setError(message.toString());
        errorObjectDTO.setCode(String.valueOf(statusCode.value()));
        return new ResponseEntity<Object>(errorObjectDTO, statusCode);
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            SQLException.class
    })
    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception ex) {
        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();
        StringBuilder message = new StringBuilder();

        if (ex instanceof DataIntegrityViolationException) {
            message.append("DataIntegrityViolationException: ").append(ex.getCause().getCause().getMessage());
        } else if (ex instanceof ConstraintViolationException) {
            message.append("ConstraintViolationException: ").append(ex.getCause().getCause().getMessage());
        } else if (ex instanceof SQLException) {
            message.append("SQLException: ").append(ex.getCause().getCause().getMessage());
        } else {
            message.append(ex.getMessage());
        }

        errorObjectDTO.setError(message.toString());
        errorObjectDTO.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<Object>(errorObjectDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();
        errorObjectDTO.setError(ex.getMessage());
        errorObjectDTO.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<Object>(errorObjectDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ResourceAlreadyExistsException.class })
    public ResponseEntity<Object> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();
        errorObjectDTO.setError(ex.getMessage());
        errorObjectDTO.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<Object>(errorObjectDTO, HttpStatus.BAD_REQUEST);
    }
}
