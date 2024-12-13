package com.turi.user.domain.port;

import com.turi.user.domain.model.RefreshToken;
import com.turi.user.domain.model.ResetToken;
import com.turi.user.domain.model.User;

public interface UserService
{
    User getById(final Long id);

    User getByUsername(final String username);

    User getByEmail(final String email);

    String getUsername(final Long id);

    String getEmail(final Long id);

    User getByPasswordResetToken(final String resetToken);

    Long getUserIdByLogin(final String login);

    Boolean isUsernameExists(final String username);

    Boolean isEmailExists(final String email);

    ResetToken sendResetPasswordCode(final String email);

    RefreshToken resetPassword(final String resetToken, final Integer code);

    User create(final User user);

    void changeUsername(final Long id, final String username);

    void changeEmail(final Long id, final String email);

    void changePassword(final Long id, final String password);

    void deleteAllExpiredPasswordResetDetails();
}
