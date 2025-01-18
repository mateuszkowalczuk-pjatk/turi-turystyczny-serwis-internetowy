package com.turi.reservation.infrastructure.adapter.service;

import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.domain.model.PriceType;
import com.turi.attraction.infrastructure.adapter.interfaces.AttractionFacade;
import com.turi.infrastructure.common.EmailSender;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.infrastructure.adapter.interfaces.PaymentFacade;
import com.turi.premium.infrastructure.adapter.interfaces.PremiumFacade;
import com.turi.reservation.domain.exception.*;
import com.turi.reservation.domain.model.*;
import com.turi.reservation.domain.port.ReservationAttractionService;
import com.turi.reservation.domain.port.ReservationRepository;
import com.turi.reservation.domain.port.ReservationService;
import com.turi.stay.domain.model.StayDto;
import com.turi.stay.infrastructure.adapter.interfaces.StayFacade;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.infrastructure.adapter.interfaces.TouristicPlaceFacade;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.turi.reservation.domain.model.ReservationStatus.REALIZATION;
import static com.turi.reservation.domain.model.ReservationStatus.RESERVATION;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService
{
    private final StayFacade stayFacade;
    private final UserFacade userFacade;
    private final EmailSender emailSender;
    private final PaymentFacade paymentFacade;
    private final PremiumFacade premiumFacade;
    private final ReservationRepository repository;
    private final AttractionFacade attractionFacade;
    private final TouristicPlaceFacade touristicPlaceFacade;
    private final ReservationAttractionService reservationAttractionService;

    private List<Reservation> getAll()
    {
        return repository.findAll();
    }

    @Override
    public List<ReservationDto> getAllByAccountId(final Long accountId,
                                                  final ReservationStatus[] statuses)
    {
        return repository.findAllByAccountId(accountId).stream()
                .filter(reservation -> statuses.length == 0 || Arrays.asList(statuses).contains(reservation.getStatus()))
                .map(reservation -> {
                    final var reservationAttractions = reservationAttractionService.getAllByReservationId(reservation.getReservationId());

                    return ReservationDto.builder()
                            .withReservation(reservation)
                            .withAttractions(reservationAttractions)
                            .build();
                })
                .toList();
    }

    @Override
    public List<ReservationDto> getAllByTouristicPlaceId(final Long touristicPlaceId,
                                                         final ReservationStatus[] statuses)
    {
        return Objects.requireNonNull(stayFacade.getAllStaysByTouristicPlaceId(String.valueOf(touristicPlaceId)).getBody())
                .stream()
                .flatMap(stay -> repository.findAllByStayId(stay.getStayId()).stream())
                .filter(reservation -> statuses.length == 0 || Arrays.asList(statuses).contains(reservation.getStatus()))
                .map(reservation -> {
                    final var reservationAttractions = reservationAttractionService.getAllByReservationId(reservation.getReservationId());

                    return ReservationDto.builder()
                            .withReservation(reservation)
                            .withAttractions(reservationAttractions)
                            .build();
                })
                .toList();
    }

    private Reservation getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public ReservationDto getWithAttractionsById(final Long id)
    {
        final var reservation = getById(id);

        final var attractions = reservationAttractionService.getAllByReservationId(id);

        return ReservationDto.builder()
                .withReservation(reservation)
                .withAttractions(attractions)
                .build();
    }

    @Override
    public List<StayDto> getAllTouristicPlaceStaysAvailableInDate(final Long touristicPlaceId,
                                                                  final LocalDate dateFrom,
                                                                  final LocalDate dateTo)
    {
        return Objects.requireNonNull(stayFacade.getAllStaysByTouristicPlaceId(String.valueOf(touristicPlaceId)).getBody())
                .stream()
                .filter(stay -> isStayAvailable(stay.getStayId(), dateFrom, dateTo))
                .toList();
    }

    @Override
    public List<Attraction> getAllTouristicPlaceAttractionsAvailableInDate(final Long touristicPlaceId,
                                                                           final LocalDate dateFrom,
                                                                           final LocalDate dateTo)
    {
        return Objects.requireNonNull(attractionFacade.getAllAttractionsByTouristicPlaceId(String.valueOf(touristicPlaceId)).getBody())
                .stream()
                .filter(attraction -> isAttractionAvailable(attraction.getAttractionId(), dateFrom, dateTo, attraction.getHourFrom(), attraction.getHourTo(), true))
                .toList();
    }

    @Override
    public ReservationDto checkPayment(final Long id,
                                       final ReservationMode[] modes)
    {
        if (!paymentFacade.isPaymentForReservationSucceeded(id))
        {
            throw new ReservationUnpaidException(id);
        }

        final var reservation = getById(id);

        if (Arrays.asList(modes).contains(ReservationMode.INITIAL))
        {
            updateStatus(reservation, RESERVATION);
        }

        if (Arrays.asList(modes).contains(ReservationMode.DATE_EXTENSION))
        {
            updateStatus(reservation,
                    reservation.getStatus().equals(ReservationStatus.RESERVATION_UNPAID_DATE_EXTENSION)
                            ? RESERVATION : ReservationStatus.REALIZATION
            );
        }

        if (Arrays.asList(modes).contains(ReservationMode.INITIAL) || Arrays.asList(modes).contains(ReservationMode.ATTRACTIONS))
        {
            reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), RESERVATION);
        }

        return getWithAttractionsById(id);
    }

    private Boolean isStayAvailable(final Long stayId, final LocalDate dateFrom, final LocalDate dateTo)
    {
        final var stay = stayFacade.getStayById(String.valueOf(stayId)).getBody();

        if (stay != null && (!stay.getDateFrom().isBefore(dateFrom) || !stay.getDateTo().isAfter(dateTo)))
        {
            return false;
        }

        return repository.findAllByStayId(stayId).stream()
                .noneMatch(reservation -> reservation.getDateFrom().isBefore(dateTo) && reservation.getDateTo().isAfter(dateFrom));
    }

    private Boolean isAttractionAvailable(final Long attractionId, final LocalDate dateFrom, final LocalDate dateTo, final LocalTime hourFrom, final LocalTime hourTo, final boolean isPartial)
    {
        final var attraction = attractionFacade.getAttractionById(String.valueOf(attractionId)).getBody();

        if (attraction != null && (!attraction.getDateFrom().isBefore(dateFrom) || !attraction.getDateTo().isAfter(dateTo)
                || !attraction.getHourFrom().isBefore(hourFrom) || !attraction.getHourTo().isAfter(hourTo)))
        {
            return false;
        }

        final var minutes = reservationAttractionService.getAllByReservationId(attractionId).stream()
                .filter(reservationAttraction -> reservationAttraction.getDateFrom().isBefore(dateTo) && reservationAttraction.getDateTo().isAfter(dateFrom))
                .mapToLong(reservation -> Duration.between(
                        reservation.getHourFrom().isBefore(hourFrom) ? hourFrom : reservation.getHourFrom(),
                        reservation.getHourTo().isAfter(hourTo) ? hourTo : reservation.getHourTo()
                ).toMinutes())
                .sum();

        if (isPartial)
        {
            return minutes < Duration.between(hourFrom, hourTo).toMinutes();
        }

        return minutes == 0;
    }

    @Override
    public Reservation create(final Long stayId, final Long accountId, final LocalDate dateFrom, final LocalDate dateTo)
    {
        if (!isStayAvailable(stayId, dateFrom, dateTo))
        {
            throw new ReservationStayUnavailableException(stayId);
        }

        if (!(dateFrom.isAfter(LocalDate.now()) && dateTo.isAfter(dateFrom)))
        {
            throw new InvalidReservationDateException();
        }

        final var reservation = Reservation.builder()
                .withStayId(stayId)
                .withAccountId(accountId)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withModifyDate(LocalDateTime.now())
                .withStatus(ReservationStatus.LOCKED)
                .build();

        final var reservationId = repository.insert(reservation);

        return getById(reservationId);
    }

    @Override
    public ReservationAttraction createAttraction(final Long reservationId, final Long attractionId, final LocalDate dateFrom, final LocalDate dateTo, final LocalTime hourFrom, final LocalTime hourTo)
    {
        if (!isAttractionAvailable(attractionId, dateFrom, dateTo, hourFrom, hourTo, false))
        {
            throw new ReservationAttractionUnavailableException(attractionId);
        }

        if (!(dateFrom.isAfter(LocalDate.now()) && !dateTo.isBefore(dateFrom)))
        {
            throw new InvalidReservationDateException();
        }

        if (!(hourFrom.isAfter(LocalTime.now().plusHours(1)) && hourTo.isAfter(hourFrom.plusHours(1))))
        {
            throw new InvalidReservationTimeException();
        }

        final var reservationAttraction = ReservationAttraction.builder()
                .withReservationId(reservationId)
                .withAttractionId(attractionId)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withHourFrom(hourFrom)
                .withHourTo(hourTo)
                .withModifyDate(LocalDateTime.now())
                .withStatus(ReservationStatus.LOCKED)
                .build();

        return reservationAttractionService.create(reservationAttraction);
    }

    @Override
    public String pay(final Long id,
                      final PaymentMethod method,
                      final ReservationMode mode,
                      final LocalDate dateTo,
                      final List<ReservationAttraction> reservationAttractions)
    {
        final var reservation = getById(id);

        if (reservation.getStatus().equals(ReservationStatus.REALIZED) || reservation.getStatus().equals(ReservationStatus.CANCELED))
        {
            throw new ReservationCompletedException(reservation.getReservationId());
        }

        if (mode.equals(ReservationMode.INITIAL))
        {
            return payForReservation(reservation, method, reservationAttractions);
        }

        if (mode.equals(ReservationMode.DATE_EXTENSION))
        {
            return payForDateExtension(reservation, method, dateTo);
        }

        return payForAttractions(reservation, method, reservationAttractions);
    }

    private String payForReservation(final Reservation reservation,
                                     final PaymentMethod method,
                                     final List<ReservationAttraction> reservationAttractions)
    {
        final var price = calculatePrice(reservation, reservationAttractions, null);

        updateStatus(reservation, ReservationStatus.UNPAID);

        updatePrice(reservation, price);

        reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.UNPAID);

        updatePriceForAttractions(reservationAttractions);

        return paymentFacade.payForReservation(reservation.getReservationId(), price, method, reservationAttractions);
    }

    private String payForDateExtension(final Reservation reservation,
                                       final PaymentMethod method,
                                       final LocalDate dateTo)
    {
        final var price = calculatePrice(reservation, null, dateTo);

        if (reservation.getStatus().equals(RESERVATION))
        {
            updateStatus(reservation, ReservationStatus.RESERVATION_UNPAID_DATE_EXTENSION);
        }

        if (reservation.getStatus().equals(ReservationStatus.REALIZATION))
        {
            updateStatus(reservation, ReservationStatus.REALIZATION_UNPAID_DATE_EXTENSION);
        }

        updatePrice(reservation, reservation.getPrice() + price);

        return paymentFacade.payForReservation(reservation.getReservationId(), price, method, Collections.emptyList());
    }

    private String payForAttractions(final Reservation reservation,
                                     final PaymentMethod method,
                                     final List<ReservationAttraction> reservationAttractions)
    {
        final var price = calculatePrice(null, reservationAttractions, null);

        reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.UNPAID);

        updatePriceForAttractions(reservationAttractions);

        return paymentFacade.payForReservation(reservation.getReservationId(), price, method, reservationAttractions);
    }

    @Override
    public ReservationDto makePayOnSite(final Long id,
                                        final ReservationMode mode,
                                        final LocalDate dateTo,
                                        final List<ReservationAttraction> reservationAttractions)
    {
        final var reservation = getById(id);

        if (reservation.getStatus().equals(ReservationStatus.REALIZED) || reservation.getStatus().equals(ReservationStatus.CANCELED))
        {
            throw new ReservationCompletedException(reservation.getReservationId());
        }

        if (mode.equals(ReservationMode.INITIAL))
        {
            makePayOnSiteForReservation(reservation, reservationAttractions);
        }

        if (mode.equals(ReservationMode.DATE_EXTENSION))
        {
            makePayOnSiteForDateExtension(reservation, dateTo);
        }

        if (mode.equals(ReservationMode.ATTRACTIONS))
        {
            makePayOnSiteForAttractions(reservation, reservationAttractions);
        }

        return getWithAttractionsById(id);
    }

    private void makePayOnSiteForReservation(final Reservation reservation,
                                             final List<ReservationAttraction> reservationAttractions)
    {
        final var price = calculatePrice(reservation, reservationAttractions, null);

        updateStatus(reservation, ReservationStatus.PAY_ON_SITE);

        updatePrice(reservation, price);

        reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.PAY_ON_SITE);

        updatePriceForAttractions(reservationAttractions);
    }

    private void makePayOnSiteForDateExtension(final Reservation reservation,
                                               final LocalDate dateTo)
    {
        final var price = calculatePrice(reservation, null, dateTo);

        if (reservation.getStatus().equals(RESERVATION))
        {
            updateStatus(reservation, ReservationStatus.PAY_ON_SITE);
        }

        if (reservation.getStatus().equals(ReservationStatus.REALIZATION))
        {
            updateStatus(reservation, ReservationStatus.REALIZATION_PAY_ON_SITE_DATE_EXTENSION);
        }

        updatePrice(reservation, reservation.getPrice() + price);
    }

    private void makePayOnSiteForAttractions(final Reservation reservation,
                                             final List<ReservationAttraction> reservationAttractions)
    {
        reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.UNPAID);

        updatePriceForAttractions(reservationAttractions);
    }

    @Override
    public ReservationDto payOnSite(final Long id,
                                    final ReservationMode mode,
                                    final List<ReservationAttraction> reservationAttractions)
    {
        final var reservation = getById(id);

        if (!reservation.getStatus().equals(ReservationStatus.PAY_ON_SITE) && !reservation.getStatus().equals(ReservationStatus.REALIZATION_PAY_ON_SITE_DATE_EXTENSION))
        {
            throw new ReservationPaidOnSiteException(id);
        }

        if (mode.equals(ReservationMode.INITIAL))
        {
            updateStatus(reservation, REALIZATION);

            reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), REALIZATION);
        }

        if (mode.equals(ReservationMode.DATE_EXTENSION))
        {
            updateStatus(reservation, REALIZATION);
        }

        if (mode.equals(ReservationMode.ATTRACTIONS))
        {
            reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), RESERVATION);
        }

        return getWithAttractionsById(id);
    }

     private void updatePrice(final Reservation reservation,
                              final Double price)
     {
        reservation.setPrice(price);

        repository.update(reservation.getReservationId(), reservation);
     }

     private void updatePriceForAttractions(final List<ReservationAttraction> attractions)
     {
         attractions.forEach(reservationAttraction -> reservationAttractionService.updatePrice(
                 reservationAttraction.getReservationAttractionId(),
                 calculatePrice(null, List.of(reservationAttraction), null)
         ));
     }

    private Double calculatePrice(final Reservation reservation,
                                  final List<ReservationAttraction> reservationAttractions,
                                  final LocalDate dateTo)
    {
        var price = 0.0;

        if (reservation != null && dateTo == null)
        {
            final var stay = Objects.requireNonNull(stayFacade.getStayById(String.valueOf(reservation.getStayId())).getBody());

            final var days = ChronoUnit.DAYS.between(reservation.getDateFrom(), reservation.getDateTo());

            price += stay.getPrice().doubleValue() * days;
        }

        if (reservationAttractions != null)
        {
            price += reservationAttractions.stream()
                    .mapToDouble(reservationAttraction -> {
                        final var attraction = attractionFacade.getAttractionById(String.valueOf(reservationAttraction.getReservationId())).getBody();

                        if (attraction != null && attraction.getPriceType().equals(PriceType.HOUR))
                        {
                            final var hours = ChronoUnit.HOURS.between(reservationAttraction.getHourFrom(), reservationAttraction.getHourTo());

                            return attraction.getPrice().doubleValue() * hours;
                        }
                        else if (attraction != null && attraction.getPriceType().equals(PriceType.PERSON) && reservationAttraction.getPeople() != null)
                        {
                            return attraction.getPrice().doubleValue() * reservationAttraction.getPeople();
                        }
                        else if (attraction != null && attraction.getPriceType().equals(PriceType.ITEM) && reservationAttraction.getItems() != null)
                        {
                            return attraction.getPrice().doubleValue() * reservationAttraction.getItems();
                        }

                        return 0.0;
                    })
                    .sum();
        }

        if (reservation != null && dateTo != null)
        {
            final var stay = Objects.requireNonNull(stayFacade.getStayById(String.valueOf(reservation.getStayId())).getBody());

            final var days = ChronoUnit.DAYS.between(reservation.getDateTo(), dateTo);

            price += stay.getPrice().doubleValue() * days;
        }

        return price;
    }

    private void updateStatus(final Reservation reservation,
                              final ReservationStatus status)
    {
        reservation.setStatus(status);
        reservation.setModifyDate(LocalDateTime.now());

        repository.update(reservation.getReservationId(), reservation);
    }

    @Override
    public void createAllReservationsReminder()
    {
        getAll().forEach(reservation -> {
            final var touristicPlace = getTouristicPlaceByStayId(reservation.getStayId());
            final var stay = Objects.requireNonNull(stayFacade.getStayById(String.valueOf(reservation.getStayId())).getBody());
            final var clientEmail = userFacade.getUserEmailByUserId(reservation.getAccountId());
            final var ownerEmail = userFacade.getUserEmailByUserId(premiumFacade.getPremiumAccountId(String.valueOf(touristicPlace.getPremiumId())).getBody());
            final var reservationDate = reservation.getDateFrom().toString();

            if (reservation.getDateFrom().isBefore(LocalDate.now().plusDays(1)))
            {
                emailSender.sendEmailReminder(clientEmail,
                        "Przypomnienie o rezerwacji",
                        String.format("Przypominamy, że jutro rozpoczyna się twój pobyt w %s.", touristicPlace.getName()),
                        reservationDate);

                emailSender.sendEmailReminder(ownerEmail,
                        "Przypomnienie o rezerwacji",
                        String.format("Przypominamy, że jutro rozpoczyna się pobyt w twoim miejscu turystycznym w %s.", stay.getName()),
                        reservationDate);
            }

            if (reservation.getDateFrom().equals(LocalDate.now()) && reservation.getCheckInTime().isBefore(LocalTime.now().plusHours(1)))
            {
                emailSender.sendEmailReminder(ownerEmail,
                        "Przypomnienie o rezerwacji",
                        String.format("Przypominamy, że za godzinę rozpoczyna się pobyt w twoim miejscu turystycznym w %s.", stay.getName()),
                        reservationDate);
            }

            if (reservation.getDateTo().isBefore(LocalDate.now().plusDays(1)))
            {
                emailSender.sendEmailReminder(clientEmail,
                        "Przypomnienie o końcu pobytu",
                        String.format("Przypominamy, że jutro kończy się twój pobyt w %s.", touristicPlace.getName()),
                        reservationDate);

                emailSender.sendEmailReminder(ownerEmail,
                        "Przypomnienie o końcu pobytu",
                        String.format("Przypominamy, że jutro kończy się pobyt w twoim miejscu turystycznym w %s.", stay.getName()),
                        reservationDate);
            }

            if (reservation.getDateTo().equals(LocalDate.now()) && touristicPlace.getCheckOutTimeFrom().isBefore(LocalTime.now().plusHours(1)))
            {
                emailSender.sendEmailReminder(ownerEmail,
                        "Przypomnienie o końcu pobytu",
                        String.format("Przypominamy, że za godzinę kończy się pobyt w twoim miejscu turystycznym w %s.", stay.getName()),
                        reservationDate);
            }

            reservationAttractionService.getAllByReservationId(reservation.getReservationId()).forEach(reservationAttraction -> {
                final var attraction = attractionFacade.getAttractionById(String.valueOf(reservationAttraction.getAttractionId())).getBody();
                final var attractionDate = reservationAttraction.getDateFrom().toString() + ", o " + reservationAttraction.getHourFrom().toString();

                if (reservationAttraction.getDateFrom().isBefore(LocalDate.now().plusDays(1)))
                {
                    emailSender.sendEmailReminder(clientEmail,
                            "Przypomnienie o rezerwacji",
                            String.format("Przypominamy, że jutro rozpoczyna się twoja atrakcja %s.", Objects.requireNonNull(attraction).getName()),
                            attractionDate);

                    emailSender.sendEmailReminder(ownerEmail,
                            "Przypomnienie o rezerwacji",
                            String.format("Przypominamy, że jutro w twoim miejscu turystycznym rozpoczyna się atrakcja %s.", attraction.getName()),
                            attractionDate);
                }

                if (reservationAttraction.getDateFrom().equals(LocalDate.now()) && reservationAttraction.getHourFrom().isBefore(LocalTime.now().plusHours(1)))
                {
                    emailSender.sendEmailReminder(ownerEmail,
                            "Przypomnienie o rezerwacji",
                            String.format("Przypominamy, że za godzinę w twoim miejscu turystycznym rozpoczyna się atrakcja %s.", Objects.requireNonNull(attraction).getName()),
                            attractionDate);
                }
            });
        });
    }

    @Override
    public ReservationDto updateDetails(final Long id,
                                        final LocalTime checkInTime,
                                        final String request)
    {
        final var reservation = getById(id);

        final var touristicPlace = getTouristicPlaceByStayId(reservation.getStayId());

        if (checkInTime.isBefore(touristicPlace.getCheckInTimeFrom()) || checkInTime.isAfter(touristicPlace.getCheckInTimeTo()))
        {
            throw new BadRequestParameterException("Check in time must be in touristic place check in time range.");
        }

        reservation.setCheckInTime(checkInTime);
        reservation.setRequest(request);

        repository.update(reservation.getReservationId(), reservation);

        return getWithAttractionsById(id);
    }

    @Override
    public ReservationDto updateOpinion(final Long id,
                                        final Double rating,
                                        final String opinion)
    {
        final var reservation = getById(id);

        if (rating < 1.0 || rating > 5.0)
        {
            throw new BadRequestParameterException("Rating must be in 1.0 - 5.0 range.");
        }

        reservation.setRating(rating);
        reservation.setOpinion(opinion);

        repository.update(reservation.getReservationId(), reservation);

        return getWithAttractionsById(id);
    }

    @Override
    public ReservationDto updateDateTo(final Long id,
                                       final LocalDate dateTo)
    {
        final var reservation = getById(id);

        if (dateTo.isBefore(reservation.getDateTo()) || dateTo.isEqual(reservation.getDateTo()))
        {
            throw new BadRequestParameterException("Updated reservation date to must be after current reservation date to.");
        }

        reservation.setDateTo(dateTo);

        repository.update(reservation.getReservationId(), reservation);

        return getWithAttractionsById(id);
    }

    @Override
    public void updateAllReservationsStatuses()
    {
        getAll().forEach(reservation -> {
            final var touristicPlace = getTouristicPlaceByStayId(reservation.getStayId());

            if (reservation.getStatus().equals(ReservationStatus.UNPAID)
                    && touristicPlace != null && touristicPlace.getCancelReservationDays() != 0
                    && reservation.getDateFrom().plusDays(touristicPlace.getCancelReservationDays()).isBefore(LocalDate.now()))
            {
                updateStatus(reservation, ReservationStatus.CANCELED);
            }
            else if (reservation.getStatus().equals(ReservationStatus.PAY_ON_SITE)
                    && reservation.getDateFrom().equals(LocalDate.now())
                    && reservation.getCheckInTime().isAfter(LocalTime.now()))
            {
                updateStatus(reservation, ReservationStatus.REALIZATION_PAY_ON_SITE_DATE_EXTENSION);
            }
            else if (reservation.getStatus().equals(ReservationStatus.RESERVATION)
                    && reservation.getDateFrom().equals(LocalDate.now())
                    && reservation.getCheckInTime().isAfter(LocalTime.now()))
            {
                updateStatus(reservation, ReservationStatus.REALIZATION);
            }
            else if (reservation.getStatus().equals(ReservationStatus.REALIZATION)
                    && reservation.getDateTo().equals(LocalDate.now())
                    && reservation.getCheckInTime().isAfter(LocalTime.now()))
            {
                updateStatus(reservation, ReservationStatus.REALIZED);
            }
        });
    }

    private TouristicPlace getTouristicPlaceByStayId(final Long stayId)
    {
        return touristicPlaceFacade.getTouristicPlaceById(Objects.requireNonNull(stayFacade.getStayById(String.valueOf(stayId)).getBody()).getTouristicPlaceId());
    }

    @Override
    public void updateAllReservationsAttractionsStatuses()
    {
        reservationAttractionService.updateAllReservationsAttractionsStatuses();
    }

    @Override
    public void cancel(final Long id)
    {
        final var reservation = getById(id);

        if (reservation.getStatus().equals(ReservationStatus.REALIZED) || reservation.getStatus().equals(ReservationStatus.CANCELED))
        {
            throw new BadRequestParameterException("Reservation must not be completed.");
        }

        reservation.setStatus(ReservationStatus.CANCELED);

        repository.update(reservation.getReservationId(), reservation);
    }

    @Override
    public void cancelAttraction(final Long reservationAttractionId)
    {
        reservationAttractionService.cancel(reservationAttractionId);
    }

    @Override
    public void deleteAllExpiredLockedReservations()
    {
        getAll().stream()
                .filter(reservation -> reservation.getStatus().equals(ReservationStatus.LOCKED) && reservation.getModifyDate().plusMinutes(15).isBefore(LocalDateTime.now()))
                .forEach(reservation -> repository.delete(reservation.getReservationId()));
    }

    @Override
    public void deleteAllExpiredLockedReservationsAttractions()
    {
        reservationAttractionService.deleteAllExpiredLockedReservationsAttractions();
    }
}
