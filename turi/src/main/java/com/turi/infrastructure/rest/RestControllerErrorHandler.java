package com.turi.infrastructure.rest;

import com.turi.account.domain.exception.*;
import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.attraction.domain.exception.AttractionNotFoundException;
import com.turi.attraction.domain.exception.InvalidAttractionException;
import com.turi.authentication.domain.exception.*;
import com.turi.image.domain.exception.*;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.offer.domain.exception.InvalidFavouriteException;
import com.turi.offer.domain.exception.UniqueFavouriteException;
import com.turi.payment.domain.exception.*;
import com.turi.premium.domain.exception.*;
import com.turi.reservation.domain.exception.*;
import com.turi.stay.domain.exception.InvalidStayException;
import com.turi.stay.domain.exception.InvalidStayInformationException;
import com.turi.stay.domain.exception.StayNotFoundException;
import com.turi.touristicplace.domain.exception.*;
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

    @ExceptionHandler(AttractionNotFoundException.class)
    public ResponseEntity<ErrorCode> handleAttractionNotFoundException(final AttractionNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidAttractionException.class)
    public ResponseEntity<ErrorCode> handleInvalidAttractionException(final InvalidAttractionException ex)
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

    @ExceptionHandler(AzureBlobStorageUploadException.class)
    public ResponseEntity<ErrorCode> handleAzureBlobStorageUploadException(final AzureBlobStorageUploadException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorCode> handleImageNotFoundException(final ImageNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ImageNotFoundForAccountException.class)
    public ResponseEntity<ErrorCode> handleImageNotFoundForAccountException(final ImageNotFoundForAccountException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ImageStorageModeException.class)
    public ResponseEntity<ErrorCode> handleImageStorageModeException(final ImageStorageModeException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ErrorCode> handleInvalidAttractionException(final InvalidImageException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidImageNameException.class)
    public ResponseEntity<ErrorCode> handleInvalidImageNameException(final InvalidImageNameException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LocalStorageException.class)
    public ResponseEntity<ErrorCode> handleLocalStorageException(final LocalStorageException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
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

    @ExceptionHandler(InvalidFavouriteException.class)
    public ResponseEntity<ErrorCode> handleInvalidFavouriteException(final InvalidFavouriteException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UniqueFavouriteException.class)
    public ResponseEntity<ErrorCode> handleUniqueFavouriteException(final UniqueFavouriteException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<ErrorCode> handleInvalidPaymentException(final InvalidPaymentException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPaymentReservationAttractionException.class)
    public ResponseEntity<ErrorCode> handleInvalidPaymentReservationAttractionException(final InvalidPaymentReservationAttractionException ex)
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

    @ExceptionHandler(PaymentForReservationFailedException.class)
    public ResponseEntity<ErrorCode> handlePaymentForReservationFailedException(final PaymentForReservationFailedException ex)
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

    @ExceptionHandler(UniquePaymentReservationAttractionException.class)
    public ResponseEntity<ErrorCode> handleUniquePaymentReservationAttractionException(final UniquePaymentReservationAttractionException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
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

    @ExceptionHandler(InvalidReservationAttractionException.class)
    public ResponseEntity<ErrorCode> handleInvalidReservationAttractionException(final InvalidReservationAttractionException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationDateException.class)
    public ResponseEntity<ErrorCode> handleInvalidReservationDateException(final InvalidReservationDateException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<ErrorCode> handleInvalidReservationException(final InvalidReservationException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationTimeException.class)
    public ResponseEntity<ErrorCode> handleInvalidReservationTimeException(final InvalidReservationTimeException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReservationAttractionException.class)
    public ResponseEntity<ErrorCode> handleReservationAttractionException(final ReservationAttractionException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReservationAttractionUnavailableException.class)
    public ResponseEntity<ErrorCode> handleReservationAttractionUnavailableException(final ReservationAttractionUnavailableException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReservationCompletedException.class)
    public ResponseEntity<ErrorCode> handleReservationCompletedException(final ReservationCompletedException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundByStayException.class)
    public ResponseEntity<ErrorCode> handleReservationNotFoundByStayException(final ReservationNotFoundByStayException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorCode> handleReservationNotFoundException(final ReservationNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ReservationPaidOnSiteException.class)
    public ResponseEntity<ErrorCode> handleReservationPaidOnSiteException(final ReservationPaidOnSiteException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReservationStayUnavailableException.class)
    public ResponseEntity<ErrorCode> handleReservationStayUnavailableException(final ReservationStayUnavailableException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ReservationUnpaidException.class)
    public ResponseEntity<ErrorCode> handleReservationUnpaidException(final ReservationUnpaidException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(StayNotFoundException.class)
    public ResponseEntity<ErrorCode> handleStayNotFoundException(final StayNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidStayException.class)
    public ResponseEntity<ErrorCode> handleInvalidStayException(final InvalidStayException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidStayInformationException.class)
    public ResponseEntity<ErrorCode> handleInvalidStayInformationException(final InvalidStayInformationException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TouristicPlaceNotFoundException.class)
    public ResponseEntity<ErrorCode> handleTouristicPlaceNotFoundException(final TouristicPlaceNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(GuaranteedServiceNotFoundException.class)
    public ResponseEntity<ErrorCode> handleGuaranteedServiceNotFoundException(final GuaranteedServiceNotFoundException ex)
    {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidTouristicPlaceException.class)
    public ResponseEntity<ErrorCode> handleInvalidTouristicPlaceException(final InvalidTouristicPlaceException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TouristicPlaceAlreadyExistsException.class)
    public ResponseEntity<ErrorCode> handleTouristicPlaceAlreadyExistsException(final TouristicPlaceAlreadyExistsException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TouristicPlaceUniqueAddressException.class)
    public ResponseEntity<ErrorCode> handleTouristicPlaceUniqueAddressException(final TouristicPlaceUniqueAddressException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidGuaranteedServiceException.class)
    public ResponseEntity<ErrorCode> handleInvalidGuaranteedServiceException(final InvalidGuaranteedServiceException ex)
    {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(GuaranteedServiceUniqueException.class)
    public ResponseEntity<ErrorCode> handleGuaranteedServiceUniqueException(final GuaranteedServiceUniqueException ex)
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
