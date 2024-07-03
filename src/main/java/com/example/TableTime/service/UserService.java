package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.ReservalRepository;
import com.example.TableTime.adapter.repository.ReviewRepository;
import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.auth.dto.RegistrationRequest;
import com.example.TableTime.adapter.web.user.dto.*;
import com.example.TableTime.adapter.web.user.dto.reserval.Reserval;
import com.example.TableTime.adapter.web.user.dto.reserval.UserReservalForm;
import com.example.TableTime.domain.restaurant.reserval.State;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservalRepository reservalRepository;
    private final ReviewRepository reviewRepository;

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
        return userRepository.existsByEmail(email) || userRepository.existsByUsername(username) || username.startsWith("client");
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

    public UserReservalForm getUserInfoReserval(UserEntity user) {
        var reservals = reservalRepository.findByUserOrderByDate(user);
        var grade = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();
        var dictReserval = new LinkedList<Reserval>();
        if (reservals.isEmpty()) {

        }
        else {
            for (var reserval : reservals) {
                if (reserval.getState().equals(State.TRUE)) {

                    var datetime = reserval.getDate().atTime(reserval.getTimeEnd());
                    if (currentDateTime.isAfter(datetime)) {
                        reserval.setState(State.FALSE);
                        reservalRepository.save(reserval);
                    }
                }
                var rest = reserval.getRestaurant();

                if (reserval.getState().equals(State.RATED)) {
                    var review = reviewRepository.findByReserval(reserval);
                    if (review.isPresent()) {
                        grade = review.get().getGrade();
                    }
                }
                else {
                    grade = 0;
                }

                var form = new Reserval(reserval.getId_res(), rest.getName(), rest.getPhone(),
                        DateTimeFormatter.ofPattern("dd.MM.YYYY").format(reserval.getDate()),
                        DateTimeFormatter.ofPattern("HH:mm").format(reserval.getTimeStart()),
                        reserval.getTable().getNumber(),
                        reserval.getPersons(),
                        reserval.getMessage(),
                        reserval.getState(), grade);

                dictReserval.add(form);
            }
        }
        return new UserReservalForm(user.getUsername(), user.getRealname(),
                user.getEmail(), user.getPhone(), dictReserval);
    }

    public AccountUpdate updateUser (UserEntity user, AccountUpdate account) {
        //нужна оброботка этих двух if-ов
        if (!user.getUsername().equals(account.getUsername())
                && userRepository.existsByUsername(account.getUsername())) {
            return account;
        }
        if (!user.getEmail().equals(account.getEmail())
                && userRepository.existsByEmail(account.getEmail())) {
            return account;
        }

        user.setUsername(account.getUsername());
        user.setRealname(account.getRealname());
        user.setPhone(account.getPhone());
        user.setEmail(account.getEmail());
        userRepository.save(user);
        return account;
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }
}
