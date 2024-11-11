package com.turi.authentication.infrastructure.adpater.service;

import com.turi.authentication.domain.exception.InvalidRefreshTokenException;
import com.turi.authentication.domain.exception.RefreshTokenExpiredException;
import com.turi.authentication.domain.exception.RefreshTokenNotFoundException;
import com.turi.authentication.domain.model.RefreshToken;
import com.turi.authentication.domain.port.RefreshTokenService;
import com.turi.infrastructure.config.SecurityProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class RefreshTokenServiceTest
{
    @Autowired
    private RefreshTokenService service;

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private UserFacade userFacade;

    @Test
    void testRefreshToken_GenerateRefreshToken()
    {
        final var refreshToken = mockRefreshToken();

        final var result = service.generateRefreshToken(refreshToken.getUserId());

        assertNotNull(result);
    }

    @Test
    void testRefreshToken_GenerateRefreshToken_UserIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.generateRefreshToken(null));
    }

    @Test
    void testRefreshToken_GetById()
    {
        final var refreshToken = mockRefreshToken();

        final var result = service.getById(refreshToken.getRefreshTokenId());

        assertNotNull(result);
        assertThat(result).isEqualTo(refreshToken);
    }

    @Test
    void testRefreshToken_GetById_NotFound()
    {
        assertThrows(RefreshTokenNotFoundException.class, () -> service.getById(mockNewRefreshToken().getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_GetById_RefreshTokenIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getById(null));
    }

    @Test
    void testRefreshToken_GetByToken()
    {
        final var refreshToken = mockRefreshToken();

        final var result = service.getByToken(refreshToken.getToken());

        assertNotNull(result);
        assertThat(result).isEqualTo(refreshToken);
    }

    @Test
    void testRefreshToken_GetByToken_NotFound()
    {
        assertNull(service.getByToken(mockNewRefreshToken().getToken()));
    }

    @Test
    void testRefreshToken_GetByToken_TokenIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getByToken(null));
    }

    @Test
    void testRefreshToken_IsRefreshTokenExpired()
    {
        final var result = service.isRefreshTokenExpired(mockRefreshToken());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testRefreshToken_IsRefreshTokenExpired_NotExpired()
    {
        final var refreshToken = mockRefreshToken();

        refreshToken.setExpiresAt(LocalDateTime.now().plusMinutes(1));

        final var result = service.isRefreshTokenExpired(refreshToken);

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testRefreshToken_IsRefreshTokenExpired_RefreshTokenIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.isRefreshTokenExpired(null));
    }

    @Test
    void testRefreshToken_Create()
    {
        final var refreshToken = mockNewRefreshToken();

        final var refreshTokenId = service.create(refreshToken).getRefreshTokenId();

        refreshToken.setUserId(refreshTokenId);

        final var result = service.getById(refreshTokenId);

        assertNotNull(result);
        assertThat(result).isEqualTo(refreshToken);
    }

    @Test
    void testRefreshToken_Create_RefreshTokenIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.create(null));
    }

    @Test
    void testRefreshToken_Create_WithoutRequiredUserIdField()
    {
        final var refreshToken = mockNewRefreshToken();

        refreshToken.setUserId(null);

        assertThrows(InvalidRefreshTokenException.class, () -> service.create(refreshToken));
    }

    @Test
    void testRefreshToken_Create_WithoutRequiredTokenField()
    {
        final var refreshToken = mockNewRefreshToken();

        refreshToken.setToken(null);

        assertThrows(InvalidRefreshTokenException.class, () -> service.create(refreshToken));
    }

    @Test
    void testRefreshToken_Create_WithoutRequiredExpiresAtField()
    {
        final var refreshToken = mockNewRefreshToken();

        refreshToken.setExpiresAt(null);

        assertThrows(InvalidRefreshTokenException.class, () -> service.create(refreshToken));
    }

    @Test
    void testRefreshToken_DeleteById()
    {
        final var refreshToken = mockRefreshToken();

        service.deleteById(refreshToken.getRefreshTokenId());

        assertThrows(RefreshTokenNotFoundException.class, () -> service.getById(refreshToken.getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_DeleteById_NothingToDelete()
    {
        assertThrows(RefreshTokenNotFoundException.class, () -> service.deleteById(mockNewRefreshToken().getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_DeleteById_RefreshTokenIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.deleteById(null));
    }

    @Test
    void testRefreshToken_DeleteByToken()
    {
        final var refreshToken = mockNewRefreshToken();

        final var token = properties.getSecretKey() + "-" + refreshToken.getUserId() + "-" + UUID.randomUUID();
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(LocalDateTime.now().plusMinutes(1));

        final var result = service.create(refreshToken);

        service.deleteByToken(token);

        assertThrows(RefreshTokenNotFoundException.class, () -> service.getById(result.getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_DeleteByToken_NothingToDelete()
    {
        assertThrows(RefreshTokenExpiredException.class, () -> service.deleteByToken(mockNewRefreshToken().getToken()));
    }

    @Test
    void testRefreshToken_DeleteByToken_TokenIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.deleteByToken(null));
    }

    @Test
    void testRefreshToken_DeleteAllExpiredRefreshTokens()
    {
        final var mock = service.create(mockNewRefreshToken());

        final var refreshTokens = List.of(mockRefreshToken(), mock);

        service.deleteAllExpiredRefreshTokens();

        refreshTokens.forEach(refreshToken -> assertThrows(RefreshTokenNotFoundException.class, () -> service.getById(refreshToken.getRefreshTokenId())));
    }

    @Test
    void testRefreshToken_DeleteAllExpiredRefreshTokens_NothingToDelete()
    {
        final var mock = mockNewRefreshToken();
        mock.setExpiresAt(LocalDateTime.now().plusMinutes(1));

        final var refreshToken = service.create(mock);

        service.deleteAllExpiredRefreshTokens();

        final var result = service.getById(refreshToken.getRefreshTokenId());

        assertNotNull(result);
    }

    private RefreshToken mockRefreshToken()
    {
        return RefreshToken.builder()
                .withRefreshTokenId(1L)
                .withUserId(1L)
                .withToken("sample-refresh-token")
                .withExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();
    }

    private RefreshToken mockNewRefreshToken()
    {
        final var user = User.builder()
                .withUsername("Marek")
                .withEmail("marek@turi.com")
                .withPassword("MarekNowak123!")
                .build();

        final var userId = userFacade.createUser(user).getUserId();

        return RefreshToken.builder()
                .withRefreshTokenId(2L)
                .withUserId(userId)
                .withToken("other-refresh-token")
                .withExpiresAt(LocalDateTime.of(2024, 2, 2, 0, 0, 0))
                .build();
    }
}
