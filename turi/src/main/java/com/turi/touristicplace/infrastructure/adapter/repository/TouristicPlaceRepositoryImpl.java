package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.exception.TouristicPlaceNotFoundException;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.port.TouristicPlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class TouristicPlaceRepositoryImpl implements TouristicPlaceRepository
{
    private final TouristicPlaceRepositoryDao repositoryDao;

    @Override
    public TouristicPlace findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(TouristicPlace::of)
                .orElseThrow(() -> new TouristicPlaceNotFoundException(id));
    }

    @Override
    public TouristicPlace findByPremiumId(final Long premiumId)
    {
        return repositoryDao.findByPremiumId(premiumId)
                .map(TouristicPlace::of)
                .orElse(null);
    }

    @Override
    public TouristicPlace findByAddressId(final Long addressId)
    {
        return repositoryDao.findByAddressId(addressId)
                .map(TouristicPlace::of)
                .orElse(null);
    }

    @Override
    public void insert(final TouristicPlace touristicPlace)
    {
        final var entity = TouristicPlaceEntity.of(touristicPlace);

        repositoryDao.saveAndFlush(entity);
    }

    @Override
    public void update(final Long id, final TouristicPlace touristicPlace)
    {
        final var touristicPlaceEntity = repositoryDao.findById(id).orElse(null);

        final var entity = TouristicPlaceEntity.of(touristicPlace);

        Optional.ofNullable(touristicPlaceEntity).ifPresent(e -> {
            e.setPremiumId(entity.getPremiumId());
            e.setAddressId(entity.getAddressId());
            e.setTouristicPlaceType(entity.getTouristicPlaceType());
            e.setName(entity.getName());
            e.setDescription(entity.getDescription());
            e.setInformation(entity.getInformation());
            e.setOwnerDescription(entity.getOwnerDescription());
            e.setCheckInTimeFrom(entity.getCheckInTimeFrom());
            e.setCheckInTimeTo(entity.getCheckInTimeTo());
            e.setCheckOutTimeFrom(entity.getCheckOutTimeFrom());
            e.setCheckOutTimeTo(entity.getCheckOutTimeTo());
            e.setPrepayment(entity.isPrepayment());
            e.setCancelReservationDays(entity.getCancelReservationDays());

            repositoryDao.saveAndFlush(touristicPlaceEntity);
        });
    }
}
