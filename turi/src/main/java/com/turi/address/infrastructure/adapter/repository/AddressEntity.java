package com.turi.address.infrastructure.adapter.repository;

import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@Builder(setterPrefix = "with")
public final class AddressEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -6815925214521941740L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressid")
    private Long addressId;

    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Column(name = "zipcode", length = 6, nullable = false)
    private String zipCode;

    @Column(name = "street", length = 50, nullable = false)
    private String street;

    @Column(name = "buildingnumber", length = 5, nullable = false)
    private String buildingNumber;

    @Column(name = "apartmentnumber")
    private Integer apartmentNumber;

    public static AddressEntity of(final Address address)
    {
        if (!validation(address))
        {
            throw new InvalidAddressException();
        }

        return AddressEntity.builder()
                .withCountry(address.getCountry())
                .withCity(address.getCity())
                .withZipCode(address.getZipCode())
                .withStreet(address.getStreet())
                .withBuildingNumber(address.getBuildingNumber())
                .withApartmentNumber(address.getApartmentNumber())
                .build();
    }

    private static boolean validation(final Address address)
    {
        return address.getCountry() != null
                && address.getCity() != null
                && address.getZipCode() != null
                && address.getStreet() != null
                && address.getBuildingNumber() != null;
    }
}
