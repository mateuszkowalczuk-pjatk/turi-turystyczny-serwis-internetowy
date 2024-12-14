package com.turi.premium.infrastructure.adapter.service;

import com.turi.infrastructure.properties.CeidgProperties;
import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.port.CeidgService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
@AllArgsConstructor
public class CeidgServiceImpl implements CeidgService
{
    private final RestTemplate restTemplate;
    private final CeidgProperties properties;

    @Override
    public boolean verifyCompany(final PremiumCompanyParam params)
    {
        try
        {
            final var uri = fromHttpUrl(properties.getUrl())
                    .queryParam("nip", params.getNip())
                    .build().toUri();

            final var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + properties.getApiKey());

            return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class)
                    .getStatusCode()
                    .is2xxSuccessful();
        }
        catch (final Exception ex)
        {
            return false;
        }
    }
}
