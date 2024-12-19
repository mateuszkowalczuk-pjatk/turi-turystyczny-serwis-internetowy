package com.turi.stay.infrastructure.adapter.service;

import com.turi.stay.domain.model.StayInformation;
import com.turi.stay.domain.port.StayInformationRepository;
import com.turi.stay.domain.port.StayInformationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StayInformationServiceImpl implements StayInformationService
{
    private final StayInformationRepository repository;

    @Override
    public List<StayInformation> getAllByStayId(final Long stayId)
    {
        return repository.findAllByStayId(stayId);
    }

    @Override
    public void create(final StayInformation stayInformation)
    {
        repository.insert(stayInformation);
    }

    @Override
    public void deleteById(final Long id)
    {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByStayId(final Long stayId)
    {
        repository.deleteAllByStayId(stayId);
    }
}
