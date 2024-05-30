package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.ReservalRepository;
import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.auth.dto.RegistrationRequest;
import com.example.TableTime.adapter.web.user.dto.UserForm;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements UserDetailsService{

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservalRepository reservalRepository;

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


    public UserEntity getByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("Пользователь не найден");
        return user.get();
    }

//    public UserForm getUserInfo(UserEntity user) {
//        var reservals = reservalRepository.findByUser(user);
//        if (reservals.isEmpty()) {
//        }
//        else {
//            for (var reserval : reservals) {
//                if (reserval.getState().equals("true")
//                        && reserval.getDate().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
//                        && reserval.getTimeStart().after(new SimpleDateFormat("HH:mm").format(new Date())).)
//            }
//        }
//    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }
}
