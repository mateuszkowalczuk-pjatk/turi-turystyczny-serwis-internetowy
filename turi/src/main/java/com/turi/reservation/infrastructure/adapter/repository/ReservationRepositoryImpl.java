package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.exception.ReservationNotFoundException;
import com.turi.reservation.domain.model.Reservation;
import com.turi.reservation.domain.port.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository
{
    private final ReservationRepositoryDao repositoryDao;

    @Override
    public List<Reservation> findAll()
    {
        return repositoryDao.findAll().stream()
                .map(Reservation::of)
                .toList();
    }

    @Override
    public List<Reservation> findAllByStayId(final Long stayId)
    {
        return repositoryDao.findAllByStayId(stayId).stream()
                .map(Reservation::of)
                .toList();
    }

    @Override
    public List<Reservation> findAllByAccountId(final Long accountId)
    {
        return repositoryDao.findAllByAccountId(accountId).stream()
                .map(Reservation::of)
                .toList();
    }

    @Override
    public Reservation findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Reservation::of)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @Override
    public Long insert(final Reservation reservation)
    {
        final var entity = ReservationEntity.of(reservation);

        return repositoryDao.saveAndFlush(entity).getReservationId();
    }

    @Override
    public void update(final Long id, final Reservation reservation)
    {
        final var reservationEntity = repositoryDao.findById(id).orElse(null);

        final var entity = ReservationEntity.of(reservation);

        Optional.ofNullable(reservationEntity).ifPresent(e -> {
            e.setStayId(e.getStayId());
            e.setAccountId(e.getAccountId());
            e.setDateFrom(entity.getDateFrom());
            e.setDateTo(entity.getDateTo());
            e.setPrice(entity.getPrice());
            e.setCheckInTime(entity.getCheckInTime());
            e.setRequest(entity.getRequest());
            e.setRating(entity.getRating());
            e.setOpinion(entity.getOpinion());
            e.setModifyDate(entity.getModifyDate());
            e.setStatus(entity.getStatus());

            repositoryDao.saveAndFlush(reservationEntity);
        });
    }

    @Override
    public void delete(final Long id)
    {
        final var reservation = findById(id);

        repositoryDao.deleteById(reservation.getReservationId());
    }
}
