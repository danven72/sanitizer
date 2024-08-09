package it.bitrock.sanitizer.handler;

import it.bitrock.sanitizer.dto.ErrorDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String EXCEPTION_MESSAGE = "Caught Exception by Handler:";
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    /*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manageException(Exception exception) {
        logger.error(EXCEPTION_MESSAGE+": Unknown "+ exception.getClass().getName(), exception);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<List<ErrorDTO>> badRequest(HandlerMethodValidationException handlerMethodValidationException) {
        logger.error(EXCEPTION_MESSAGE, handlerMethodValidationException);
        return new ResponseEntity<>(fromHandlerMethodValidationException.apply(handlerMethodValidationException),
         HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDTO>> badRequest(MethodArgumentNotValidException methodArgumentNotValidException) {
        logger.error(EXCEPTION_MESSAGE, methodArgumentNotValidException);
        return new ResponseEntity<>(fromMethodArgumentNotValidException.apply(methodArgumentNotValidException),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorDTO>> badRequest(ConstraintViolationException violationException) {
        logger.error(EXCEPTION_MESSAGE, violationException);
        return new ResponseEntity<>(fromConstraintViolationException.apply(violationException), HttpStatus.BAD_REQUEST);
    }

    private final Function<ConstraintViolationException, List<ErrorDTO>> fromConstraintViolationException =
            violationException -> violationException.getConstraintViolations().stream().map(fieldError -> {
                String fieldName = StreamSupport.stream(fieldError.getPropertyPath().spliterator(), false)
                        .reduce((first, second) -> second)
                        .map(Path.Node::getName)
                        .orElse(fieldError.getPropertyPath().toString());
                return ErrorCode.buildBadRequestErrorCode(fieldName, fieldError.getMessage());
            }).map(this::toErrorDTO).toList();

    private final Function<MethodArgumentNotValidException, List<ErrorDTO>> fromMethodArgumentNotValidException =
            notValidException -> notValidException.getBindingResult().getAllErrors().stream().map(fieldError -> {
                var fieldName = Objects.requireNonNull(fieldError.getCodes())[0].split("\\.")[2];
                return ErrorCode.buildBadRequestErrorCode(fieldName, fieldError.getDefaultMessage());
            }).map(this::toErrorDTO).toList();

    private final Function<HandlerMethodValidationException, List<ErrorDTO>> fromHandlerMethodValidationException =
            notValidException -> notValidException.getAllValidationResults().stream().map(parameterValidationResult -> {
                //var result = parameterValidationResult.toString();
                String parameterName = parameterValidationResult.getMethodParameter().getParameterName();
                List<String> emsg = parameterValidationResult.getResolvableErrors().stream().map(re -> re.getDefaultMessage()).toList();
                return ErrorCode.buildBadRequestErrorCode(parameterName, emsg.toString());
            }).map(this::toErrorDTO).toList();


    private ErrorDTO toErrorDTO(ErrorCode errorCodeDomain) {
        ErrorDTO errorDTO = null;
        if(errorCodeDomain != null) {
            String code = errorCodeDomain.getErrorCode();
            String message = errorCodeDomain.getErrorMessage();
            errorDTO = new ErrorDTO(code, message);
        }
        return errorDTO;
    }
}
