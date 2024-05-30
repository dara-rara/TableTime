package com.example.TableTime.config;

import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminCommandLineRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            var user = new UserEntity();
            user.setUsername("admin");
            user.setRealname("admin");
            user.setEmail("admin1234567890@gmail.com");
            user.setPhone("89530521477");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN_APP);
            userRepository.save(user);
        }
    }
}
