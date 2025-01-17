package com.turi.reservation.infrastructure.adapter.service;

import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.infrastructure.adapter.interfaces.AttractionFacade;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.infrastructure.adapter.interfaces.PaymentFacade;
import com.turi.reservation.domain.exception.*;
import com.turi.reservation.domain.model.*;
import com.turi.reservation.domain.port.ReservationAttractionService;
import com.turi.reservation.domain.port.ReservationRepository;
import com.turi.reservation.domain.port.ReservationService;
import com.turi.stay.domain.model.StayDto;
import com.turi.stay.infrastructure.adapter.interfaces.StayFacade;
import com.turi.touristicplace.infrastructure.adapter.interfaces.TouristicPlaceFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService
{
    private final StayFacade stayFacade;
    private final PaymentFacade paymentFacade;
    private final ReservationRepository repository;
    private final AttractionFacade attractionFacade;
    private final TouristicPlaceFacade touristicPlaceFacade;
    private final ReservationAttractionService reservationAttractionService;

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
            updateStatus(reservation, ReservationStatus.RESERVATION);
        }

        if (Arrays.asList(modes).contains(ReservationMode.DATE_EXTENSION))
        {
            updateStatus(reservation,
                    reservation.getStatus().equals(ReservationStatus.RESERVATION_UNPAID_DATE_EXTENSION)
                            ? ReservationStatus.RESERVATION : ReservationStatus.REALIZATION
            );
        }

        if (Arrays.asList(modes).contains(ReservationMode.INITIAL) || Arrays.asList(modes).contains(ReservationMode.ATTRACTIONS))
        {
            reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.RESERVATION);
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
            throw new StayUnavailableException(stayId);
        }

        if (!(dateFrom.isAfter(LocalDate.now()) && dateTo.isAfter(dateFrom)))
        {
            throw new InvalidDateException();
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
            throw new AttractionUnavailableException(attractionId);
        }

        if (!(dateFrom.isAfter(LocalDate.now()) && !dateTo.isBefore(dateFrom)))
        {
            throw new InvalidDateException();
        }

        if (!(hourFrom.isAfter(LocalTime.now().plusHours(1)) && hourTo.isAfter(hourFrom.plusHours(1))))
        {
            throw new InvalidTimeException();
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
        // ToDo - nie jest updatowana cena | obliczanie oddzielnie dla reservation attraction i updatowanie dla niego

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

        reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.UNPAID);

        return paymentFacade.payForReservation(reservation.getReservationId(), price, method, reservationAttractions);
    }

    private String payForDateExtension(final Reservation reservation,
                                       final PaymentMethod method,
                                       final LocalDate dateTo)
    {
        final var price = calculatePrice(reservation, null, dateTo);

        if (reservation.getStatus().equals(ReservationStatus.RESERVATION))
        {
            updateStatus(reservation, ReservationStatus.RESERVATION_UNPAID_DATE_EXTENSION);
        }

        if (reservation.getStatus().equals(ReservationStatus.REALIZATION))
        {
            updateStatus(reservation, ReservationStatus.REALIZATION_UNPAID_DATE_EXTENSION);
        }

        return paymentFacade.payForReservation(reservation.getReservationId(), price, method, Collections.emptyList());
    }

    private String payForAttractions(final Reservation reservation,
                                     final PaymentMethod method,
                                     final List<ReservationAttraction> reservationAttractions)
    {
        final var price = calculatePrice(null, reservationAttractions, null);

        reservationAttractionService.updateAllStatusesByReservationId(reservation.getReservationId(), ReservationStatus.UNPAID);

        return paymentFacade.payForReservation(reservation.getReservationId(), price, method, reservationAttractions);
    }

    @Override
    public ReservationDto makePayOnSite(final Long id,
                                        final ReservationMode mode,
                                        final List<ReservationAttraction> reservationAttractions)
    {
        // ToDo - obliczenie i update ceny - to samo co w zwykłym pay

        // ustaiwnie statusu paid_on_site

        return null;
    }

    @Override
    public ReservationDto payOnSite(final Long id,
                                    final ReservationMode mode,
                                    final List<ReservationAttraction> reservationAttractions)
    {
        // według mode - zmiana statusu

        return null;
    }

    // private x updatePrice - ToDo - nowa metoda

    private Double calculatePrice(final Reservation reservation,
                                  final List<ReservationAttraction> reservationAttractions,
                                  final LocalDate dateTo)
    {
        // jak za date extension to tez musi brac pod uwage reservation zeby pobrac cene za noc i aktualne date to zeby obliczyc roznice

        // w ten metodzie tez update ceny w reservation / reservationAttraction
        var price = 0.0;

        if (reservation != null)
        {
            final var touristicPlaceId = Objects.requireNonNull(stayFacade.getStayById(String.valueOf(reservation.getStayId())).getBody()).getTouristicPlaceId();

            final var touristicPlace = touristicPlaceFacade.getTouristicPlaceById(touristicPlaceId);
            // pobranie liczby nocy
            // przemnozenie przez cene za noc
            // ToDo - logika obliczania ceny pobytu na podstawie ceny za noc
        }

        if (reservationAttractions != null)
        {
            // pobranie ceny atrakcji per item/hour/person i na podstaiwe typuprice atrakcji okreslenie
            // ToDo - logika obliczania ceny za dodane atrakcje
        }

        if (dateTo != null)
        {
            final var touristicPlaceId = Objects.requireNonNull(stayFacade.getStayById(String.valueOf(reservation.getStayId())).getBody()).getTouristicPlaceId();

            final var touristicPlace = touristicPlaceFacade.getTouristicPlaceById(touristicPlaceId);

            //dodanie do aktualej ceny liczby dodanych nocy * cena za noc TP
            // ToDo - logika obliczania ceny pobytu na podstawie różnicy pomiędzy aktualną datą pobytu do i nową datą pobytu do
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
        // ToDo
    }

    @Override
    public ReservationDto updateDetails(final Long id,
                                        final LocalTime checkInTime,
                                        final String request)
    {
        final var reservation = getById(id);

//        if (true)
//        {
//            // sprawdzenie czy checkintime jest w przedziałach
//        }

        reservation.setCheckInTime(checkInTime);
        reservation.setRequest(request);

        repository.update(reservation.getReservationId(), reservation);

        return null;
    }

    @Override
    public ReservationDto updateOpinion(final Long id,
                                        final Double rating,
                                        final String opinion)
    {
        final var reservation = getById(id);

//        if ()
//        {
//            // sprawdzenie czy rating jest w przedziałach
//        }

        reservation.setRating(rating);
        reservation.setOpinion(opinion);

        repository.update(reservation.getReservationId(), reservation);

        return null;
    }

    @Override
    public ReservationDto updateDateTo(final Long id,
                                       final LocalDate dateTo)
    {
        return null;
    }

    @Override
    public void updateAllReservationsStatuses()
    {
        // ToDo
    }

    @Override
    public void updateAllReservationsAttractionsStatuses()
    {
        // ToDo
    }

    @Override
    public void cancel(final Long id)
    {
        // ToDo
    }

    @Override
    public void cancelAttraction(final Long reservationAttractionId)
    {
        // ToDo
    }

    @Override
    public void deleteAllExpiredLockedReservations()
    {
        // ToDo
    }

    @Override
    public void deleteAllExpiredLockedReservationsAttractions()
    {
        // ToDo
    }
}
