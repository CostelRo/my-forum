package app.controller.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.NotNull;
import java.sql.SQLIntegrityConstraintViolationException;


@RestControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler( MethodArgumentNotValidException.class )
    protected ResponseEntity<Object> handleMethodArgumentNotValid( @NotNull MethodArgumentNotValidException ex )
    {
        System.out.println( "\n>>>> handleMethodArgumentNotValid: ..." );

        String[] fullErrorMessage = ex.getMessage().split( "default message" );
        String simpleErrorMessage = fullErrorMessage[2].replaceAll( "[\\[\\]]", "" );

        return new ResponseEntity<>( simpleErrorMessage, HttpStatus.FORBIDDEN );

//        return new ResponseEntity<>( ex.getMessage(), HttpStatus.FORBIDDEN );
    }


    @ExceptionHandler( SQLIntegrityConstraintViolationException.class )
    protected ResponseEntity<Object> handleSQLIntegrityConstraintViolation( @NotNull SQLIntegrityConstraintViolationException ex )
    {
        System.out.println( "\n>>>> handleSQLIntegrityConstraintViolation: ..." );

        return new ResponseEntity<>( "Non-existing or duplicate data in the database: ", HttpStatus.FORBIDDEN );
    }


    @ExceptionHandler( IllegalArgumentException.class )
    protected ResponseEntity<Object> handleIllegalArgumentException( @NotNull IllegalArgumentException ex )
    {
        System.out.println( "\n>>>> handleIllegalArgumentException: ..." );

        return new ResponseEntity<>( ex.getMessage(), HttpStatus.FORBIDDEN );
    }
}
