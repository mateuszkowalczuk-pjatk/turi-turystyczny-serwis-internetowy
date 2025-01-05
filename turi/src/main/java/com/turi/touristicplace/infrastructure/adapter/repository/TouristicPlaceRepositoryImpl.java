package com.turi.touristicplace.infrastructure.adapter.repository;

import com.turi.touristicplace.domain.exception.TouristicPlaceNotFoundException;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.port.TouristicPlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TouristicPlaceRepositoryImpl implements TouristicPlaceRepository
{
    private final TouristicPlaceRepositoryDao repositoryDao;

    public List<Object[]> findForSearch(final String query,
                                        final LocalDate dateFrom,
                                        final LocalDate dateTo,
                                        final Long limit,
                                        final Long touristicPlaceId,
                                        final Double rank)
    {
        return prepareSearchResult(repositoryDao.findForSearch(query, getDate(dateFrom), getDate(dateTo), limit, touristicPlaceId, rank));
    }

    @Override
    public List<Object[]> findByStaysForSearch(final String query,
                                               final LocalDate dateFrom,
                                               final LocalDate dateTo,
                                               final Integer touristicPlaceType,
                                               final Long limit,
                                               final Long touristicPlaceId,
                                               final Double rank)
    {
        return prepareSearchResult(repositoryDao.findByStaysForSearch(query, getDate(dateFrom), getDate(dateTo), touristicPlaceType, limit, touristicPlaceId, rank));
    }

    @Override
    public List<Object[]> findByAttractionsForSearch(final String query,
                                                     final LocalDate dateFrom,
                                                     final LocalDate dateTo,
                                                     final Integer attractionType,
                                                     final Long limit,
                                                     final Long touristicPlaceId,
                                                     final Double rank)
    {
        return prepareSearchResult(repositoryDao.findByAttractionsForSearch(query, getDate(dateFrom), getDate(dateTo), attractionType, limit, touristicPlaceId, rank));
    }

    private String getDate(final LocalDate date)
    {
        return date == null ? null : String.valueOf(date);
    }

    private List<Object[]> prepareSearchResult(final List<Object[]> result)
    {
        return result.stream()
                .map(searchResult -> new Object[]{findById(Long.parseLong(String.valueOf(searchResult[0]))), searchResult[1]})
                .toList();
    }

    @Override
    public List<String> findForAutocomplete(final String query)
    {
        return repositoryDao.findForAutocomplete(query);
    }

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
