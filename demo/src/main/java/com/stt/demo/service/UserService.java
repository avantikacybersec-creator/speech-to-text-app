package com.stt.demo.service;

import com.stt.demo.dto.LoginResponse;
import com.stt.demo.dto.RegisterRequest;
import com.stt.demo.dto.UserResponse;
import com.stt.demo.exception.EmailAlreadyExistsException;
import com.stt.demo.exception.InvalidCredentialsException;
import com.stt.demo.exception.UserNotFoundException;
import com.stt.demo.model.User;
import com.stt.demo.repository.UserRepository;
import com.stt.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public UserResponse registerUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public LoginResponse loginUser(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new InvalidCredentialsException("Invalid password");
        }
        String token = jwtUtil.generateToken(email);

        return new LoginResponse(token);
    }
}