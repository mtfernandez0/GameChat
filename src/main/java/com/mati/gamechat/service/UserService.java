package com.mati.gamechat.service;

import com.mati.gamechat.entity.User;
import com.mati.gamechat.repository.UserRepository;
import com.mati.gamechat.security.validator.UserValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserValidator userValidator,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public boolean checkCredentialsExistance(User user){
        return Objects.nonNull(user) &
                Objects.nonNull(user.getUsername()) &
                Objects.nonNull(user.getEmail()) &
                Objects.nonNull(user.getPassword()) &
                Objects.nonNull(user.getPasswordConfirmation());
    }

    public void checkCredentialsRegistration(User user,
                                             Errors errors){
        if (!checkCredentialsExistance(user)) return;

        if (Objects.nonNull(findByEmail(user.getEmail())))
            errors.rejectValue(
                    "email",
                    "Match"
            );

        if (Objects.nonNull(findByUsername(user.getUsername())))
            errors.rejectValue(
                    "username",
                    "Match"
            );

        userValidator.validate(user, errors);
    }

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
