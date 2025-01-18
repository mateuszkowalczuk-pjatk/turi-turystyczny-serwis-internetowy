package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.port.ReservationAttractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReservationAttractionRepositoryImpl implements ReservationAttractionRepository
{
    private final ReservationAttractionRepositoryDao repositoryDao;

    @Override
    public List<ReservationAttraction> findAll()
    {
        return repositoryDao.findAll().stream()
                .map(ReservationAttraction::of)
                .toList();
    }

    @Override
    public List<ReservationAttraction> findAllByReservationId(final Long reservationId)
    {
        return repositoryDao.findAllByReservationId(reservationId).stream()
                .map(ReservationAttraction::of)
                .toList();
    }

    @Override
    public ReservationAttraction findById(final Long reservationAttractionId)
    {
        return repositoryDao.findById(reservationAttractionId)
                .map(ReservationAttraction::of)
                .orElse(null);
    }

    @Override
    public Long insert(final ReservationAttraction reservationAttraction)
    {
        final var entity = ReservationAttractionEntity.of(reservationAttraction);

        return repositoryDao.saveAndFlush(entity).getReservationAttractionId();
    }

    @Override
    public void update(final Long id, final ReservationAttraction reservationAttraction)
    {
        final var reservationAttractionEntity = repositoryDao.findById(id).orElse(null);

        final var entity = ReservationAttractionEntity.of(reservationAttraction);

        Optional.ofNullable(reservationAttractionEntity).ifPresent(e -> {
            e.setReservationId(entity.getReservationId());
            e.setAttractionId(entity.getAttractionId());
            e.setDateFrom(entity.getDateFrom());
            e.setDateTo(entity.getDateTo());
            e.setHourFrom(entity.getHourFrom());
            e.setHourTo(entity.getHourTo());
            e.setPeople(entity.getPeople());
            e.setItems(entity.getItems());
            e.setMessage(entity.getMessage());
            e.setPrice(entity.getPrice());
            e.setModifyDate(entity.getModifyDate());
            e.setStatus(entity.getStatus());

            repositoryDao.saveAndFlush(reservationAttractionEntity);
        });
    }

    @Override
    public void delete(final Long id)
    {
        final var reservationAttraction = findById(id);

        repositoryDao.deleteById(reservationAttraction.getReservationAttractionId());
    }
}
