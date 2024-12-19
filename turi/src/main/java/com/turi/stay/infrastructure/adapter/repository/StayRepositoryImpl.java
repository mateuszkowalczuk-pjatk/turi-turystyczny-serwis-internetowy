package com.turi.stay.infrastructure.adapter.repository;

import com.turi.stay.domain.exception.StayNotFoundException;
import com.turi.stay.domain.model.Stay;
import com.turi.stay.domain.port.StayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class StayRepositoryImpl implements StayRepository
{
    private final StayRepositoryDao repositoryDao;

    @Override
    public List<Stay> findAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repositoryDao.findAllByTouristicPlaceId(touristicPlaceId).stream()
                .map(Stay::of)
                .toList();
    }

    @Override
    public Stay findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Stay::of)
                .orElseThrow(() -> new StayNotFoundException(id));
    }

    @Override
    public void insert(final Stay stay)
    {
        final var entity = StayEntity.of(stay);

        repositoryDao.saveAndFlush(entity);
    }

    @Override
    public void update(final Long id, final Stay stay)
    {
        final var stayEntity = repositoryDao.findById(id).orElse(null);

        final var entity = StayEntity.of(stay);

        Optional.ofNullable(stayEntity).ifPresent(e -> {
            e.setTouristicPlaceId(entity.getTouristicPlaceId());
            e.setName(entity.getName());
            e.setDescription(entity.getDescription());
            e.setPrice(entity.getPrice());
            e.setPeopleNumber(entity.getPeopleNumber());
            e.setDateFrom(entity.getDateFrom());
            e.setDateTo(entity.getDateTo());

            repositoryDao.saveAndFlush(stayEntity);
        });
    }

    @Override
    public void delete(final Long id)
    {
        final var stay = findById(id);

        repositoryDao.deleteById(stay.getStayId());
    }
}
