package com.turi.attraction.infrastructure.adapter.repository;

import com.turi.attraction.domain.exception.AttractionNotFoundException;
import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.domain.port.AttractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AttractionRepositoryImpl implements AttractionRepository
{
    private final AttractionRepositoryDao repositoryDao;

    @Override
    public List<Attraction> findAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repositoryDao.findAllByTouristicPlaceId(touristicPlaceId).stream()
                .map(Attraction::of)
                .toList();
    }

    @Override
    public Attraction findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Attraction::of)
                .orElseThrow(() -> new AttractionNotFoundException(id));
    }

    @Override
    public Long insert(final Attraction attraction)
    {
        final var entity = AttractionEntity.of(attraction);

        return repositoryDao.saveAndFlush(entity).getAttractionId();
    }

    @Override
    public void update(final Long id, final Attraction attraction)
    {
        final var attractionEntity = repositoryDao.findById(id).orElse(null);

        final var entity = AttractionEntity.of(attraction);

        Optional.ofNullable(attractionEntity).ifPresent(e -> {
            e.setTouristicPlaceId(entity.getTouristicPlaceId());
            e.setAttractionType(entity.getAttractionType());
            e.setName(entity.getName());
            e.setDescription(entity.getDescription());
            e.setPrice(entity.getPrice());
            e.setPriceType(entity.getPriceType());
            e.setPrepayment(entity.getPrepayment());
            e.setCancelReservationDays(entity.getCancelReservationDays());
            e.setMaxPeopleNumber(entity.getMaxPeopleNumber());
            e.setMaxItems(entity.getMaxItems());
            e.setDateFrom(entity.getDateFrom());
            e.setDateTo(entity.getDateTo());
            e.setHourFrom(entity.getHourFrom());
            e.setHourTo(entity.getHourTo());
            e.setDaysReservationBefore(entity.getDaysReservationBefore());

            repositoryDao.saveAndFlush(attractionEntity);
        });
    }

    @Override
    public void delete(final Long id)
    {
        final var attraction = findById(id);

        repositoryDao.deleteById(attraction.getAttractionId());
    }
}
