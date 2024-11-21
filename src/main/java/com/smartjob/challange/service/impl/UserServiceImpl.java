package com.smartjob.challange.service.impl;

import com.smartjob.challange.mapper.UserMapper;
import com.smartjob.challange.model.request.UserRequest;
import com.smartjob.challange.model.response.UserResponse;
import com.smartjob.challange.repository.UserRepository;
import com.smartjob.challange.repository.entity.User;
import com.smartjob.challange.service.UserService;
import com.smartjob.challange.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Value("${app.password.regex}")
  private String passwordRegex;

  @Override
  public UserResponse register(UserRequest userRequest) {

    Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("El correo ya registrado");
    }

    if (!Pattern.matches(passwordRegex, userRequest.getPassword())) {
      throw new IllegalArgumentException("La contraseña no cumple con el formato requerido");
    }

    User user = userRepository.save(buildUser(userRequest));

    return UserMapper.mapToUserResponse(user);

  }

  public User buildUser(UserRequest userRequest) {

    User user = UserMapper.mapToUser(userRequest);
    LocalDateTime now = LocalDateTime.now();
    user.setCreated(now);
    user.setModified(now);
    user.setLastLogin(now);
    user.setIsActive(true);
    user.setToken(jwtUtil.generateToken(user));

    return user;

  }

}

