package com.turi.authentication.infrastructure.adpater.repository;

import com.turi.authentication.domain.exception.InvalidRefreshTokenException;
import com.turi.authentication.domain.exception.RefreshTokenNotFoundException;
import com.turi.authentication.domain.model.RefreshToken;
import com.turi.authentication.domain.port.RefreshTokenRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class RefreshTokenRepositoryTest
{
    @Autowired
    private RefreshTokenRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testRefreshToken_FindById()
    {
        final var refreshToken = mockRefreshToken();

        final var result = repository.findById(refreshToken.getRefreshTokenId());

        assertNotNull(result);
        assertThat(result).isEqualTo(refreshToken);
    }

    @Test
    void testRefreshToken_FindById_NotFound()
    {
        assertThrows(RefreshTokenNotFoundException.class, () -> repository.findById(mockNewRefreshToken().getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_FindByToken()
    {
        final var refreshToken = mockRefreshToken();

        final var result = repository.findByToken(refreshToken.getToken());

        assertNotNull(result);
        assertThat(result).isEqualTo(refreshToken);
    }

    @Test
    void testRefreshToken_FindByToken_NotFound()
    {
        assertNull(repository.findByToken(mockNewRefreshToken().getToken()));
    }

    @Test
    void testRefreshToken_Insert()
    {
        final var refreshToken = mockNewRefreshToken();

        final var refreshTokenId = repository.insert(refreshToken);

        refreshToken.setUserId(refreshTokenId);

        final var result = repository.findById(refreshTokenId);

        assertNotNull(result);
        assertThat(result).isEqualTo(refreshToken);
    }

    @Test
    void testRefreshToken_Insert_WithoutRequiredUserIdField()
    {
        final var refreshToken = mockNewRefreshToken();

        refreshToken.setUserId(null);

        assertThrows(InvalidRefreshTokenException.class, () -> repository.insert(refreshToken));
    }

    @Test
    void testRefreshToken_Insert_WithoutRequiredTokenField()
    {
        final var refreshToken = mockNewRefreshToken();

        refreshToken.setToken(null);

        assertThrows(InvalidRefreshTokenException.class, () -> repository.insert(refreshToken));
    }

    @Test
    void testRefreshToken_Insert_WithoutRequiredExpiresAtField()
    {
        final var refreshToken = mockNewRefreshToken();

        refreshToken.setExpiresAt(null);

        assertThrows(InvalidRefreshTokenException.class, () -> repository.insert(refreshToken));
    }

    @Test
    void testRefreshToken_DeleteById()
    {
        final var refreshToken = mockRefreshToken();

        repository.deleteById(refreshToken.getRefreshTokenId());

        assertThrows(RefreshTokenNotFoundException.class, () -> repository.findById(refreshToken.getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_DeleteById_NothingToDelete()
    {
        assertThrows(RefreshTokenNotFoundException.class, () -> repository.deleteById(mockNewRefreshToken().getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_DeleteByToken()
    {
        final var refreshToken = mockRefreshToken();

        repository.deleteByToken(refreshToken.getToken());

        assertThrows(RefreshTokenNotFoundException.class, () -> repository.findById(refreshToken.getRefreshTokenId()));
    }

    @Test
    void testRefreshToken_DeleteByToken_NothingToDelete()
    {
        final var refreshToken = mockNewRefreshToken();

        assertNull(repository.findByToken(refreshToken.getToken()));

        repository.deleteByToken(refreshToken.getToken());
    }

    @Test
    void testRefreshToken_DeleteAllExpiredRefreshTokens()
    {
        final var mock = repository.insert(mockNewRefreshToken());

        final var refreshTokens = List.of(mockRefreshToken(), repository.findById(mock));

        repository.deleteAllExpiredRefreshTokens();

        refreshTokens.forEach(refreshToken -> assertThrows(RefreshTokenNotFoundException.class, () -> repository.findById(refreshToken.getRefreshTokenId())));
    }

    @Test
    void testRefreshToken_DeleteAllExpiredRefreshTokens_NothingToDelete()
    {
        final var mock = mockNewRefreshToken();
        mock.setExpiresAt(LocalDateTime.now().plusMinutes(1));

        final var refreshTokenId = repository.insert(mock);

        repository.deleteAllExpiredRefreshTokens();

        final var result = repository.findById(refreshTokenId);

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
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123!")
                .build();

        final var userId = userRepository.insert(user);

        return RefreshToken.builder()
                .withRefreshTokenId(2L)
                .withUserId(userId)
                .withToken("other-refresh-token")
                .withExpiresAt(LocalDateTime.of(2024, 2, 2, 0, 0, 0))
                .build();
    }
}
