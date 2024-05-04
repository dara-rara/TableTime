package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.auth.dto.RegistrationRequest;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements UserDetailsService{

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity saveUser(RegistrationRequest registrationRequest) {
        if (userExists(registrationRequest.email(), registrationRequest.username())) {
            throw new UsernameNotFoundException(registrationRequest.username());
        }
        var newUser = new UserEntity();
        newUser.setUsername(registrationRequest.username());
        newUser.setRealname(registrationRequest.realname());
        newUser.setEmail(registrationRequest.email());
        newUser.setPhone(registrationRequest.phone());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.password()));
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }

    public boolean userExists(String email, String username) {
        return userRepository.existsByEmail(email) || userRepository.existsByUsername(username);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserEntity getUser (String email) {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("Пользователь не найден");
        return user.get();
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }
}
