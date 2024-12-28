package com.turi.stay.infrastructure.adapter.service;

import com.turi.image.infrastructure.adapter.interfaces.ImageFacade;
import com.turi.stay.domain.model.Stay;
import com.turi.stay.domain.model.StayDto;
import com.turi.stay.domain.model.StayInformation;
import com.turi.stay.domain.port.StayInformationService;
import com.turi.stay.domain.port.StayRepository;
import com.turi.stay.domain.port.StayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StayServiceImpl implements StayService
{
    private final ImageFacade imageFacade;
    private final StayRepository repository;
    private final StayInformationService stayInformationService;

    @Override
    public List<StayDto> getAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        final var stays = repository.findAllByTouristicPlaceId(touristicPlaceId);

        return stays.stream()
                .map(this::prepareStayDto)
                .toList();
    }

    @Override
    public StayDto getById(final Long id)
    {
        final var stay = repository.findById(id);

        return prepareStayDto(stay);
    }

    private StayDto prepareStayDto(final Stay stay)
    {
        final var stayInformations = stayInformationService.getAllByStayId(stay.getStayId());

        return StayDto.builder()
                .withStayId(stay.getStayId())
                .withTouristicPlaceId(stay.getTouristicPlaceId())
                .withName(stay.getName())
                .withDescription(stay.getDescription())
                .withPrice(stay.getPrice())
                .withPeopleNumber(stay.getPeopleNumber())
                .withDateFrom(stay.getDateFrom())
                .withDateTo(stay.getDateTo())
                .withStayInformations(stayInformations)
                .build();
    }

    @Override
    public Stay create(final StayDto stayDto)
    {
        final var stay = Stay.builder()
                .withStayId(stayDto.getStayId())
                .withTouristicPlaceId(stayDto.getTouristicPlaceId())
                .withName(stayDto.getName())
                .withDescription(stayDto.getDescription())
                .withPrice(stayDto.getPrice())
                .withPeopleNumber(stayDto.getPeopleNumber())
                .withDateFrom(stayDto.getDateFrom())
                .withDateTo(stayDto.getDateTo())
                .build();

        final var stayId = repository.insert(stay);

        stayDto.getStayInformations().forEach(stayInformation -> {
            stayInformation.setStayId(stayId);

            stayInformationService.create(stayInformation);
        });

        return repository.findById(stayId);
    }

    @Override
    public void createStayInformation(final StayInformation stayInformation)
    {
        stayInformationService.create(stayInformation);
    }

    @Override
    public void update(final Long id, final Stay stay)
    {
        final var currentStay = getById(id);

        final var stayToUpdate = Stay.builder()
                .withTouristicPlaceId(currentStay.getTouristicPlaceId())
                .withName(stay.getName())
                .withDescription(stay.getDescription())
                .withPrice(stay.getPrice())
                .withPeopleNumber(stay.getPeopleNumber())
                .withDateFrom(stay.getDateFrom())
                .withDateTo(stay.getDateTo())
                .build();

        repository.update(id, stayToUpdate);
    }

    @Override
    public void delete(final Long id)
    {
        imageFacade.deleteAllImagesByStayId(id);

        stayInformationService.deleteAllByStayId(id);

        repository.delete(id);
    }

    @Override
    public void deleteStayInformation(final Long stayInformationId)
    {
        stayInformationService.deleteById(stayInformationId);
    }
}
