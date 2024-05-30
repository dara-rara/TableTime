package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.adminApp.dto.RoleList;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminAppService {

    private final UserRepository userRepository;

    public UserEntity changeRole (UserEntity userEntity, Role role) {
        userEntity.setRole(role);
        return userRepository.save(userEntity);
    }

    public RoleList getRole () {
        return new RoleList(Arrays.asList(Role.values()));
    }
}
