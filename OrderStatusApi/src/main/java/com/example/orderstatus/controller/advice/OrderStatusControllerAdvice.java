package src.main.java.com.example.orderstatus.controller.advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import src.main.java.com.example.orderstatus.exception.GeneralErrorException;
import src.main.java.com.example.orderstatus.exception.InvalidRequestException;

@RestControllerAdvice
public class OrderStatusControllerAdvice {
    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        return new ResponseEntity<>("Invalid request",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {GeneralErrorException.class})
    public ResponseEntity<String> handleGeneralErrorException(GeneralErrorException e, WebRequest request) {
        return new ResponseEntity<>("Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleUnknownException(Exception e, WebRequest request) {
        return new ResponseEntity<>("Unknown",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}