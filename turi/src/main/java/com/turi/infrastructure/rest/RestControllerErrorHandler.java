package com.turi.infrastructure.rest;

import com.turi.account.domain.exception.*;
import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.authentication.domain.exception.*;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.payment.domain.exception.*;
import com.turi.premium.domain.exception.*;
import com.turi.user.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class RestControllerErrorHandler extends ErrorHandler
{
    @ExceptionHandler(AccountActivationCodeExpiredException.class)
    public ResponseEntity<ErrorCode> handleAccountActivationCodeExpiredException(final AccountActivationCodeExpiredException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccountActivationCodeRecentlySentException.class)
    public ResponseEntity<ErrorCode> handleAccountActivationCodeRecentlySentException(final AccountActivationCodeRecentlySentException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorCode> handleAccountNotFoundException(final AccountNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
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

    @ExceptionHandler(AccountUniqueUserIdException.class)
    public ResponseEntity<ErrorCode> handleAccountUniqueUserIdException(final AccountUniqueUserIdException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<ErrorCode> handleInvalidAccountException(final InvalidAccountException ex)
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

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorCode> handleInvalidRefreshTokenException(final InvalidRefreshTokenException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorCode> handleRefreshTokenExpiredException(final RefreshTokenExpiredException ex)
    {
        return createResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenNotFoundByTokenException.class)
    public ResponseEntity<ErrorCode> handleRefreshTokenNotFoundByTokenException(final RefreshTokenNotFoundByTokenException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorCode> handleRefreshTokenNotFoundException(final RefreshTokenNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorCode> handleUnauthorizedException(final UnauthorizedException ex)
    {
        return createResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(BadRequestParameterException.class)
    public ResponseEntity<ErrorCode> handleBadRequestParameterException(final BadRequestParameterException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BadRequestResponseException.class)
    public ResponseEntity<ErrorCode> handleBadRequestResponseException(final BadRequestResponseException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<ErrorCode> handleInvalidPaymentException(final InvalidPaymentException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidStripePaymentException.class)
    public ResponseEntity<ErrorCode> handleInvalidStripePaymentException(final InvalidStripePaymentException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PaymentForPremiumFailedException.class)
    public ResponseEntity<ErrorCode> handlePaymentForPremiumFailedException(final PaymentForPremiumFailedException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorCode> handlePaymentNotFoundException(final PaymentNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PaymentStripeException.class)
    public ResponseEntity<ErrorCode> handlePaymentStripeException(final PaymentStripeException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PaymentWebhookException.class)
    public ResponseEntity<ErrorCode> handlePaymentWebhookException(final PaymentWebhookException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidCompanyException.class)
    public ResponseEntity<ErrorCode> handleInvalidCompanyException(final InvalidCompanyException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPremiumException.class)
    public ResponseEntity<ErrorCode> handleInvalidPremiumException(final InvalidPremiumException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PremiumActivatedException.class)
    public ResponseEntity<ErrorCode> handlePremiumActivatedException(final PremiumActivatedException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PremiumInactiveException.class)
    public ResponseEntity<ErrorCode> handlePremiumInactiveException(final PremiumInactiveException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PremiumLoginCodeExpiredException.class)
    public ResponseEntity<ErrorCode> handlePremiumLoginCodeExpiredException(final PremiumLoginCodeExpiredException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PremiumNotFoundByAccountException.class)
    public ResponseEntity<ErrorCode> handlePremiumNotFoundByAccountException(final PremiumNotFoundByAccountException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PremiumNotFoundByLoginTokenException.class)
    public ResponseEntity<ErrorCode> handlePremiumNotFoundByLoginTokenException(final PremiumNotFoundByLoginTokenException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PremiumNotFoundException.class)
    public ResponseEntity<ErrorCode> handlePremiumNotFoundException(final PremiumNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PremiumUnpaidException.class)
    public ResponseEntity<ErrorCode> handlePremiumUnpaidException(final PremiumUnpaidException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorCode> handleInvalidUserException(final InvalidUserException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundByEmailException.class)
    public ResponseEntity<ErrorCode> handleUserNotFoundByEmailException(final UserNotFoundByEmailException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundByLoginException.class)
    public ResponseEntity<ErrorCode> handleUserNotFoundByLoginException(final UserNotFoundByLoginException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundByResetTokenException.class)
    public ResponseEntity<ErrorCode> handleUserNotFoundByResetTokenException(final UserNotFoundByResetTokenException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorCode> handleUserNotFoundException(final UserNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserResetCodeExpiredException.class)
    public ResponseEntity<ErrorCode> handleUserResetCodeExpiredException(final UserResetCodeExpiredException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserResetCodeRecentlySentException.class)
    public ResponseEntity<ErrorCode> handleUserResetCodeRecentlySentException(final UserResetCodeRecentlySentException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserUniqueEmailException.class)
    public ResponseEntity<ErrorCode> handleUserUniqueEmailException(final UserUniqueEmailException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserUniqueUsernameException.class)
    public ResponseEntity<ErrorCode> handleUserUniqueUsernameException(final UserUniqueUsernameException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
