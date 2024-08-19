package com.turi.address.domain.model;

import com.turi.address.infrastructure.adapter.repository.AddressEntity;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Address
{
    private Long addressId;
    private String country;
    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;
    private Integer apartmentNumber;

    public static Address of(final AddressEntity entity)
    {
        return Address.builder()
                .withAddressId(entity.getAddressId())
                .withCountry(entity.getCountry())
                .withCity(entity.getCity())
                .withZipCode(entity.getZipCode())
                .withStreet(entity.getStreet())
                .withBuildingNumber(entity.getBuildingNumber())
                .withApartmentNumber(entity.getApartmentNumber())
                .build();
    }
}
