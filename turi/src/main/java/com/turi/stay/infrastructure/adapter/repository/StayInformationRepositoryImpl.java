package com.turi.stay.infrastructure.adapter.repository;

import com.turi.stay.domain.model.StayInformation;
import com.turi.stay.domain.port.StayInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class StayInformationRepositoryImpl implements StayInformationRepository
{
    private StayInformationRepositoryDao repositoryDao;

    @Override
    public StayInformation findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(StayInformation::of)
                .orElse(null);
    }

    @Override
    public List<StayInformation> findAllByStayId(final Long stayId)
    {
        return repositoryDao.findAllByStayId(stayId).stream()
                .map(StayInformation::of)
                .toList();
    }

    @Override
    public void insert(final StayInformation stayInformation)
    {
        final var entity = StayInformationEntity.of(stayInformation);

        repositoryDao.saveAndFlush(entity);
    }

    @Override
    public void deleteById(final Long id)
    {
        final var stayInformation = findById(id);

        repositoryDao.deleteById(stayInformation.getStayInformationId());
    }

    @Override
    public void deleteAllByStayId(final Long stayId)
    {
        findAllByStayId(stayId).forEach(stayInformation -> deleteById(stayInformation.getStayInformationId()));
    }
}
