package com.turi.offer.infrastructure.adapter.repository;

import com.turi.offer.domain.model.Favourite;
import com.turi.offer.domain.port.FavouriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FavouriteRepositoryImpl implements FavouriteRepository
{
    private final FavouriteRepositoryDao repositoryDao;

    @Override
    public List<Favourite> findAllByAccount(final Long accountId)
    {
        final var result = repositoryDao.findAllByAccountId(accountId);

        return result.stream()
                .map(Favourite::of)
                .toList();
    }

    @Override
    public Favourite findByAccountIdAndTouristicPlaceId(final Long accountId, final Long touristicPlaceId)
    {
        return repositoryDao.findByAccountIdAndTouristicPlaceId(accountId, touristicPlaceId)
                .map(Favourite::of)
                .orElse(null);
    }

    @Override
    public void insertForAccount(final Long accountId, final Long touristicPlaceId)
    {
        final var favourite = Favourite.builder()
                .withAccountId(accountId)
                .withTouristicPlaceId(touristicPlaceId)
                .build();

        final var entity = FavouriteEntity.of(favourite);

        repositoryDao.saveAndFlush(entity);
    }

    @Override
    public void deleteForAccount(final Long accountId, final Long touristicPlaceId)
    {
        if (findByAccountIdAndTouristicPlaceId(accountId, touristicPlaceId) != null)
        {
            repositoryDao.deleteById(FavouriteId.builder()
                    .withAccountId(accountId)
                    .withTouristicPlaceId(touristicPlaceId)
                    .build());
        }
    }
}
