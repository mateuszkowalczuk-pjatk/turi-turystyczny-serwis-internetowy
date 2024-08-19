package com.turi.user.infrastructure.adapter.service;

import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.user.domain.exception.UserNotFoundByLoginException;
import com.turi.user.domain.exception.UserUniqueEmailException;
import com.turi.user.domain.exception.UserUniqueUsernameException;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import com.turi.user.domain.port.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService
{
    private final AccountFacade accountFacade;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getById(final Long id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("User ID must not be null.");
        }

        return userRepository.findById(id);
    }

    @Override
    public User getByUsername(final String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean isUsernameExists(final String username)
    {
        return getByUsername(username) != null;
    }

    @Override
    public User getByEmail(final String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean isEmailExists(final String email)
    {
        return getByEmail(email) != null;
    }

    @Override
    public User createUser(final User user)
    {
        if (isUsernameExists(user.getUsername()))
        {
            throw new UserUniqueUsernameException(user.getUsername());
        }

        if (isEmailExists(user.getEmail()))
        {
            throw new UserUniqueEmailException(user.getEmail());
        }

        if (user.getPassword() == null)
        {
            throw new BadRequestParameterException("User password must not be null.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final var userId = userRepository.insert(user);

        return getById(userId);
    }

    @Override
    public User changeUsername(final Long userId, final String username)
    {
        if (isUsernameExists(username))
        {
            throw new UserUniqueUsernameException(username);
        }

        final var user = getById(userId);

        user.setUsername(username);

        userRepository.update(userId, user);

        return getById(userId);
    }

    @Override
    public User changeEmail(final Long userId, final String email)
    {
        if (isEmailExists(email))
        {
            throw new UserUniqueEmailException(email);
        }

        final var user = getById(userId);

        user.setEmail(email);

        userRepository.update(userId, user);

        return getById(userId);
    }

    @Override
    public User changePassword(final Long userId, final String password)
    {
        final var user = getById(userId);

        user.setPassword(passwordEncoder.encode(password));

        userRepository.update(userId, user);

        return getById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        var user = getByUsername(username);

        if (user == null)
        {
            user = getByEmail(username);
        }

        if (user == null)
        {
            throw new UserNotFoundByLoginException(username);
        }

        final var account = accountFacade.getByUserId(user.getUserId());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(account.getAccountType().getName())))
                .build();
    }
}
