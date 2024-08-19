package com.turi.infrastructure.rest;

import com.turi.account.domain.exception.AccountNotFoundException;
import com.turi.account.domain.exception.AccountUniqueAddressException;
import com.turi.account.domain.exception.AccountUniquePhoneNumberException;
import com.turi.account.domain.exception.InvalidAccountException;
import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.authentication.domain.exception.InvalidLoginException;
import com.turi.authentication.domain.exception.InvalidPasswordForLoginException;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.user.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class RestControllerErrorHandler extends ErrorHandler
{
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorCode> handleInvalidLoginException(final InvalidLoginException ex)
    {
        return createResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordForLoginException.class)
    public ResponseEntity<ErrorCode> handleInvalidPasswordForLoginException(final InvalidPasswordForLoginException ex)
    {
        return createResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorCode> handleUserNotFoundException(final UserNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundByLoginException.class)
    public ResponseEntity<ErrorCode> handleUserNotFoundByLoginException(final UserNotFoundByLoginException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorCode> handleInvalidUserException(final InvalidUserException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserUniqueUsernameException.class)
    public ResponseEntity<ErrorCode> handleUserUniqueUsernameException(final UserUniqueUsernameException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserUniqueEmailException.class)
    public ResponseEntity<ErrorCode> handleUserUniqueEmailException(final UserUniqueEmailException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorCode> handleAddressNotFoundException(final AddressNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidAddressException.class)
    public ResponseEntity<ErrorCode> handleInvalidAddressException(final InvalidAddressException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorCode> handleAccountNotFoundException(final AccountNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<ErrorCode> handleInvalidAccountException(final InvalidAccountException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccountUniqueAddressException.class)
    public ResponseEntity<ErrorCode> handleAccountUniqueAddressException(final AccountUniqueAddressException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccountUniquePhoneNumberException.class)
    public ResponseEntity<ErrorCode> handleAccountUniquePhoneNumberException(final AccountUniquePhoneNumberException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BadRequestParameterException.class)
    public ResponseEntity<ErrorCode> handleBadRequestParameterException(final BadRequestParameterException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
